package com.mastercard.gateway.ats

import com.mastercard.gateway.ats.domain.ATSMessage
import com.mastercard.gateway.common.*
import java.io.Closeable

/**
 * Facilitates connecting and communicating to ATS
 */
class ATSClient : Closeable {

    companion object {
        internal const val CONNECTION_ATTEMPTS = 3
    }

    /**
     * Callback for messages from {@code ATSClient}
     */
    interface Callback {
        /**
         * Called when ATSClient successfully connects to ATS
         */
        fun onConnected()

        /**
         * Called when ATSClient is disconnected from ATS
         */
        fun onDisconnected()

        /**
         * Called when ATS sends a message
         *
         * @param message The parsed response from ATS
         */
        fun onMessageReceived(message: ATSMessage?)

        /**
         * Called when ATSClient encounters an error
         *
         * @param throwable The error ATSClient encountered
         */
        fun onError(throwable: Throwable)
    }

    internal lateinit var socketClient: SocketClient
    internal val callbacks = mutableListOf<Callback>()
    internal val readBuffer = Buffer()
    internal var closed = false

    /**
     * Starts the connection to an ATS instance
     *
     * @param ipAddress IP address of the ATS instance
     * @param port Port ATS is configured for listening to incoming messages
     */
    fun connect(ipAddress: String, port: Int) {
        closed = false

        "Connecting to ATS at $ipAddress:$port".log(this)
        socketClient = IPSocketClient(ipAddress, port).apply {
            addCallback(SocketCallback())
            connect(CONNECTION_ATTEMPTS)
        }
    }

    /**
     * Checks if the client is connected to ATS
     *
     * @return true if connected
     */
    fun isConnected(): Boolean {
        return socketClient.isConnected()
    }

    /**
     * Sends a raw XML message to ATS
     *
     * @param message XML payload encoded as a String
     */
    fun sendMessage(message: String) {
        "Sending raw XML message:\n$message".log(this)
        socketClient.write(Message(message).bytes)
    }

    /**
     * Serializes an ATSMessage to XML and sends it to ATS
     *
     * @param message The message to encode and send to ATS
     */
    fun sendMessage(message: ATSMessage) {
        val serializedMessage = Interpreter.serialize(message)
        "Sending ATS message:\n$serializedMessage".log(this)
        socketClient.write(Message(serializedMessage).bytes)
    }

    /**
     * Closes the connection to ATS
     */
    override fun close() {
        closed = true

        "Closing connection to ATS".log(this)
        socketClient.close()
    }

    /**
     * Registers a {@code ATSClient.Callback} for messages from ATSClient
     *
     * @param callback The Callback to be registered
     */
    fun addCallback(callback: Callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback)
        }
    }

    /**
     * Unregisters a {@code ATSClient.Callback}
     *
     * @param callback The Callback to be unregistered
     */
    fun removeCallback(callback: Callback) {
        callbacks.remove(callback)
    }

    internal inner class SocketCallback : SocketClient.Callback {

        override fun onConnected() {
            "Connected to ATS".log(this@ATSClient)

            callbacks.forEach { it.onConnected() }
        }

        override fun onRead(bytes: ByteArray) {
            readBuffer.put(bytes)

            // read the buffer for a complete message
            Message.read(readBuffer)?.let { message ->
                "Received message:\n${message.content}".log(this@ATSClient)
                callbacks.forEach { callback ->
                    callback.onMessageReceived(Interpreter.deserialize(message))
                }
            }
        }

        override fun onDisconnected() {
            "Disconnected from ATS".log(this@ATSClient)
            readBuffer.clear()
            callbacks.forEach { it.onDisconnected() }
        }

        override fun onError(throwable: Throwable) {
            // only want to notify the consumer of an error if client is not closed
            if (!closed) {
                "An error occurred while communicating with ATS".log(this@ATSClient, throwable)
                callbacks.forEach { it.onError(throwable) }
            }
        }
    }
}