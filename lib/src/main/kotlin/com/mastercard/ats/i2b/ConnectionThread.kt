package com.mastercard.ats.i2b

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/**
 * Finally the ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and
 * receiving incoming data through input/output streams respectively.
 */
@SuppressLint("MissingPermission")
class ConnectionThread(device: BluetoothDevice, secure: Boolean, maxAttemptsAllowed: Int, val handler: Handler) : Thread() {

    private val TAG = "BTConnectionService"
    private val SUB_TAG = this::class.java.simpleName
    private val MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
    private var socketType = if (secure) "Secure" else "Insecure"

    private var socket: BluetoothSocket? = null
    private var maxAttemptsAllowed = 1
    private var currentCountOfAttempts = 0

    init {
        this.maxAttemptsAllowed = Math.max(1, maxAttemptsAllowed)
        socket = getSocket(device, secure)
        if (socket == null)
            notifyConnectionError(RuntimeException("Socket of $socketType type couldn't be created"))
    }

    /**
     *  Returns a BluetoothSock for a connection to the provided BluetoothDevice
     */
    private fun getSocket(device: BluetoothDevice, secure: Boolean): BluetoothSocket? {

        try {

            return when (secure) {
                true -> device.createRfcommSocketToServiceRecord(MY_UUID)
                false -> device.createInsecureRfcommSocketToServiceRecord(MY_UUID)
            }

        } catch (e: IOException) {

            Log.e(TAG, "$SUB_TAG: IOException: " + e.message)
            return null
        }
    }

    /**
     *  Returns whether connected is alive
     */
    fun isConnected(): Boolean {
        return socket?.isConnected ?: false
    }

    /**
     *  Opens a connection to the provided bluetooth device
     */
    fun connect(): Boolean {

        Log.d(TAG, "$SUB_TAG : Calling connect() on socket Type $socketType ")

        try {
            socket?.connect()
        } catch (e: IOException) {
            Log.e(TAG, "Couldn't connect to the provided bluetooth device")
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     *  Opens a connection to the provided bluetooth device
     */
    //TODO:: this can't be the final solution. Need to analyse why connect() method isn't working
    fun fallBackSocketConnect(): Boolean {

        Log.d(TAG, "$SUB_TAG : Calling fallBackSocketConnect() on socket Type $socketType ")

        val temp = socket as BluetoothSocket
        val clazz = temp.remoteDevice.javaClass
        val paramTypes = arrayOf<Class<*>>(Integer.TYPE)
        val m = clazz.getMethod("createRfcommSocket", *paramTypes)
        val fallbackSocket = m.invoke(socket?.remoteDevice, Integer.valueOf(1)) as BluetoothSocket
        try {
            fallbackSocket.connect()
        } catch (e: Exception) {
            Log.e(TAG, "Couldn't connect to the provided bluetooth device")
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     *  Closes the opened connection to the provided bluetooth device
     */
    fun disconnect() {
        if (isConnected()) {
            Log.d(TAG, "$SUB_TAG :  Socket Type" + socketType + "getting disconnected ")
            try {
                socket?.close()
            } catch (e: IOException) {
                Log.e(TAG, "$SUB_TAG :  Socket Type" + socketType + "close() of server failed", e)
            }
        }
    }

    override fun run() {

        Log.d(TAG, "$SUB_TAG : Socket Type: $socketType ... ")

        var connected = false

        while (!isInterrupted && !connected && currentCountOfAttempts < maxAttemptsAllowed) {

            Log.d(TAG, "Attempt $currentCountOfAttempts trying to connect... ")

            currentCountOfAttempts++

//            connected = connect()
            connected = fallBackSocketConnect()
        }

        if (connected) {

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {

                inputStream = socket?.inputStream
                outputStream = socket?.outputStream

            } catch (e: IOException) {
                Log.d(TAG, "$SUB_TAG :  Getting input/output streams failed")
                notifyConnectionError(e)
            }

            if (inputStream != null && outputStream != null) {
                notifyConnectionSuccess(inputStream = inputStream, outputStream = outputStream)
            } else {
                notifyConnectionError(RuntimeException("Getting input/output streams as NULL"))
            }

        } else {
            notifyConnectionError(RuntimeException("Couldn't connect to the provided bluetooth device"))
            disconnect()
        }

    }

    private fun notifyConnectionError(e: Exception) {
        val msg = handler.obtainMessage(BluetoothConnectionService.MSG_CONNECT_ERROR, e.message)
        handler.sendMessage(msg)
    }

    private fun notifyConnectionSuccess(inputStream: InputStream, outputStream: OutputStream) {
        val msg = handler.obtainMessage(BluetoothConnectionService.MSG_CONNECT_SUCCESS, Pair(inputStream, outputStream))
        handler.sendMessage(msg)
    }
}