package com.mastercard.gateway.ats

import com.mastercard.gateway.common.Buffer
import com.mastercard.gateway.common.IPSocketClient
import com.mastercard.gateway.common.SocketClient
import com.mastercard.gateway.common.readMessage
import java.io.Closeable

class ATSClient(val ipAddress: String, val port: Int) : SocketClient.Callback, Closeable {

    interface Callback {
        fun onConnected()
        fun onDisconnected()
        fun onMessageReceived(message: String)
        fun onError(throwable: Throwable)
    }

    private val socketClient: IPSocketClient = IPSocketClient(ipAddress, port)
    private val callbacks = mutableListOf<Callback>()
    private val readBuffer = Buffer()

    init {
        socketClient.addCallback(this)
    }

    fun connect() {
        socketClient.connect(3)
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


    override fun onConnected() {
        callbacks.forEach { it.onConnected() }
    }

    override fun onRead(bytes: ByteArray) {
        readBuffer.put(bytes)

        // read the buffer for a complete message
        readBuffer.readMessage()?.let { message ->
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