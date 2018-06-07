package com.mastercard.ats.aci

import com.mastercard.ats.common.SocketConnection
import java.io.Closeable

class ATSClient(private val ipAddress: String, private val port: Int) : SocketConnection.Callback, Closeable {

    var connection: SocketConnection? = null

    var isConnected: Boolean = false

    private val callbacks = mutableListOf<Callback>()

    fun connect() {
        connection = SocketConnection().apply {
            connect(ipAddress, port)
            addCallback(this@ATSClient)
        }
    }

    fun sendMesage(message: String) {
        connection?.send(message.toByteArray())
    }

    override fun close() {
        connection?.disconnect()
        connection?.removeCallback(this)
    }

    override fun connected() {
        isConnected = true
    }

    override fun dataReceived(bytes: ByteArray) {
        val message = String(bytes)

        callbacks.forEach {
            it.messageReceived(message)
        }
    }

    override fun disconnected() {
        isConnected = false
    }

    override fun error(throwable: Throwable) {
        callbacks.forEach {
            it.error(throwable)
        }
    }

    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    interface Callback {
        fun messageReceived(message: String)
        fun error(throwable: Throwable)
    }

}