package com.mastercard.ats.common

import android.os.Handler
import android.os.Message
import android.util.Log
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketException

class SocketConnection {

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

    private var socket: Socket = Socket()

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
        disconnect()
        socket = Socket()

        // start connection thread
        Thread(ConnectRunnable(ip, port)).start()
    }

    fun disconnect() {
        socket.close()
    }

    fun send(bytes: ByteArray) {
        sendBuffer.put(bytes)
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

    private inner class ConnectRunnable(val ip: String, val port: Int) : Runnable {

        override fun run() {
            try {
                // connect to the socket
                socket.connect(InetSocketAddress(ip, port))

                // start send thread
                Thread(SendRunnable()).start()

                // notify connected
                handler.sendEmptyMessage(EVENT_CONNECTED)

                // get input stream
                val inputStream = socket.getInputStream()
                val buffer = ByteArray(1024)

                // read loop
                while (true) {
                    val count = inputStream.read(buffer)
                    if (count < 0) break

                    Log.v(SocketConnection::class.java.simpleName, "Read $count bytes")

                    // notify data received
                    val msg = handler.obtainMessage()
                    msg.what = EVENT_DATA_RECEIVED
                    msg.obj = buffer.copyOf(count)
                    handler.sendMessage(msg)
                }
            } catch (e: SocketException) {
                // socket disconnected. event dispatched below
            } catch (e: Exception) {
                Log.e(SocketConnection::class.java.simpleName, "Error connecting to $ip:$port ", e)

                val msg = handler.obtainMessage()
                msg.what = EVENT_ERROR
                msg.obj = e
                handler.sendMessage(msg)
            } finally {
                disconnect()
                handler.sendEmptyMessage(EVENT_DISCONNECTED)
            }
        }
    }

    private inner class SendRunnable : Runnable {

        override fun run() {
            try {
                // get output stream
                val outputStream = socket.getOutputStream()

                while (!socket.isClosed) {
                    val size = sendBuffer.size
                    if (size > 0) {
                        outputStream.write(sendBuffer.pop(size))
                        outputStream.flush()

                        Log.v(SocketConnection::class.java.simpleName, "Sent $size bytes")
                    }

                    Thread.sleep(50) // just throttle down a bit
                }
            } catch (e: Exception) {
                Log.e(SocketConnection::class.java.simpleName, "Exception in send loop", e)
            } finally {
                disconnect()
            }
        }
    }
}