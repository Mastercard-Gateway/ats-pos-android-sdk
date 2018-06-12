package com.mastercard.gateway.ats

import com.mastercard.gateway.common.Buffer
import com.mastercard.gateway.common.SocketClient
import com.mastercard.gateway.common.readMeassage
import java.io.Closeable

class ATSClient(val ipAddress: String, val port: Int) : SocketClient.Callback, Closeable {

    interface Callback {
        fun connected()
        fun disconnected()
        fun messageReceived(message: String)
        fun error(throwable: Throwable)
    }

    private val socketClient: SocketClient = SocketClient()
    private val callbacks = mutableListOf<Callback>()
    private val readBuffer = Buffer()

    init {
        socketClient.addCallback(this)
    }

    fun connect() {
        socketClient.connect(ipAddress, port)
    }

    fun sendMessage(msg: String) {
        socketClient.send(Message(msg).bytes)
    }

    override fun close() {
        socketClient.disconnect()
    }

    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }


    override fun connected() {
        callbacks.forEach { it.connected() }
    }

    override fun dataReceived(bytes: ByteArray) {
        readBuffer.put(bytes)

        // read the buffer for a complete message
        readBuffer.readMeassage()?.let { message ->
            callbacks.forEach { callback ->
                callback.messageReceived(message.content)
            }
        }
    }

    override fun disconnected() {
        readBuffer.clear()
        callbacks.forEach { it.disconnected() }
    }

    override fun error(throwable: Throwable) {
        callbacks.forEach { it.error(throwable) }
    }
}