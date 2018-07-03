package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root
import java.math.BigDecimal
import java.math.BigInteger
import javax.xml.datatype.XMLGregorianCalendar

@Root(name = "ServiceRequest", strict = false)
@Order(elements = ["poSdata", "totalAmount", "agent", "pinPadProgramLoad", "privateData", "encryptionRSA"])

class ServiceRequest(@Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                     @Attribute(name = "WorkstationID", required = true) var workstationID: String,
                     @Attribute(name = "RequestID", required = true) var requestID: String) : ATSMessage {

    @Element(name = "POSdata")
    var poSdata: POSdata? = null
    @Element(name = "TotalAmount")
    var totalAmount: TotalAmount? = null
    @Element(name = "Agent")
    var agent: AgentOnlineType? = null
    @Element(name = "PINPadProgramLoad")
    var pinPadProgramLoad: PINPadProgramLoad? = null
    @Element(name = "PrivateData")
    var privateData: List<Any>? = null
    @Element(name = "EncryptionRSA")
    var encryptionRSA: String? = null
    @Attribute(name = "ApplicationSender")
    var applicationSender: String? = null
    @Attribute(name = "POPID")
    var popid: String? = null


    @Order(elements = ["forceApplication", "forceContactless", "forceFirmware", "forceOperatingSystem", "installEncrypt"])
    class PINPadProgramLoad {

        @Element(name = "ForceApplication")
        var forceApplication: Boolean? = null
        @Element(name = "ForceContactless")
        var forceContactless: Boolean? = null
        @Element(name = "ForceFirmware")
        var forceFirmware: Boolean? = null
        @Element(name = "ForceOperatingSystem")
        var forceOperatingSystem: Boolean? = null
        @Element(name = "InstallEncrypt")
        var installEncrypt: Boolean? = null

    }

    @Order(elements = ["posTimeStamp", "shiftNumber", "clerkID", "clerkPermission", "reference", "diagnosisMethod"])
    class POSdata(@Element(name = "POSTimeStamp", required = true) var posTimeStamp: XMLGregorianCalendar) {

        @Element(name = "ShiftNumber")
        var shiftNumber: BigInteger? = null
        @Element(name = "ClerkID")
        var clerkID: Int? = null
        @Element(name = "ClerkPermission")
        var clerkPermission: String? = null
        @Element(name = "Reference")
        var reference: String? = null
        @Element(name = "DiagnosisMethod")
        var diagnosisMethod: String? = null
        @Attribute(name = "LanguageCode")
        var languageCode: LanguageCodeType? = null

    }

    class TotalAmount(var value: BigDecimal) {
        @Attribute(name = "Currency")
        var currency: CurrencyCode? = null
    }

}
