package com.mastercard.ats.i2b

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Handler
import android.util.Log
import java.io.InputStream
import java.io.OutputStream

@SuppressLint("MissingPermission")
class BluetoothConnectionService {

    companion object {

        @JvmStatic
        val MSG_CONNECT_SUCCESS = 1
        @JvmStatic
        val MSG_CONNECT_ERROR = 2
        @JvmStatic
        val MSG_READ_SUCCESS = 3
        @JvmStatic
        val MSG_READ_FAILURE = 4
        @JvmStatic
        val MSG_SEND_SUCCESS = 5
        @JvmStatic
        val MSG_SEND_FAILURE = 6
    }

    private val TAG = "BTConnectionService"

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var state = BluetoothConnectionState.DISCONNECTED
    private var connectionThread: ConnectionThread? = null
    private var readThread: ReadThread? = null
    private var writeThread: WriteThread? = null

    private enum class BluetoothConnectionState {
        DISCONNECTED, CONNECTING, CONNECTED
    }

    private val handlerCallback = Handler.Callback { msg ->

        when (msg?.what) {

            MSG_CONNECT_SUCCESS -> {
                setState(BluetoothConnectionState.CONNECTED)

                val (inputStream, outputStream) = msg.obj as (Pair<InputStream, OutputStream>)

                startReaderThread(inputStream)

                startWriterThread(outputStream)

                // TODO : callbacks
                println("MSG_CONNECT_SUCCESS !!!!! ")

            }

            MSG_CONNECT_ERROR -> {

                println("MSG_CONNECT_ERROR !!!!! ")

                setState(BluetoothConnectionState.DISCONNECTED)

                val errorMessage = msg.obj as String
                // TODO : callbacks
            }

            MSG_SEND_SUCCESS -> {
                println("MSG_SEND_SUCCESS !!!")
                // TODO : callbacks
            }

            MSG_READ_SUCCESS -> {
                val data = msg.obj as ByteArray
                println("MSG_READ_SUCCESS !!! Message is >>>>>>>> : ${String(data)}")
                // TODO : callbacks
            }

            MSG_READ_FAILURE, MSG_SEND_FAILURE -> {
                println("MSG_READ_FAILURE or MSG_SEND_FAILURE !!!")
                // TODO : callbacks
                disconnect()
            }

            else -> Unit

        }
        true
    }

    fun startReaderThread(inputStream: InputStream) {
        readThread = ReadThread(inputStream = inputStream, handler = handler)
        readThread?.start()
    }

    fun startWriterThread(outputStream: OutputStream) {
        writeThread = WriteThread(outputStream = outputStream, handler = handler)
        writeThread?.start()
    }

    private val handler = Handler(handlerCallback)

    private fun setState(state: BluetoothConnectionState) {
        this.state = state
    }

    private fun getState(): BluetoothConnectionState {
        return state
    }

    fun isConnecting(): Boolean {
        //return mConnectThread != null && mConnectThread.isAlive() && !mConnectThread.isInterrupted()
        return false
    }

    fun isConnected(): Boolean {
        return connectionThread?.isConnected() ?: false
    }

    fun connectTo(deviceName: String, secure: Boolean, attempts: Int) {
        Log.d(TAG, "connectTo $deviceName")

        // likely going to connect to first matching device found
        val bondedDevices = bluetoothAdapter.bondedDevices

        val device = try {
            bondedDevices.first { deviceName == it.name }
        } catch (exception: NoSuchElementException) {
            null
        }

        when (device) {
            null -> throw DeviceNotFoundException("No device found with name: $deviceName")
            else -> connect(device, secure, attempts)
        }
    }


    fun connect(device: BluetoothDevice, secure: Boolean, attempts: Int) {

        Log.d(TAG, "connect $device")

        // disconnect from current device if already connected or trying to connect
        if (isConnected() || isConnecting()) {
            disconnect()
        }

        if (getState() !== BluetoothConnectionState.CONNECTING) {
            setState(BluetoothConnectionState.CONNECTING)
        }

        Log.d(TAG, "Attempting to connect to " + device.name)

        // Always cancel discovery because it will slow down a connection
        bluetoothAdapter.cancelDiscovery()

        // start connection thread
        connectionThread = ConnectionThread(device, secure, attempts, handler)
        connectionThread?.start()
    }

    fun disconnect() {

        bluetoothAdapter.cancelDiscovery()

        if (connectionThread?.isConnected() == true) {
            connectionThread?.disconnect()
        }

        if (readThread?.isRunning() == true) {
            readThread?.cancel()
        }

        if (writeThread?.isRunning() == true) {
            writeThread?.cancel()
        }

        if (getState() != BluetoothConnectionState.DISCONNECTED) {
            setState(BluetoothConnectionState.DISCONNECTED)
        }

        // TODO : callbacks
    }

}