package com.mastercard.gateway.ats

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import com.mastercard.gateway.common.*
import java.io.Closeable
import java.net.Socket
import java.util.*

object ATSBluetoothAdapter : Closeable {

    private val CONNECTION_ATTEMPTS = 3

    internal var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    internal var bluetoothSocketClient: BluetoothSocketClient? = null
    internal var socketClient: SocketClient? = null
    internal lateinit var socketServer: SocketServer

    private val bluetoothSocketCallback = BluetoothSocketCallback()
    private val incomingSocketCallback = SocketCallbacks()

    @JvmStatic
    fun init(port: Int) {

        if (::socketServer.isInitialized) {
            throw IllegalStateException("ATSBluetoothAdapter has already been initialized")
        }

        socketServer = SocketServer(port).apply {
            addCallback(SocketServerCallback())
        }
    }

    @JvmStatic
    @JvmOverloads
    fun setBluetoothDevice(deviceName: String?, secure: Boolean = true) {
        if (!deviceName.isNullOrEmpty()) {
            "Looking for Bluetooth device: $deviceName".log(this)
            val bondedDevices = bluetoothAdapter.bondedDevices
            val device = try {
                bondedDevices.first { deviceName == it.name }
            } catch (exception: NoSuchElementException) {
                null
            }



            when (device) {
                null -> throw RuntimeException("No device found with name: $deviceName")
                else -> {
                    bluetoothSocketClient = BluetoothSocketClient(device = device, secure = secure).apply {
                        addCallback(bluetoothSocketCallback)
                    }
                    "Found: $deviceName".log(this)
                }
            }
        } else {
            // Close and null out bluetooth socket client if name is null
            bluetoothSocketClient?.close()
            bluetoothSocketClient = null
        }
    }


    override fun close() {
        socketServer.close()
        bluetoothSocketClient?.close()
    }


    @SuppressLint("NewApi")
    private class BluetoothSocketCallback : SocketClient.Callback {

        override fun onConnected() {}

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from the bluetooth device to ATS
            /*""""From Bluetooth:
                |${Base64.getEncoder().encode(bytes)}""".log(this)*/
            socketClient?.write(bytes)
        }

        override fun onDisconnected() {
            // If the bluetooth socket closes, close the socket ATS is connected to
            "Bluetooth connection closed".log(this)
            socketClient?.close()
        }

        override fun onError(throwable: Throwable) {
            "Bluetooth connection closed due to error".log(this, throwable)
            socketClient?.close()
        }
    }

    @SuppressLint("NewApi")
    private class SocketCallbacks : SocketClient.Callback {
        override fun onConnected() {
            // When we receive the connection from the server spin up the connection to the bluetooth device
            if (bluetoothSocketClient != null) {
                "Connecting to device".log(this)
                bluetoothSocketClient?.connect(CONNECTION_ATTEMPTS)
            } else {
                //If the bluetooth socket client isn't initialized close the current connection to ATS
                "BluetoothSocketClient is not initialized, closing ATS connection".log(this)
                socketClient?.close()
            }
        }

        override fun onDisconnected() {
            // When we close the connection close the connection to the bluetooth device
            "ATS connection closed".log(this)
            bluetoothSocketClient?.close()
        }

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from ATS to the bluetooth device
           /* """"From ATS:
                |${Base64.getEncoder().encode(bytes)}""".log(this)*/
            bluetoothSocketClient?.write(bytes)
        }

        override fun onError(throwable: Throwable) {
            "ATS connection closed due to error".log(this, throwable)
            bluetoothSocketClient?.close()
        }
    }

    private class SocketServerCallback : SocketServer.Callback {
        override fun incomingSocket(socket: Socket) {
            socketClient = BasicSocketClient(socket)

            socketClient?.addCallback(incomingSocketCallback)
            socketClient?.connect()
        }
    }

}