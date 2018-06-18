package com.mastercard.gateway.common

import android.os.Handler
import android.os.Message
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.net.SocketException

internal abstract class SocketClient : Closeable {

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


    val handler = Handler { handleMessage(it) }
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

    @JvmOverloads
    fun connect(attempts: Int = 1) {
        close()

        // start connection thread
        createConnectThread(attempts).start()
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
            var attempt = 0

            while (attempt++ < attempts) {
                "Attempting to connect ($attempt of $attempts)".log(this)

                try {
                    connectToSocket()

                    // if we got a closeable, break from loop
                    break
                } catch (e: Exception) {
                    "Connection attempt failed".log(this)

                    // if error on last attempt, rethrow exception
                    if (attempt == attempts) {
                        throw e
                    }
                }
            }

            // start write thread
            createWriteThread().start()

            // notify connected
            handler.sendEmptyMessage(EVENT_CONNECTED)

            // get input stream
            val inputStream = getInputStream()
            val buffer = ByteArray(1024)

            // read loop
            while (true) {
                val count = inputStream.read(buffer)
                if (count < 0) break

                "Read $count bytes".log(this)

                // notify read
                val msg = handler.obtainMessage(EVENT_READ, buffer.copyOf(count))
                handler.sendMessage(msg)
            }
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

                    "Sent $size bytes".log(this)
                }

                Thread.sleep(50) // just throttle down a bit
            }
        } catch (e: Exception) {
            "Error writing to socket".log(this, e)
        } finally {
            close()
        }
    }

    fun createConnectThread(attempts: Int): Thread {
        return Thread(Runnable { runConnect(attempts) })
    }

    fun createWriteThread(): Thread {
        return Thread(Runnable { runWrite() })
    }
}