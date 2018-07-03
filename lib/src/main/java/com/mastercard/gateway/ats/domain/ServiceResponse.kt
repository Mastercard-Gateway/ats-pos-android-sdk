package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root
import java.math.BigDecimal
import java.math.BigInteger
import javax.xml.datatype.XMLGregorianCalendar

@Suppress("ArrayInDataClass")
@Root(name = "ServiceResponse", strict = false)
@Order(elements = ["Terminal", "ErrorDetail", "Authorisation", "Reconciliation", "Submission", "DiagnosisResult", "OriginalHeader", "PrivateData", "EncryptionModulus", "EncryptionExponent", "Reference", "Versions"])
data class ServiceResponse(@field:Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                           @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                           @field:Attribute(name = "RequestID", required = true) var requestID: String,
                           @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {

    @Deprecated("Required empty constructor for SimpleXML, do not use")
    constructor() : this(ServiceRequestType.AcquireDevice, "", "", RequestResultType.Aborted)


    @field:Element(name = "Terminal", required = false)
    var terminal: Terminal? = null
    @field:Element(name = "ErrorDetail", required = false)
    var errorDetail: ATSErrorDetailType? = null
    @field:Element(name = "Authorisation", required = false)
    var authorisation: Authorisation? = null
    @field:Element(name = "Reconciliation", required = false)
    var reconciliation: Reconciliation? = null
    @field:Element(name = "Submission", required = false)
    var submission: Submission? = null
    @field:Element(name = "DiagnosisResult", required = false)
    var diagnosisResult: String? = null
    @field:Element(name = "OriginalHeader", required = false)
    var originalHeader: OriginalHeader? = null
    @field:Element(name = "PrivateData", required = false)
    var privateData: List<Any>? = null
    @field:Element(name = "EncryptionModulus", type = String::class, required = false)
    var encryptionModulus: ByteArray? = null
    @field:Element(name = "EncryptionExponent", type = String::class, required = false)
    var encryptionExponent: ByteArray? = null
    @field:Element(name = "Reference", required = false)
    var reference: String? = null
    @field:Element(name = "Versions", required = false)
    var versions: List<Versions>? = null
    @field:Attribute(name = "ApplicationSender", required = false)
    var applicationSender: String? = null
    @field:Attribute(name = "POPID", required = false)
    var popid: String? = null


    data class Authorisation(@field:Attribute(name = "AcquirerID", required = true) var acquirerID: String,
                             @field:Attribute(name = "TimeStamp", required = true) var timeStamp: XMLGregorianCalendar) {
        @field:Attribute(name = "ApprovalCode", required = false)
        var approvalCode: String? = null
        @field:Attribute(name = "AcquirerBatch", required = false)
        var acquirerBatch: String? = null
    }

    data class OriginalHeader(@field:Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                              @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                              @field:Attribute(name = "RequestID", required = true) var requestID: String,
                              @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) {

        @field:Attribute(name = "ApplicationSender", required = false)
        var applicationSender: String? = null
        @field:Attribute(name = "POPID", required = false)
        var popid: String? = null
    }

    @Order(elements = ["TotalAmount"])
    data class Reconciliation(@field:Element(name = "TotalAmount", required = true) var totalAmount: List<TotalAmount>) {

        @field:Attribute(name = "LanguageCode", required = false)
        var languageCode: LanguageCodeType? = null

        data class TotalAmount(var value: BigDecimal,
                               @field:Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger,
                               @field:Attribute(name = "PaymentType", required = true) var paymentType: TransactionType) {

            @field:Attribute(name = "Currency", required = false)
            var currency: CurrencyCode? = null
            @field:Attribute(name = "CardCircuit", required = false)
            var cardCircuit: String? = null
            @field:Attribute(name = "Acquirer", required = false)
            var acquirer: String? = null
            @field:Attribute(name = "NumberHeld", required = false)
            var numberHeld: BigInteger? = null
            @field:Attribute(name = "AmountHeld", required = false)
            var amountHeld: BigDecimal? = null
        }
    }

    @Order(elements = ["Successful", "Failed"])
    data class Submission(@field:Element(name = "Successful", required = true) var successful: Successful,
                          @field:Element(name = "Failed", required = true) var failed: Failed) {

        @field:Attribute(name = "LanguageCode", required = false)
        var languageCode: LanguageCodeType? = null

        data class Failed(var value: BigDecimal,
                          @field:Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger)

        data class Successful(var value: BigDecimal,
                              @field:Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger)
    }

    data class Terminal(@field:Attribute(name = "TerminalID", required = true) var terminalID: String) {
        @field:Attribute(name = "TerminalBatch", required = false)
        var terminalBatch: String? = null
        @field:Attribute(name = "STAN", required = false)
        var stan: Long? = null
    }

    @Order(elements = ["Devices"])
    data class Versions(@field:Element(name = "Devices", required = true) var devices: List<Devices>) {

        @Order(elements = ["Device"])
        data class Devices(@field:Element(name = "Device", required = true) var device: List<Device>) {

            @Order(elements = ["SerialNumber", "TimeStamp", "PTID", "Manufacturer", "Model", "ApplicationName", "ApplicationVersion", "ContactlessKernelName", "ContactlessKernelVersion", "FirmwareName", "FirmwareVersion", "OSName", "OSVersion", "EncryptionModuleName", "EncryptionModuleVersion", "Product"])
            data class Device(@field:Attribute(name = "POPID", required = true) var popid: String) {

                @field:Element(name = "SerialNumber", required = false)
                var serialNumber: String? = null
                @field:Element(name = "TimeStamp", required = false)
                var timeStamp: XMLGregorianCalendar? = null
                @field:Element(name = "PTID", required = false)
                var ptid: String? = null
                @field:Element(name = "Manufacturer", required = false)
                var manufacturer: String? = null
                @field:Element(name = "Model", required = false)
                var model: String? = null
                @field:Element(name = "ApplicationName", required = false)
                var applicationName: String? = null
                @field:Element(name = "ApplicationVersion", required = false)
                var applicationVersion: String? = null
                @field:Element(name = "ContactlessKernelName", required = false)
                var contactlessKernelName: String? = null
                @field:Element(name = "ContactlessKernelVersion", required = false)
                var contactlessKernelVersion: String? = null
                @field:Element(name = "FirmwareName", required = false)
                var firmwareName: String? = null
                @field:Element(name = "FirmwareVersion", required = false)
                var firmwareVersion: String? = null
                @field:Element(name = "OSName", required = false)
                var osName: String? = null
                @field:Element(name = "OSVersion", required = false)
                var osVersion: String? = null
                @field:Element(name = "EncryptionModuleName", required = false)
                var encryptionModuleName: String? = null
                @field:Element(name = "EncryptionModuleVersion", required = false)
                var encryptionModuleVersion: String? = null
                @field:Element(name = "Product", required = false)
                var product: String? = null
            }

        }

    }

}
