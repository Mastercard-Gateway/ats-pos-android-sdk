package com.mastercard.gateway.common

import android.os.Handler
import android.os.Message
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.net.SocketException
import kotlin.concurrent.thread
import kotlin.math.max

internal abstract class SocketClient: Closeable {

    companion object {
        const val EVENT_CONNECTED = 1
        const val EVENT_DISCONNECTED = 2
        const val EVENT_READ = 3
        const val EVENT_ERROR = 4
    }

    interface Callback {
        fun onConnected()
        fun onDisconnected()
        fun onRead(bytes: ByteArray)
        fun onError(throwable: Throwable)
    }


    var handler = Handler { handleMessage(it) }
    val callbacks = mutableListOf<Callback>()
    val writeBuffer = Buffer()


    abstract fun isConnected(): Boolean
    abstract fun connectToSocket()
    abstract fun getInputStream(): InputStream
    abstract fun getOutputStream(): OutputStream


    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    fun connect(attempts: Int = 1) {
        close()

        // start connection thread
        startConnectThread(attempts)
    }

    override fun close() {
        writeBuffer.clear()
    }

    fun write(bytes: ByteArray) {
        if (isConnected()) {
            writeBuffer.put(bytes)
        }
    }

    fun handleMessage(msg: Message): Boolean {
        when {
            msg.what == EVENT_CONNECTED -> callbacks.forEach { it.onConnected() }
            msg.what == EVENT_DISCONNECTED -> callbacks.forEach { it.onDisconnected() }
            msg.what == EVENT_READ -> callbacks.forEach { it.onRead(msg.obj as ByteArray) }
            msg.what == EVENT_ERROR -> callbacks.forEach { it.onError(msg.obj as Throwable) }
            else -> return false
        }

        return true
    }

    fun runConnect(attempts: Int) {
        try {
            // attempt to connect to the socket
            attemptConnection(attempts)

            // start write thread
            startWriteThread()

            // notify connected
            handler.sendEmptyMessage(EVENT_CONNECTED)

            readLoop()

        } catch (e: SocketException) {
            // socket disconnected. event dispatched below
        } catch (e: Exception) {
            "Error connecting to / reading from socket".log(this, e)

            val msg = handler.obtainMessage(EVENT_ERROR, e)
            handler.sendMessage(msg)
        } finally {
            close()
            handler.sendEmptyMessage(EVENT_DISCONNECTED)
        }
    }

    fun runWrite() {
        try {
            // get output stream
            val outputStream = getOutputStream()

            while (isConnected()) {
                val size = writeBuffer.size
                if (size > 0) {
                    outputStream.write(writeBuffer.pop(size))
                    outputStream.flush()

                    "Sent $size bytes".logV(this)
                }

                Thread.sleep(50) // just throttle down a bit
            }
        } catch (e: Exception) {
            "Error writing to socket".log(this, e)
        } finally {
            close()
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

    fun readLoop() {
        // get input stream
        val inputStream = getInputStream()
        val buffer = ByteArray(1024)

        // read loop
        while (true) {
            val count = inputStream.read(buffer)
            if (count < 0) {
                "End of stream reached".logV(this)
                break
            }

            "Read $count bytes".logV(this)

            // notify read
            val msg = handler.obtainMessage(EVENT_READ, buffer.copyOf(count))
            handler.sendMessage(msg)
        }
    }

    fun startConnectThread(attempts: Int) {
        thread(start = true) { runConnect(attempts) }
    }

    fun startWriteThread() {
        thread(start = true) { runWrite() }
    }
}