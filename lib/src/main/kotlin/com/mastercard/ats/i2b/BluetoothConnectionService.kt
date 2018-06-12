package com.mastercard.ats.i2b

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Handler
import android.util.Log

@SuppressLint("MissingPermission")
class BluetoothConnectionService {

    companion object {

        @JvmStatic
        val MSG_CONNECT_SUCCESS = 1
        @JvmStatic
        val MSG_CONNECT_ERROR = 2
    }

    private val TAG = "BTConnectionService"

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var state = BluetoothConnectionState.DISCONNECTED
    private var connectionThread: ConnectionThread? = null

    private enum class BluetoothConnectionState {
        DISCONNECTED, CONNECTING, CONNECTED
    }

    private val handlerCallback = Handler.Callback { msg ->

        when (msg?.what) {

            MSG_CONNECT_SUCCESS -> {
                setState(BluetoothConnectionState.CONNECTED)

                println("MSG_CONNECT_SUCCESS !!!!! ")

            }

            MSG_CONNECT_ERROR -> {

                println("MSG_CONNECT_ERROR !!!!! ")

                setState(BluetoothConnectionState.DISCONNECTED)

                val errorMessage = msg.obj as String
                /*for (listener in getListeners()) {
                    listener.onConnectError(errorMessage)
                }*/
            }

        }
        true
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
        //return mSocket != null && mSocket.isConnected()
        return false
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
            connectionThread?.interrupt()
            connectionThread?.disconnect()
        }

        if (getState() != BluetoothConnectionState.DISCONNECTED) {
            setState(BluetoothConnectionState.DISCONNECTED)
        }

    }

}