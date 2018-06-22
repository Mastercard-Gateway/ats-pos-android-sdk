package com.mastercard.gateway.common

import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

/**
 * Basic { @link SocketClient } implementation that assumes the provided socket is already connected
 * and closes the socket when SocketClient is closed
 */
internal class BasicSocketClient(val socket: Socket) : SocketClient() {

    override fun connectToSocket() {
        // NO-OP We are already connected
    }

    override fun getInputStream(): InputStream? = socket.getInputStream()

    override fun getOutputStream(): OutputStream? = socket.getOutputStream()

    override fun isConnected(): Boolean = socket.isConnected && !socket.isClosed

    override fun close() {
        super.close()
        socket.close()
    }
}