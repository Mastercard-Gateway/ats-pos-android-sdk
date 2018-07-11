package com.mastercard.gateway.common

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream

/**
 * @see { @link SocketClient } implementation that connects to the provided @see { @link android.bluetooth.BluetoothDevice }
 */
internal class BluetoothSocketClient(val device: BluetoothDevice, val secure: Boolean = true): SocketClient() {

    var socket: BluetoothSocket? = null

    override fun connectToSocket() {
        socket = when (secure) {
            true -> device.createRfcommSocketToServiceRecord(device.uuids[0].uuid)
            false -> device.createInsecureRfcommSocketToServiceRecord(device.uuids[0].uuid)
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
        socket?.close()
        socket = null
    }
}