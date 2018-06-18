package com.mastercard.gateway.ats

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class ATSDiagnostics {

    companion object {

        internal var logLevel: Int = Log.DEBUG
        internal var capturing: Boolean = false
        internal val log = StringBuffer()
        internal val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

        @JvmStatic
        fun getLogLevel(): Int {
            return logLevel
        }

        @JvmStatic
        fun setLogLevel(level: Int) {
            logLevel = level
        }

        @JvmStatic
        fun startLogCapture() {
            capturing = true
        }

        @JvmStatic
        fun stopLogCapture(): String {
            capturing = false
            return getLog()
        }

        @JvmStatic
        fun getLog(): String {
            return log.toString()
        }

        @JvmStatic
        fun clearLog() {
            log.setLength(0)
        }

        internal fun log(priority: Int, tag: String, message: String) {
            if (capturing && priority >= logLevel) {
                val timestamp = dateFormatter.format(Date())
                log.append("$timestamp [$tag] $message\n")
            }
        }
    }
}