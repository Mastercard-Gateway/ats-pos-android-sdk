package com.mastercard.ats.aci

import android.os.Handler
import android.os.Message
import android.util.Log
import com.mastercard.ats.common.Buffer
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class Connection {

    companion object {
        private const val EVENT_CONNECTED = 1
        private const val EVENT_DATA_RECEIVED = 2
        private const val EVENT_ERROR = 3
    }

    interface Callback {
        fun connected(closeable: Closeable)
        fun dataReceived(bytes: ByteArray)
        fun error(throwable: Throwable)
    }

    private var socket: Socket? = null

    private val handler = Handler { handleCallback(it) }
    private val callbacks = arrayListOf<Callback>()
    private val sendBuffer = Buffer()

    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    fun connect(ip: String, port: Int) {
        // start connection to socket on new thread
        Thread(Runnable { doConnect(ip, port) }).start()
    }

    fun disconnect() {
        socket?.close()
        socket = null
    }

    fun send(bytes: ByteArray) {
        sendBuffer.put(bytes)
    }

    private fun handleCallback(msg: Message): Boolean {
        when {
            msg.what == EVENT_CONNECTED -> {
                (msg.obj as Socket).let {
                    socket = it

                    // start read/send threads
                    Thread(Runnable { readLoop() }).start()
                    Thread(Runnable { sendLoop() }).start()

                    callbacks.forEach { it.connected(socket!!) }
                }
            }
            msg.what == EVENT_ERROR -> callbacks.forEach { it.error(msg.obj as Throwable) }
            msg.what == EVENT_DATA_RECEIVED -> callbacks.forEach { it.dataReceived(msg.obj as ByteArray) }
            else -> return false
        }

        return true
    }

    private fun doConnect(ip: String, port: Int) {
        try {
            val socket = Socket(ip, port)

            val msg = handler.obtainMessage()
            msg.what = EVENT_CONNECTED
            msg.obj = socket
            handler.sendMessage(msg)
        } catch (e: Exception) {
            Log.e("Connection", "Error connecting to $ip:$port ", e)

            val msg = handler.obtainMessage()
            msg.what = EVENT_ERROR
            msg.obj = e
            handler.sendMessage(msg)
        }
    }

    private fun readLoop() {
        try {
            socket?.apply {
                val inputStream = this.getInputStream()
                val buffer = ByteArray(1024)

                while (true) {
                    val count = inputStream.read(buffer)
                    if (count < 0) break

                    val msg = handler.obtainMessage()
                    msg.what = EVENT_DATA_RECEIVED
                    msg.obj = buffer.copyOf(count)
                    handler.sendMessage(msg)
                }
            }
        } catch (e: Exception) {
            Log.e("Connection", "Error reading from input stream", e)

            val msg = handler.obtainMessage()
            msg.what = EVENT_ERROR
            msg.obj = e
            handler.sendMessage(msg)
        }

        Log.i("Connection", "Finished read loop")
    }

    private fun sendLoop() {
        try {
            socket?.apply {
                val outputStream = getOutputStream()

                while (!isClosed) {
                    val size = sendBuffer.size
                    if (size > 0) {
                        outputStream.write(sendBuffer.pop(size))
                        outputStream.flush()
                        Log.i("Connection", "Sent $size bytes")

                        Thread.sleep(50) // just throttle down a bit
                    }
                }

            }
        } catch (e: Exception) {
            Log.e("Connection", "Error writing to output stream", e)

            val msg = handler.obtainMessage()
            msg.what = EVENT_ERROR
            msg.obj = e
            handler.sendMessage(msg)
        }

        Log.i("Connection", "Finished send loop")
    }
}
