package com.mastercard.gateway.ats

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.mastercard.gateway.common.*
import java.net.Socket

/**
 * This class is used to expose a locally paired bluetooth PED to ATS by acting as a data proxy.
 * When you startStatic this adapter with a port, a ServerSocket is started and begins listening for incoming
 * connection requests from ATS. After a request is received, a bluetooth connection is established
 * with the provided reader. A simple example is illustrated below:
 *
 *     int port = 12345; // the ATS configured port for this PED
 *     BluetoothDevice device = fetchBluetoothDevice(); // user-defined bluetooth device
 *
 *      ATSBluetoothConfiguration configuration = new ATSBluetoothConfiguration.Static(port, device);
 *
 *     // ready to handle PED connections
 *     ATSBluetoothAdapter.start(configuration);
 *
 *     // ... handle PED connection requests ...
 *
 *     // no longer need to handle PED connections
 *     ATSBluetoothAdapter.stop();
 */
object ATSBluetoothAdapter {

    internal const val CONNECTION_ATTEMPTS = 3

    internal val DEFAULT_DEVICE_PREFIXES = arrayOf("Miura", "Simplify")

    internal var atsSocketServer: SocketServer? = null
    internal var atsCommunicationSocketClient: SocketClient? = null
    internal var bluetoothSocketClient: BluetoothSocketClient? = null

    // mockable callbacks
    internal val atsServerSocketCallback = ATSServerSocketCallback()
    internal val atsCommunicationSocketCallback = ATSCommunicationSocketCallback()
    internal val bluetoothSocketCallback = BluetoothSocketCallback()

    internal var currentConfiguration: ATSBluetoothConfiguration? = null

    internal var isRetrying = false


    /**
     * Starts the communication between ATS and the Bluetooth device
     *
     * @param configuration: ATSBluetoothConfiguration - provides the Adapter with the information
     * to connect to ATS and the Bluetooth device. The supported configurations are: Static and Roaming
     */
    @JvmStatic
    fun start(configuration: ATSBluetoothConfiguration) {
        if (isRunning()) {
            return
        }

        currentConfiguration = configuration

        when (configuration) {
            is ATSBluetoothConfiguration.Static -> {
                atsSocketServer = createSocketServer(configuration.port)
                bluetoothSocketClient = createBluetoothSocketClient(configuration.bluetoothDevice)
            }
            is ATSBluetoothConfiguration.Roaming -> {
                initRoamingSocketClients(configuration)
            }
        }

        "Starting ATS bluetooth adapter".log(this)
    }

    private fun initRoamingSocketClients(configuration: ATSBluetoothConfiguration.Roaming) {
        bluetoothSocketClient = createBluetoothSocketClient(configuration.bluetoothDevice)
        bluetoothSocketClient?.connect(CONNECTION_ATTEMPTS)
    }

    /**
     * Stops listening for ATS connection requests and closes all connections
     */
    @JvmStatic
    fun stop() {
        currentConfiguration = null
        "Stopping ATS bluetooth adapter".log(this)
        closeATSSocketServer()
        closeATSConnection()
        closeBluetoothConnection()
    }

    /**
     * Checks whether or not the adapter is listening for ATS connection requests
     *
     * @return True or False
     */
    @JvmStatic
    fun isRunning(): Boolean {
        return atsSocketServer?.isRunning() ?: atsCommunicationSocketClient?.isConnected() ?: false
    }

    /**
     *  Returns a list of supported bluetooth card readers that have previously been paired to this device.
     *
     *  @return The list of previously paired supported bluetooth devices
     */
    @JvmStatic
    fun getSupportedDevices(): List<BluetoothDevice> {
        return BluetoothAdapter.getDefaultAdapter()?.bondedDevices?.filter { device ->
            DEFAULT_DEVICE_PREFIXES.forEach { prefix ->
                if (device.name.startsWith(prefix)) {
                    return@filter true
                }
            }
            return@filter false
        } ?: listOf()
    }

    internal fun retryBluetoothConnection() {
        if (!isRetrying) {
            isRetrying = true
            currentConfiguration?.let { configuration ->
                if (configuration is ATSBluetoothConfiguration.Roaming) {
                    initRoamingSocketClients(configuration)
                }
            }

        }
    }

    internal fun createSocketServer(port: Int): SocketServer {
        return SocketServer(port).apply {
            addCallback(atsServerSocketCallback)
        }
    }

    internal fun createATSCommunicationSocketClient(socket: Socket): SocketClient {
        return BasicSocketClient(socket).apply {
            addCallback(atsCommunicationSocketCallback)
        }
    }

    internal fun createATSCommunicationSocketClient(ipAddress: String, port: Int): SocketClient {
        return IPSocketClient(ipAddress, port).apply {
            addCallback(atsCommunicationSocketCallback)
        }
    }

    internal fun createBluetoothSocketClient(device: BluetoothDevice): BluetoothSocketClient {
        return BluetoothSocketClient(device, true).apply {
            addCallback(bluetoothSocketCallback)
        }
    }

    internal fun closeATSSocketServer() {
        atsSocketServer?.close()
        atsSocketServer = null
    }

    internal fun closeATSConnection() {
        atsCommunicationSocketClient?.close()
        atsCommunicationSocketClient = null
    }

    internal fun closeBluetoothConnection() {
        bluetoothSocketClient?.close()
        bluetoothSocketClient = null
    }


    internal class ATSServerSocketCallback : SocketServer.Callback {
        override fun incomingSocket(socket: Socket) {
            "Received incoming ATS socket".logV(this)

            if (atsCommunicationSocketClient?.isConnected() == true) {
                "Communication socket already open, closing incoming socket".logV(this)
                socket.close()
                return
            }

            // set up communication socket client
            atsCommunicationSocketClient = createATSCommunicationSocketClient(socket).apply {
                connect()
            }

            bluetoothSocketClient?.connect(CONNECTION_ATTEMPTS)
        }
    }


    internal class ATSCommunicationSocketCallback : SocketClient.Callback {

        override fun onConnected() {}

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from ATS to the bluetooth device
            bluetoothSocketClient?.write(bytes)
        }

        override fun onDisconnected() {
            // on disconnect, close the connection to the bluetooth device
            "ATS connection closed".log(this)
            closeBluetoothConnection()

            if (currentConfiguration is ATSBluetoothConfiguration.Roaming) {
                retryBluetoothConnection()
            }
        }

        override fun onError(throwable: Throwable) {
            "ATS connection encountered an error".log(this, throwable)
        }
    }


    internal class BluetoothSocketCallback : SocketClient.Callback {

        override fun onConnected() {
            val configuration = currentConfiguration
            if (configuration is ATSBluetoothConfiguration.Roaming) {
                "Connecting to ATS".log(this)
                isRetrying = false
                atsCommunicationSocketClient = createATSCommunicationSocketClient(configuration.ipAddress, configuration.port).apply {
                    connect(CONNECTION_ATTEMPTS)
                }
            }
        }

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from the bluetooth device to ATS
            atsCommunicationSocketClient?.write(bytes)
        }

        override fun onDisconnected() {
            // If the bluetooth socket closes, close the socket ATS is connected to
            "Bluetooth connection closed".log(this)
            closeATSConnection()

            if (currentConfiguration is ATSBluetoothConfiguration.Roaming) {
                retryBluetoothConnection()
            }
        }

        override fun onError(throwable: Throwable) {
            "Bluetooth connection encountered an error".log(this, throwable)

            if (currentConfiguration is ATSBluetoothConfiguration.Roaming) {
                if (bluetoothSocketClient?.isConnected()?.not() ?: true) {
                    isRetrying = false
                    retryBluetoothConnection()
                }
            }
        }
    }
}