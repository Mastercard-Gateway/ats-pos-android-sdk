package com.mastercard.gateway.common

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.net.SocketException
import kotlin.concurrent.thread
import kotlin.math.max

internal abstract class SocketClient: StreamManager(), Closeable {

    companion object {
        const val EVENT_CONNECTED = 1
    }

    interface Callback {
        fun onConnected()
        fun onDisconnected()
        fun onRead(bytes: ByteArray)
        fun onError(throwable: Throwable)
    }

    override var handler: Handler = Handler { handleMessage(it) }
    val callbacks = mutableListOf<Callback>()

    abstract fun connectToSocket()

    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    fun connect(attempts: Int = 1) {
        writeBuffer.clear()
        // start connection thread
        startConnectThread(attempts)
    }

    fun handleMessage(msg: Message): Boolean {
        when (msg.what) {
            EVENT_CONNECTED -> {
                // Spin up both the read and write threads
                startWriteThread()
                startReadThread()

                callbacks.forEach { it.onConnected() }

            }
            EVENT_DISCONNECTED -> {
                //Notify disconnect and clear all callbacks
                callbacks.forEach { it.onDisconnected() }

                close()
            }
            EVENT_READ -> callbacks.forEach { it.onRead(msg.obj as ByteArray) }
            EVENT_ERROR -> callbacks.forEach { it.onError(msg.obj as Throwable) }
            else -> return false
        }

        return true
    }

    fun runConnect(attempts: Int) {
        try {
            // attempt to connect to the socket
            attemptConnection(attempts)

            // notify connected
            handler.sendEmptyMessage(EVENT_CONNECTED)

        } catch (e: Exception) {
            val msg = handler.obtainMessage(EVENT_ERROR, e)
            handler.sendMessage(msg)
        }
    }

    fun attemptConnection(attempts: Int) {
        // attempt to connect to the socket
        var attempt = 0
        val minAttempts = max(attempts, 1)

        while (attempt++ < minAttempts) {
            "Attempting to connect ($attempt of $minAttempts)".log(this)

            try {
                connectToSocket()
                // if we got a closeable, break from loop
                break
            } catch (e: Exception) {
                "Connection attempt failed".log(this)

                // if error on last attempt, rethrow exception
                if (attempt == minAttempts) {
                    throw e
                }
            }
        }
    }

    fun startConnectThread(attempts: Int) {
        thread(start = true) { runConnect(attempts) }
    }
}