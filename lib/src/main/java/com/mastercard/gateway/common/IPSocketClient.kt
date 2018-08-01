package com.mastercard.gateway.common

import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket

/**
 * @see { @link SocketClient } implementation that connects to the provided IP Address and a port
 */
internal class IPSocketClient(val ip: String, val port: Int): SocketClient() {

    companion object {
        const val SOCKET_TIMEOUT = 10000
    }

    var socket: Socket? = null

    override fun connectToSocket() {
        socket = Socket().also {
            it.connect(InetSocketAddress(ip, port), SOCKET_TIMEOUT)
        }
    }

    override fun isConnected(): Boolean {
        return socket?.run { return@run isConnected && !isClosed } ?: false
    }

    override fun getInputStream(): InputStream {
        return when (socket) {
            null -> throw IllegalStateException("Socket may not be null")
            else -> socket!!.getInputStream()
        }
    }

    override fun getOutputStream(): OutputStream {
        return when (socket) {
            null -> throw IllegalStateException("Socket may not be null")
            else -> socket!!.getOutputStream()
        }
    }

    override fun close() {
        socket?.close()
        socket = null
    }
}