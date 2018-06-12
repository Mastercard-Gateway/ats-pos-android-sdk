package com.mastercard.ats.i2b

import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.OutputStream

class WriteThread(private val outputStream: OutputStream, private val handler: Handler) : Thread() {

    private val TAG = "BTConnectionService"
    private val SUB_TAG = this::class.java.simpleName
    private val dataQueue = arrayListOf<ByteArray>()

    fun write(data: ByteArray) {
        dataQueue.add(data)
    }

    fun cancel() {
        this.interrupt()
        try {
            outputStream.close()
        } catch (e: IOException) {
            Log.e(TAG, "$SUB_TAG :  close() of bluetooth socket inputStream failed")
        }
    }

    override fun run() {

        while (!isInterrupted) {

            if (dataQueue.size > 0) {

                try {

                    val bytes = dataQueue.removeAt(0)
                    outputStream.write(bytes)

                } catch (e: IOException) {
                    Log.e(TAG, "$SUB_TAG : Error writing to output stream.")
                    //notify the failure of sending the message
                    notifySendMessageFailure()
                }

                //notify the success of sending the message
                notifySendMessageSuccess()
            }

        }
    }

    private fun notifySendMessageSuccess() {
        val msg = handler.obtainMessage(BluetoothConnectionService.MSG_SEND_SUCCESS)
        handler.sendMessage(msg)
    }

    private fun notifySendMessageFailure() {
        val msg = handler.obtainMessage(BluetoothConnectionService.MSG_SEND_FAILURE)
        handler.sendMessage(msg)
    }

    fun isRunning(): Boolean {
        return isAlive && !isInterrupted
    }

}