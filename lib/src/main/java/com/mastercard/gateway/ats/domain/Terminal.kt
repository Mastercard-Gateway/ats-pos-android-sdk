package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "Terminal", strict = false)
class Terminal {

    // required
    @field:Attribute(name = "TerminalID")
    lateinit var terminalId: String


    // optional
    @field:Attribute(name = "TerminalBatch", required = false)
    var terminalBatch: String? = null

    @field:Attribute(name = "MerchantID", required = false)
    var merchantId: String? = null

    @field:Attribute(name = "STAN", required = false)
    var stan: Int? = null

    @field:Attribute(name = "APACS50TN", required = false)
    var apacs50tn: Int? = null
}