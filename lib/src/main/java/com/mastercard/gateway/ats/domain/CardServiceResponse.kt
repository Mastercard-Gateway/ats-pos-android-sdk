package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "CardServiceResponse", strict = false)
class CardServiceResponse {

    // required
    @field:Attribute(name = "RequestType")
    lateinit var requestType: CardRequestType

    @field:Attribute(name = "WorkstationID")
    lateinit var workstationId: String

    @field:Attribute(name = "RequestID")
    lateinit var requestId: String

    @field:Attribute(name = "OverallResult")
    lateinit var overallResult: String


    // optional
    @field:Attribute(name = "ApplicationSender", required = false)
    var applicationSender: String? = null

    @field:Attribute(name = "POPID", required = false)
    var popId: String? = null

    @field:Element(name = "Terminal", required = false)
    var terminal: Terminal? = null

    @field:Element(name = "Tender", required = false)
    var tender: Tender? = null
}