package com.mastercard.gateway.common

import android.os.Handler
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.net.SocketException
import kotlin.concurrent.thread

internal abstract class StreamManager : Closeable {

    companion object {
        const val EVENT_DISCONNECTED = 2
        const val EVENT_READ = 3
        const val EVENT_ERROR = 4
    }


    val writeBuffer = Buffer()

    abstract fun getInputStream(): InputStream?
    abstract fun getOutputStream(): OutputStream?
    abstract fun isConnected(): Boolean

    abstract val handler: Handler


    fun write(bytes: ByteArray) {
        if (isConnected()) {
            writeBuffer.put(bytes)
        }
    }

    fun startReadThread() {
        thread(start = true) { runRead() }
    }

    fun startWriteThread() {
        thread(start = true) { runWrite() }
    }

    private fun runRead() {
        // get input stream
        val inputStream = getInputStream()
        val buffer = ByteArray(1024)

        try {
            // read loop
            while (isConnected()) {
                val count = inputStream?.read(buffer) ?: 0
                if (count < 0) {
                    "End of stream reached".logV(this)
                    break
                }

                "Read $count bytes".logV(this)

                // notify read
                val msg = handler.obtainMessage(EVENT_READ, buffer.copyOf(count))
                handler.sendMessage(msg)
            }
        } catch (e: Exception) {
            val msg = handler.obtainMessage(EVENT_ERROR, e)
            handler.sendMessage(msg)
        } finally {
            close()
            handler.sendEmptyMessage(EVENT_DISCONNECTED)
        }
    }

    private fun runWrite() {
        try {
            // get output stream
            val outputStream = getOutputStream()

            while (isConnected()) {
                val size = writeBuffer.size
                if (size > 0) {
                    outputStream?.write(writeBuffer.pop(size))
                    outputStream?.flush()

                    "Sent $size bytes".logV(this)
                }

                Thread.sleep(50) // just throttle down a bit
            }
        } catch (e: Exception) {
            val msg = handler.obtainMessage(EVENT_ERROR, e)
            handler.sendMessage(msg)
        } finally {
            writeBuffer.clear()
            close()
        }
    }
}