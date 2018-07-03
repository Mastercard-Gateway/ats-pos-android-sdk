package com.mastercard.gateway.ats

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.mastercard.gateway.common.*
import java.net.Socket

object ATSBluetoothAdapter {

    internal const val CONNECTION_ATTEMPTS = 3

    internal var atsSocketServer: SocketServer? = null
    internal var atsCommunicationSocketClient: BasicSocketClient? = null
    internal var bluetoothSocketClient: BluetoothSocketClient? = null

    // mockable callbacks
    internal val atsServerSocketCallback = ATSServerSocketCallback()
    internal val atsCommunicationSocketCallback = ATSCommunicationSocketCallback()
    internal val bluetoothSocketCallback = BluetoothSocketCallback()


    /**
     *
     */
    @JvmStatic
    var bluetoothDevice: BluetoothDevice? = null

    /**
     *
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
     *
     */
    @JvmStatic
    fun stop() {
        "Stopping ATS bluetooth adapter".log(this)
        closeATSSocketServer()
        closeATSConnection()
        closeBluetoothConnection()
    }

    /**
     *
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
            // no need to call connect, already connected
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
            "Bluetooth connection closed due to error".log(this, throwable)
        }
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