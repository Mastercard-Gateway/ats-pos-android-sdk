package com.mastercard.gateway.common

import android.os.Handler
import android.os.Message
import java.io.Closeable
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

/**
 * Spins up a @see { @link java.net.ServerSocket } at the provided port. When a new connection
 * comes in it is passed to all listeners on the incomingSocket() callback
 */
internal class SocketServer(port: Int) : Closeable {

    companion object {
        const val EVENT_NEW_CONNECTION = 1
    }

    interface Callback {
        fun incomingSocket(socket: Socket)
    }

    val handler: Handler = Handler { handleMessage(it) }
    val callbacks = mutableListOf<Callback>()

    val serverSocket = ServerSocket(port)

    init {
        startIncomingConnectThread()
    }

    private fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            EVENT_NEW_CONNECTION -> callbacks.forEach { it.incomingSocket(msg.obj as Socket) }
            else -> return false
        }

        return true
    }

    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    fun isRunning(): Boolean {
        return serverSocket.isBound && !serverSocket.isClosed
    }

    /**
     * Closes the ServerSocket and removes all callbacks
     */
    override fun close() {
        serverSocket.close()
        callbacks.clear()
    }

    private fun startIncomingConnectThread() {
        thread(start = true) { runConnectLoop() }
    }

    private fun runConnectLoop() {
        while (isRunning()) {
            val incomingSocket = serverSocket.accept()

            val msg = handler.obtainMessage(EVENT_NEW_CONNECTION, incomingSocket)
            handler.sendMessage(msg)
        }
    }
}