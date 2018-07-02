package com.mastercard.gateway.ats

import com.mastercard.gateway.ats.domain.ATSMessage
import com.mastercard.gateway.common.*
import java.io.Closeable

class ATSClient(val ipAddress: String, val port: Int) : Closeable {

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
        "Connecting to ATS at $ipAddress:$port".log(this)
        socketClient.connect(CONNECTION_ATTEMPTS)
    }

    fun sendMessage(msg: String) {
        "Sending message:\n$msg".log(this)
        socketClient.write(Message(msg).bytes)
    }

    fun sendMessage(message: ATSMessage) {
        "Sending message:\n$message".log(this)
        socketClient.write(Interpreter.serialize(message).bytes)
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
            "Connected to ATS".log(this@ATSClient)

            callbacks.forEach { it.onConnected() }
        }

        override fun onRead(bytes: ByteArray) {
            readBuffer.put(bytes)

            // read the buffer for a complete message
            Message.read(readBuffer)?.let { message ->
                "Received message:\n${message.content}".log(this@ATSClient)
                callbacks.forEach { callback ->
                    callback.onMessageReceived(message.content)
                }
            }
        }

        override fun onDisconnected() {
            "Disconnected from ATS".log(this@ATSClient)
            readBuffer.clear()
            callbacks.forEach { it.onDisconnected() }
        }

        override fun onError(throwable: Throwable) {
            "An error occurred while communicating with ATS".log(this@ATSClient, throwable)
            callbacks.forEach { it.onError(throwable) }
        }
    }
}