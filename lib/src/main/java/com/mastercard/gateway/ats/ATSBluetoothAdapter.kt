package com.mastercard.gateway.ats

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.os.Build
import com.mastercard.gateway.common.BluetoothSocketClient
import com.mastercard.gateway.common.ServerSocketClient
import com.mastercard.gateway.common.SocketClient
import com.mastercard.gateway.common.log
import java.io.Closeable
import java.util.*

class ATSBluetoothAdapter(port: Int) : Closeable {

    private val CONNECTION_ATTEMPTS = 3

    internal var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    internal var bluetoothSocketClient: BluetoothSocketClient? = null
    private val serverSocketClient: ServerSocketClient

    init {
        serverSocketClient = ServerSocketClient(port).apply {
            addCallback(ServerSocketCallback())
            connect()
        }
    }

    @JvmOverloads
    fun setBluetoothDevice(deviceName: String?, secure: Boolean = true) {
        if (!deviceName.isNullOrEmpty()) {
            "Attempting to connect to: $deviceName".log(this)
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
                    "Connected to: $deviceName".log(this)
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


    @SuppressLint("NewApi")
    private inner class BluetoothSocketCallback : SocketClient.Callback {

        override fun onConnected() {}

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from the bluetooth device to ATS
            """"From Bluetooth:
                |${Base64.getEncoder().encode(bytes)}""".log(this)
            serverSocketClient.write(bytes)
        }

        override fun onDisconnected() {
            // If the bluetooth socket closes, close the socket ATS is connected to
            "Bluetooth connection closed".log(this)
            serverSocketClient.closeCurrentConnection()
        }

        override fun onError(throwable: Throwable) {
            "Bluetooth connection closed due to error".log(this, throwable)
            serverSocketClient.closeCurrentConnection()
        }
    }

    @SuppressLint("NewApi")
    private inner class ServerSocketCallback : SocketClient.Callback {
        override fun onConnected() {
            // When we receive the connection from the server spin up the connection to the bluetooth device
            if (bluetoothSocketClient != null) {
                "Attempting to spin up bluetooth connection".log(this)
                bluetoothSocketClient?.connect(CONNECTION_ATTEMPTS)
            } else {
                //If the bluetooth socket client isn't initialized close the current connection to ATS
                "BluetoothSocketClient is not initialized, closing ATS connection".log(this)
                serverSocketClient.closeCurrentConnection()
            }
        }

        override fun onDisconnected() {
            // When we close the connection close the connection to the bluetooth device
            "ATS connection closed".log(this)
            bluetoothSocketClient?.close()
        }

        override fun onRead(bytes: ByteArray) {
            // Pass incoming bytes from ATS to the bluetooth device
            """"From ATS:
                |${Base64.getEncoder().encode(bytes)}""".log(this)
            bluetoothSocketClient?.write(bytes)
        }

        override fun onError(throwable: Throwable) {
            "ATS connection closed due to error".log(this, throwable)
            bluetoothSocketClient?.close()
        }
    }
}