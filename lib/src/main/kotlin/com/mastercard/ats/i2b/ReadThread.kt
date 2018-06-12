package com.mastercard.ats.i2b

import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream

/**
 * ReadThread is responsible for maintaining the BTConnection and receiving incoming data through input streams respectively.
 */
class ReadThread(private val inputStream: InputStream, private val handler: Handler) : Thread() {

    private val TAG = "BTConnectionService"
    private val SUB_TAG = this::class.java.simpleName

    override fun run() {

        val buffer = ByteArray(1024)  // buffer store for the stream

        var bytes: Int // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (!isInterrupted) {

            try {

                bytes = inputStream.read(buffer)

                Log.d(TAG, ">>>>>>>>>>>> $bytes byes Received <<<<<<<<<<<<<<<<<")
                Log.d(TAG, ">>>>>>>>>>>> Message read from InputStream: ${String(buffer, 0, bytes)} <<<<<<<<<<<<<<<<<")

            } catch (e: IOException) {
                Log.e(TAG, "$SUB_TAG : Error reading Input Stream. " + e.message)
                notifyReadMessageFailure()
                break
            }

            //notify the incoming message
            notifyReadMessageSuccess(buffer)
        }
    }

    private fun notifyReadMessageSuccess(data: ByteArray) {
        val msg = handler.obtainMessage(BluetoothConnectionService.MSG_READ_SUCCESS, data)
        handler.sendMessage(msg)
    }

    private fun notifyReadMessageFailure() {
        val msg = handler.obtainMessage(BluetoothConnectionService.MSG_READ_FAILURE)
        handler.sendMessage(msg)
    }

    fun isRunning(): Boolean {
        return isAlive && !isInterrupted
    }

    fun cancel() {
        this.interrupt()
        try {
            inputStream.close()
        } catch (e: IOException) {
            Log.e(TAG, "$SUB_TAG :  close() of bluetooth socket inputStream failed")
        }

    }
}