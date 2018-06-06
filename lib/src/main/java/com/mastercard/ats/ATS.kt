package com.mastercard.ats

import com.mastercard.ats.aci.Connection

/**
 * Manages the configuration of the ATS Server and creating new connections to execute transactions
 */
class ATS @JvmOverloads constructor(private val ipAddress: String, private var port: Int = 20002) {

    fun createConnection() : Connection = Connection(ipAddress, port)


}