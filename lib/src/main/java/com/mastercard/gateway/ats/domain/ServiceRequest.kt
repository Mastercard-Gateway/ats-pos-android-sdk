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
class ServiceRequest(@field:Element(name = "POSdata") var poSdata: POSdata? = null,
                     @field:Element(name = "TotalAmount") var totalAmount: TotalAmount? = null,
                     @field:Element(name = "Agent") var agent: AgentOnlineType? = null,
                     @field:Element(name = "PINPadProgramLoad") var pinPadProgramLoad: PINPadProgramLoad? = null,
                     @field:Element(name = "PrivateData") var privateData: List<Any>? = null,
                     @field:Element(name = "EncryptionRSA") var encryptionRSA: String? = null,
                     @field:Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                     @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
                     @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                     @field:Attribute(name = "POPID") var popid: String? = null,
                     @field:Attribute(name = "RequestID", required = true) var requestID: String) {

    @Order(elements = ["forceApplication", "forceContactless", "forceFirmware", "forceOperatingSystem", "installEncrypt"])
    class PINPadProgramLoad(@field:Element(name = "ForceApplication") var forceApplication: Boolean? = null,
                            @field:Element(name = "ForceContactless") var forceContactless: Boolean? = null,
                            @field:Element(name = "ForceFirmware") var forceFirmware: Boolean? = null,
                            @field:Element(name = "ForceOperatingSystem") var forceOperatingSystem: Boolean? = null,
                            @field:Element(name = "InstallEncrypt") var installEncrypt: Boolean? = null)

    @Order(elements = ["posTimeStamp", "shiftNumber", "clerkID", "clerkPermission", "reference", "diagnosisMethod"])
    class POSdata(@field:Element(name = "POSTimeStamp", required = true) var posTimeStamp: XMLGregorianCalendar,
                  @field:Element(name = "ShiftNumber") var shiftNumber: BigInteger? = null,
                  @field:Element(name = "ClerkID") var clerkID: Int? = null,
                  @field:Element(name = "ClerkPermission") var clerkPermission: String? = null,
                  @field:Element(name = "Reference") var reference: String? = null,
                  @field:Element(name = "DiagnosisMethod") var diagnosisMethod: String? = null,
                  @field:Attribute(name = "LanguageCode") var languageCode: LanguageCodeType? = null)

    class TotalAmount(var value: BigDecimal, @field:Attribute(name = "Currency") var currency: CurrencyCode? = null)

}
