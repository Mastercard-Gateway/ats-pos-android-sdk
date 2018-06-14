package com.mastercard.gateway.common

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream
import java.util.*

internal class BluetoothSocketClient(val device: BluetoothDevice, val secure: Boolean = true): SocketClient() {

    private val MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")

    var socket: BluetoothSocket? = null

    override fun connectToSocket() {
        socket = when (secure) {
            true -> device.createRfcommSocketToServiceRecord(MY_UUID)
            false -> device.createInsecureRfcommSocketToServiceRecord(MY_UUID)
        }.apply {
            connect()
        }
    }

    override fun isConnected(): Boolean {
        return socket?.isConnected ?: false
    }

    override fun getInputStream(): InputStream {
        return when (socket) {
            null -> throw IllegalStateException("Socket may not be null")
            else -> socket!!.inputStream
        }
    }

    override fun getOutputStream(): OutputStream {
        return when (socket) {
            null -> throw IllegalStateException("Socket may not be null")
            else -> socket!!.outputStream
        }
    }

    override fun close() {
        super.close()

        socket?.close()
        socket = null
    }
}