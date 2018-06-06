package com.mastercard.ats.aci

import android.os.Handler
import android.os.Message
import android.util.Log
import com.mastercard.ats.common.Buffer
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException
import java.nio.channels.SocketChannel

class Connection {

    companion object {
        private const val EVENT_CONNECTED = 1
        private const val EVENT_DATA_RECEIVED = 2
        private const val EVENT_DISCONNECTED = 3
        private const val EVENT_ERROR = 4
    }

    interface Callback {
        fun connected()
        fun dataReceived(bytes: ByteArray)
        fun disconnected()
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
        if (socket == null || socket?.isConnected == false) {
            // start connection to socket on new thread
            Thread(ConnectRunnable(ip, port)).start()
        }
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
                callbacks.forEach { it.connected() }

                socket?.let {
                    // start read/send threads
                    Thread(ReadRunnable(it)).start()
                    Thread(SendRunnable(it)).start()
                }
            }
            msg.what == EVENT_DISCONNECTED -> callbacks.forEach { it.disconnected() }
            msg.what == EVENT_DATA_RECEIVED -> callbacks.forEach { it.dataReceived(msg.obj as ByteArray) }
            msg.what == EVENT_ERROR -> callbacks.forEach { it.error(msg.obj as Throwable) }
            else -> return false
        }

        return true
    }

    private inner class ConnectRunnable(val ip: String, val port: Int) : Runnable {

        override fun run() {
            try {
                socket = Socket(ip, port)

                handler.sendEmptyMessage(EVENT_CONNECTED)
            } catch (e: Exception) {
                Log.e("Connection", "Error connecting to $ip:$port ", e)

                val msg = handler.obtainMessage()
                msg.what = EVENT_ERROR
                msg.obj = e
                handler.sendMessage(msg)
            }
        }
    }

    private inner class ReadRunnable(val socket: Socket) : Runnable {

        override fun run() {
            try {
                val inputStream = socket.getInputStream()
                val buffer = ByteArray(1024)

                while (true) {
                    val count = inputStream.read(buffer)
                    if (count < 0) break

                    Log.i("Connection", "Read $count bytes")

                    val msg = handler.obtainMessage()
                    msg.what = EVENT_DATA_RECEIVED
                    msg.obj = buffer.copyOf(count)
                    handler.sendMessage(msg)
                }
            } catch (e: SocketException) {
                // socket disconnected. event dispatched below
            } catch (e: Exception) {
                Log.e("Connection", "Exception in read loop", e)
            }

            handler.sendEmptyMessage(EVENT_DISCONNECTED)
        }
    }

    private inner class SendRunnable(val socket: Socket) : Runnable {

        override fun run() {
            try {
                val outputStream = socket.getOutputStream()

                while (!socket.isClosed) {
                    val size = sendBuffer.size
                    if (size > 0) {
                        outputStream.write(sendBuffer.pop(size))
                        outputStream.flush()

                        Log.i("Connection", "Sent $size bytes")
                    }

                    Thread.sleep(50) // just throttle down a bit
                }
            } catch (e: SocketException) {
                // socket is disconnected, will notify from read loop
            } catch (e: Exception) {
                Log.e("Connection", "Exception in send loop", e)
            }
        }
    }
}
