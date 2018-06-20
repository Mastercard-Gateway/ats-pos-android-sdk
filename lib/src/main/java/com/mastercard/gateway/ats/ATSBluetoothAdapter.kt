package com.mastercard.gateway.ats

import android.bluetooth.BluetoothAdapter
import com.mastercard.gateway.common.BluetoothSocketClient
import com.mastercard.gateway.common.ServerSocketClient
import com.mastercard.gateway.common.SocketClient
import java.io.Closeable

object ATSBluetoothAdapter : Closeable {

    internal const val CONNECTION_ATTEMPTS = 3

    internal var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    internal var bluetoothSocketClient: BluetoothSocketClient? = null
    internal lateinit var serverSocketClient: ServerSocketClient

    @JvmOverloads
    fun init(port: Int, deviceName: String?, secure: Boolean = true) {

        setBluetoothDevice(deviceName = deviceName, secure = secure)

        startServer(port = port)
    }

    internal fun startServer(port: Int) {
        // Immediately spin up our ServerSocket and listen from incoming connections
        serverSocketClient = ServerSocketClient(port).apply {
            addCallback(ServerSocketCallback())
            connect()
        }
    }

    internal fun setBluetoothDevice(deviceName: String?, secure: Boolean) {

        if (!deviceName.isNullOrEmpty()) {
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
                        addCallback(BluetoothSocketCallback())
                    }
                }
            }
        } else {
            // Close and null out bluetooth socket client if name is null
            bluetoothSocketClient?.close()
            bluetoothSocketClient = null
        }
    }


    override fun close() {
        serverSocketClient.close()
        bluetoothSocketClient?.close()
    }

    internal class BluetoothSocketCallback : SocketClient.Callback {

        override fun onConnected() {}

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from the bluetooth device to ATS
            serverSocketClient.write(bytes)
        }

        override fun onDisconnected() {
            // If the bluetooth socket closes, close the socket ATS is connected to
            serverSocketClient.closeCurrentConnection()
        }

        override fun onError(throwable: Throwable) {
            serverSocketClient.closeCurrentConnection()
        }
    }

    internal class ServerSocketCallback : SocketClient.Callback {
        override fun onConnected() {
            // When we receive the connection from the server spin up the connection to the bluetooth device
            if (bluetoothSocketClient != null) {
                bluetoothSocketClient?.connect(CONNECTION_ATTEMPTS)
            } else {
                //If the bluetooth socket client isn't initialized close the current connection to ATS
                serverSocketClient.closeCurrentConnection()
            }
        }

        override fun onDisconnected() {
            // When we close the connection close the connection to the bluetooth device
            bluetoothSocketClient?.close()
        }

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from ATS to the bluetooth device
            bluetoothSocketClient?.write(bytes)
        }

        override fun onError(throwable: Throwable) {
            bluetoothSocketClient?.close()
        }
    }
}