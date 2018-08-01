package com.mastercard.gateway.ats

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class is used to help capture logs from the ATS SDK.
 * Each component of the SDK outputs log statements to the singleton
 * instance of this class. You can capture those statements by calling
 * {@link #startLogCapture() startLogCapture()}
 *
 * Typical use case:
 *
 * ATSDiagnostics.setLogLevel(Log.VERBOSE);
 * ATSDiagnostics.startLogCapture();
 *
 * // ... perform transaction with ATS
 *
 * ATSDiagnostics.stopLogCapture();
 *
 * String log = ATSDiagnostics.getLog();
 * ATSDiagnostics.clearLog();
 */
object ATSDiagnostics {

    private var logLevel: Int = Log.DEBUG
    private var capturing: Boolean = false
    private val log = StringBuffer()
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

    /**
     * Returns the current log level being recorded
     *
     * @return The log level
     * @see android.util.Log
     */
    @JvmStatic
    fun getLogLevel(): Int {
        return logLevel
    }

    /**
     * Sets the log level to record
     *
     * @param level The log level
     * @see android.util.Log
     */
    @JvmStatic
    fun setLogLevel(level: Int) {
        logLevel = level
    }

    /**
     * Starts capturing log messages to the internal buffer.
     */
    @JvmStatic
    fun startLogCapture() {
        capturing = true
    }

    /**
     * Stops capturing log messages
     */
    @JvmStatic
    fun stopLogCapture() {
        capturing = false
    }

    /**
     * Returns the current log stack
     *
     * @return The current log
     */
    @JvmStatic
    fun getLog(): String {
        return log.toString()
    }

    /**
     * Clears the internal log buffer
     */
    @JvmStatic
    fun clearLog() {
        log.setLength(0)
    }

    internal fun log(priority: Int, tag: String, message: String) {
        if (capturing && priority >= logLevel) {
            val timestamp = dateFormatter.format(Date())
            log.append("$timestamp ${resolvePriorityLetter(priority)}/$tag: $message\n")
        }
    }

    internal fun resolvePriorityLetter(priority: Int): Char {
        return when (priority) {
            Log.VERBOSE -> 'V'
            Log.DEBUG -> 'D'
            Log.INFO -> 'I'
            Log.ASSERT -> 'A'
            Log.WARN -> 'W'
            Log.ERROR -> 'E'
            else -> '?'
        }
    }
}