package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.*
import java.math.BigDecimal
import java.util.*

@Root(name = "ServiceRequest")
class ServiceRequest : ATSMessage {

    @field:Element(name = "POSdata", required = false)
    var poSdata: POSdata? = null
    @field:Element(name = "TotalAmount", required = false)
    var totalAmount: TotalAmount? = null
    @field:Element(name = "Agent", required = false)
    var agent: AgentOnlineType? = null
    @field:Element(name = "PINPadProgramLoad", required = false)
    var pinPadProgramLoad: PINPadProgramLoad? = null
    @field:ElementList(name = "PrivateData", required = false, inline = true)
    var privateData: List<Any>? = null
    @field:Element(name = "EncryptionRSA", required = false)
    var encryptionRSA: String? = null
    @field:Attribute(name = "ApplicationSender", required = false)
    var applicationSender: String? = null
    @field:Attribute(name = "POPID", required = false)
    var popid: String? = null
    @field:Attribute(name = "RequestType", required = true)
    lateinit var requestType: ServiceRequestType
    @field:Attribute(name = "WorkstationID", required = true)
    lateinit var workstationID: String
    @field:Attribute(name = "RequestID", required = true)
    lateinit var requestID: String


    class PINPadProgramLoad {

        @field:Element(name = "ForceApplication", required = false)
        var forceApplication: Boolean? = null
        @field:Element(name = "ForceContactless", required = false)
        var forceContactless: Boolean? = null
        @field:Element(name = "ForceFirmware", required = false)
        var forceFirmware: Boolean? = null
        @field:Element(name = "ForceOperatingSystem", required = false)
        var forceOperatingSystem: Boolean? = null
        @field:Element(name = "InstallEncrypt", required = false)
        var installEncrypt: Boolean? = null

    }


    class POSdata {

        @field:Element(name = "POSTimeStamp", required = true)
        lateinit var posTimeStamp: Date
        @field:Element(name = "ShiftNumber", required = false)
        var shiftNumber: Int? = null
        @field:Element(name = "ClerkID", required = false)
        var clerkID: Int? = null
        @field:Element(name = "ClerkPermission", required = false)
        var clerkPermission: String? = null
        @field:Element(name = "Reference", required = false)
        var reference: String? = null
        @field:Element(name = "DiagnosisMethod", required = false)
        var diagnosisMethod: String? = null
        @field:Attribute(name = "LanguageCode", required = false)
        var languageCode: LanguageCodeType? = null

    }

    class TotalAmount {
        @field:Text
        lateinit var value: BigDecimal
        @field:Attribute(name = "Currency", required = false)
        var currency: CurrencyCode? = null
    }

}
