package com.mastercard.gateway.ats

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.mastercard.gateway.common.*
import java.net.Socket

/**
 * This class is used to expose a locally paired bluetooth PED to ATS by acting as a data proxy.
 * When you start this adapter with a port, a ServerSocket is started and begins listening for incoming
 * connection requests from ATS. After a request is received, a bluetooth connection is established
 * with the provided reader. A simple example is illustrated below:
 *
 *     int port = 12345; // the ATS configured port for this PED
 *     BluetoothDevice device = fetchBluetoothDevice(); // user-defined bluetooth device
 *
 *     // ready to handle PED connections
 *     ATSBluetoothAdapter.start(port);
 *     ATSBluetoothAdapter.setBluetoothDevice(device);
 *
 *     // ... handle PED connection requests ...
 *
 *     // no longer need to handle PED connections
 *     ATSBluetoothAdapter.stop();
 */
object ATSBluetoothAdapter {

    internal const val DEFAULT_DEVICE_PREFIXES = arrayOf("Miura")
    internal const val CONNECTION_ATTEMPTS = 3

    internal var atsSocketServer: SocketServer? = null
    internal var atsCommunicationSocketClient: BasicSocketClient? = null
    internal var bluetoothSocketClient: BluetoothSocketClient? = null

    // mockable callbacks
    internal val atsServerSocketCallback = ATSServerSocketCallback()
    internal val atsCommunicationSocketCallback = ATSCommunicationSocketCallback()
    internal val bluetoothSocketCallback = BluetoothSocketCallback()


    /**
     * The bluetooth device to connect to when ATS opens a socket
     */
    @JvmStatic
    var bluetoothDevice: BluetoothDevice? = null

    /**
     * Starts listening for incoming ATS connection requests on the specified port.
     * The port should be the value provided in ATS configuration for this device.
     *
     * @param port The port to listen on
     */
    @JvmStatic
    fun start(port: Int) {
        if (isRunning()) {
            return
        }

        "Starting ATS bluetooth adapter".log(this)
        atsSocketServer = createSocketServer(port)
    }

    /**
     * Stops listening for ATS connection requests and closes all connections
     */
    @JvmStatic
    fun stop() {
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
        return atsSocketServer?.isRunning() ?: false
    }


    internal fun createSocketServer(port: Int): SocketServer {
        return SocketServer(port).apply {
            addCallback(atsServerSocketCallback)
        }
    }

    internal fun createATSCommunicationSocketClient(socket: Socket): BasicSocketClient {
        return BasicSocketClient(socket).apply {
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
            "Received incoming ATS socket".log(this)

            if (bluetoothDevice == null) {
                "No bluetooth device defined, closing incoming socket".log(this)
                socket.close()
                return
            }

            if (atsCommunicationSocketClient?.isConnected() == true) {
                "Communication socket already open, closing incoming socket".log(this)
                socket.close()
                return
            }

            // set up communication socket client
            atsCommunicationSocketClient = createATSCommunicationSocketClient(socket).apply {
                connect()
            }

            "Connecting to bluetooth device: ${bluetoothDevice!!.name}".log(this)
            bluetoothSocketClient = createBluetoothSocketClient(bluetoothDevice!!).apply {
                connect(CONNECTION_ATTEMPTS)
            }
        }
    }

    @SuppressLint("NewApi")
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
        }

        override fun onError(throwable: Throwable) {
            "ATS connection encountered an error".log(this, throwable)
        }
    }

    @SuppressLint("NewApi")
    internal class BluetoothSocketCallback : SocketClient.Callback {

        override fun onConnected() {}

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from the bluetooth device to ATS
            atsCommunicationSocketClient?.write(bytes)
        }

        override fun onDisconnected() {
            // If the bluetooth socket closes, close the socket ATS is connected to
            "Bluetooth connection closed".log(this)
            closeATSConnection()
        }

        override fun onError(throwable: Throwable) {
            "Bluetooth connection encountered an error".log(this, throwable)
        }
    }


    /**
     *  Get the list of bonded devices
     *
     *  @return list of names of bonded devices or null, if Bluetooth is not supported
     */
    @JvmStatic
    fun getDeviceNames(): List<String>? {

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter() ?: return null

        val list = mutableListOf<String>()
        bluetoothAdapter.bondedDevices.map { it.name }.toCollection(list)
        return list
    }

    /**
     *  Get the list of bonded devices
     *
     *  @return list of bonded devices or null, if Bluetooth is not supported
     */
//    @JvmStatic
//    fun getDevices(): List<BluetoothDevice>? {
//        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter() ?: return null
//        return bluetoothAdapter.bondedDevices.toList()
//    }

    /**
     *  Returns a list of supported bluetooth devices, filtered by name prefix
     *
     *  @return The list of supported bluetooth devices
     */
    @JvmStatic
    fun getDevices(): List<BluetoothDevice> {
        return BluetoothAdapter.getDefaultAdapter()?.bondedDevices?.filter { device ->
            DEFAULT_DEVICE_PREFIXES.forEach { prefix ->
                if (device.name.startsWith(prefix)) {
                    return@filter true
                }
            }
            return@filter false
        } ?: listOf()
    }

    // SAVE THIS STUBBED OUT WORK FOR BLUETOOTH DEVICE LIST METHODS

//    internal var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

//    @JvmStatic
//    fun init(port: Int) {
//        if (::atsSocketServer.isInitialized) {
//            throw IllegalStateException("ATSBluetoothAdapter has already been initialized")
//        }
//
//        atsSocketServer = SocketServer(port).apply {
//            addCallback(ATSServerSocketCallback())
//        }
//    }

//    @JvmStatic
//    @JvmOverloads
//    fun setBluetoothDevice(deviceName: String?, secure: Boolean = true) {
//        if (!deviceName.isNullOrEmpty()) {
//            "Looking for Bluetooth device: $deviceName".log(this)
//            val bondedDevices = bluetoothAdapter.bondedDevices
//            val device = try {
//                bondedDevices.first { deviceName == it.name }
//            } catch (exception: NoSuchElementException) {
//                null
//            }
//
//
//
//            when (device) {
//                null -> throw RuntimeException("No device found with name: $deviceName")
//                else -> {
//                    bluetoothSocketClient = BluetoothSocketClient(device = device, secure = secure).apply {
//                        addCallback(bluetoothSocketCallback)
//                    }
//                    "Found: $deviceName".log(this)
//                }
//            }
//        } else {
//            // Close and null out bluetooth socket client if name is null
//            bluetoothSocketClient?.close()
//            bluetoothSocketClient = null
//        }
//    }
}