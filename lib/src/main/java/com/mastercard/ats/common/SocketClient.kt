package com.mastercard.ats.common

import android.os.Handler
import android.os.Message
import android.util.Log
import java.net.Socket
import java.net.SocketException

class SocketClient {

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
    private val callbacks = mutableListOf<Callback>()
    private val sendBuffer = Buffer()

    val connected: Boolean
        get() {
            return socket?.run { return@run isConnected && !isClosed } == true
        }

    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    fun connect(ip: String, port: Int) {
        disconnect()

        // start connection thread
        Thread(Runnable { runConnect(ip, port) }).start()
    }

    fun disconnect() {
        socket?.close()
        socket = null
        sendBuffer.clear()
    }

    fun send(bytes: ByteArray) {
        if (connected) {
            sendBuffer.put(bytes)
        }
    }

    private fun handleCallback(msg: Message): Boolean {
        when {
            msg.what == EVENT_CONNECTED -> callbacks.forEach { it.connected() }
            msg.what == EVENT_DISCONNECTED -> callbacks.forEach { it.disconnected() }
            msg.what == EVENT_DATA_RECEIVED -> callbacks.forEach { it.dataReceived(msg.obj as ByteArray) }
            msg.what == EVENT_ERROR -> callbacks.forEach { it.error(msg.obj as Throwable) }
            else -> return false
        }

        return true
    }

    private fun runConnect(ip:String, port: Int) {
        try {
            // connect to the socket
            socket = Socket(ip, port)

            // start send thread
            Thread(Runnable { runSend() }).start()

            // notify connected
            handler.sendEmptyMessage(EVENT_CONNECTED)

            // get input stream
            val inputStream = socket!!.getInputStream()
            val buffer = ByteArray(1024)

            // read loop
            while (true) {
                val count = inputStream.read(buffer)
                if (count < 0) break

                Log.v(SocketClient::class.java.simpleName, "Read $count bytes")

                // notify data received
                val msg = handler.obtainMessage()
                msg.what = EVENT_DATA_RECEIVED
                msg.obj = buffer.copyOf(count)
                handler.sendMessage(msg)
            }
        } catch (e: SocketException) {
            // socket disconnected. event dispatched below
        } catch (e: Exception) {
            Log.e(SocketClient::class.java.simpleName, "Error connecting to $ip:$port ", e)

            val msg = handler.obtainMessage()
            msg.what = EVENT_ERROR
            msg.obj = e
            handler.sendMessage(msg)
        } finally {
            disconnect()
            handler.sendEmptyMessage(EVENT_DISCONNECTED)
        }
    }

    private fun runSend() {
        try {
            socket?.let {
                // get output stream
                val outputStream = it.getOutputStream()

                while (connected) {
                    val size = sendBuffer.size
                    if (size > 0) {
                        outputStream.write(sendBuffer.pop(size))
                        outputStream.flush()

                        Log.v(SocketClient::class.java.simpleName, "Sent $size bytes")
                    }

                    Thread.sleep(50) // just throttle down a bit
                }
            }
        } catch (e: Exception) {
            Log.e(SocketClient::class.java.simpleName, "Exception in send loop", e)
        } finally {
            disconnect()
        }
    }
}