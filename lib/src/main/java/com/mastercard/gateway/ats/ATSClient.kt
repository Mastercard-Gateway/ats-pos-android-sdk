package com.mastercard.gateway.ats

import com.mastercard.gateway.common.Buffer
import com.mastercard.gateway.common.IPSocketClient
import com.mastercard.gateway.common.SocketClient
import java.io.Closeable

class ATSClient(ipAddress: String, port: Int) : Closeable {

    companion object {
        internal const val CONNECTION_ATTEMPTS = 3
    }

    interface Callback {
        fun onConnected()
        fun onDisconnected()
        fun onMessageReceived(message: String)
        fun onError(throwable: Throwable)
    }

    internal var socketClient = IPSocketClient(ipAddress, port)
    internal val callbacks = mutableListOf<Callback>()
    internal val readBuffer = Buffer()

    init {
        socketClient.addCallback(SocketCallback())
    }

    fun connect() {
        socketClient.connect(CONNECTION_ATTEMPTS)
    }

    fun sendMessage(msg: String) {
        socketClient.write(Message(msg).bytes)
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
            readBuffer.put(bytes)

            // read the buffer for a complete message
            Message.read(readBuffer)?.let { message ->
                callbacks.forEach { callback ->
                    callback.onMessageReceived(message.content)
                }
            }
        }

        override fun onDisconnected() {
            readBuffer.clear()
            callbacks.forEach { it.onDisconnected() }
        }

        override fun onError(throwable: Throwable) {
            callbacks.forEach { it.onError(throwable) }
        }
    }
}