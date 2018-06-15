package com.mastercard.gateway.ats

import android.bluetooth.BluetoothAdapter
import com.mastercard.gateway.common.BluetoothSocketClient
import com.mastercard.gateway.common.IPSocketClient
import com.mastercard.gateway.common.ServerSocketClient
import com.mastercard.gateway.common.SocketClient
import java.io.Closeable

class ATSBluetoothAdapter(deviceName: String, secure: Boolean, port: Int) : Closeable {

    internal val CONNECTION_ATTEMPTS = 3

    internal var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    internal val bluetoothSocketClient: BluetoothSocketClient
    internal val serverSocketClient = ServerSocketClient(port)

    init {
        // Immediately spin up our ServerSocket and listen from incoming connections
        serverSocketClient.addCallback(ServerSocketCallback())
        serverSocketClient.connect()

        val bondedDevices = bluetoothAdapter.bondedDevices

        val device = try {
            bondedDevices.first { deviceName == it.name }
        } catch (exception: NoSuchElementException) {
            null
        }

        when (device) {
            null -> throw RuntimeException("No device found with name: $deviceName")
            else -> {
                bluetoothSocketClient = BluetoothSocketClient(device = device, secure = secure)
                bluetoothSocketClient.addCallback(BluetoothSocketCallback())
            }
        }
    }

    override fun close() {
        serverSocketClient.close()
        bluetoothSocketClient.close()
    }

    internal inner class BluetoothSocketCallback : SocketClient.Callback {

        override fun onConnected() {

        }

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from the bluetooth device to ATS
            serverSocketClient.write(bytes)
        }

        override fun onDisconnected() {
            // If the bluetooth socket closes, close the socket ATS is connected to
            serverSocketClient.close()
        }

        override fun onError(throwable: Throwable) {
            TODO("Implement me")
        }
    }

    internal inner class ServerSocketCallback : SocketClient.Callback {
        override fun onConnected() {
            // When we receive the connection from the server spin up the connection to the bluetooth device
            bluetoothSocketClient.connect(CONNECTION_ATTEMPTS)
        }

        override fun onDisconnected() {
            // When we close the connection close the connection to the bluetooth device
            bluetoothSocketClient.close()
        }

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from ATS to the bluetooth device
            bluetoothSocketClient.write(bytes)
        }

        override fun onError(throwable: Throwable) {
            TODO("Implement me")
        }
    }
}