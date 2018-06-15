package com.mastercard.gateway.common

import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

internal class ServerSocketClient(val port: Int) : SocketClient() {

    var serverSocket: ServerSocket? = null

    var currentSocket: Socket? = null

    override fun connectToSocket() {
        serverSocket = ServerSocket(port).also {
            //Wait for incoming connection
            currentSocket = it.accept()
        }
    }

    override fun isConnected(): Boolean {
        return currentSocket?.run { return@run isConnected && !isClosed } ?: false
    }

    override fun getInputStream(): InputStream {
        return when (currentSocket) {
            null -> throw IllegalStateException("Socket may not be null")
            else -> currentSocket!!.getInputStream()
        }
    }

    override fun getOutputStream(): OutputStream {
        return when (currentSocket) {
            null -> throw IllegalStateException("Socket may not be null")
            else -> currentSocket!!.getOutputStream()
        }
    }

    override fun close() {
        super.close()

        serverSocket?.close()
        serverSocket = null

        currentSocket?.close()
        currentSocket = null
    }
}