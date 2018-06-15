package com.mastercard.gateway.ats

import android.bluetooth.BluetoothAdapter
import com.mastercard.gateway.common.BluetoothSocketClient
import com.mastercard.gateway.common.Buffer
import com.mastercard.gateway.common.SocketClient
import java.io.Closeable

class ATSBluetoothAdapter(deviceName: String, secure: Boolean) : Closeable {

    internal val CONNECTION_ATTEMPTS = 3

    interface Callback {
        fun onConnected()
        fun onDisconnected()
        fun onMessageReceived(message: String)
        fun onError(throwable: Throwable)
    }

    internal var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    internal val socketClient: BluetoothSocketClient
    internal val callbacks = mutableListOf<Callback>()

    init {

        val bondedDevices = bluetoothAdapter.bondedDevices

        val device = try {
            bondedDevices.first { deviceName == it.name }
        } catch (exception: NoSuchElementException) {
            null
        }

        when (device) {
            null -> throw RuntimeException("No device found with name: $deviceName")
            else -> {
                socketClient = BluetoothSocketClient(device = device, secure = secure)
                socketClient.addCallback(SocketCallback())
            }
        }
    }

    fun connect(attempts: Int = CONNECTION_ATTEMPTS) {
        socketClient.connect(attempts = attempts)
    }

    fun sendMessage(msg: String) {
        socketClient.write(msg.toByteArray())
    }

    override fun close() {
        socketClient.close()
    }

    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    internal inner class SocketCallback : SocketClient.Callback {

        override fun onConnected() {
            callbacks.forEach { it.onConnected() }
        }

        override fun onRead(bytes: ByteArray) {

            // read the buffer for a complete message
            String(bytes).apply {
                callbacks.forEach { callback ->
                    callback.onMessageReceived(this)
                }
            }
        }

        override fun onDisconnected() {
            callbacks.forEach { it.onDisconnected() }
        }

        override fun onError(throwable: Throwable) {
            callbacks.forEach { it.onError(throwable) }
        }
    }
}