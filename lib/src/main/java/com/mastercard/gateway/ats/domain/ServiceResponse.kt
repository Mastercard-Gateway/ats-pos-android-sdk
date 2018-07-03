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
@Order(elements = ["terminal", "errorDetail", "authorisation", "reconciliation", "submission", "diagnosisResult", "originalHeader", "privateData", "encryptionModulus", "encryptionExponent", "reference", "versions"])
data class ServiceResponse(@Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                           @Attribute(name = "WorkstationID", required = true) var workstationID: String,
                           @Attribute(name = "RequestID", required = true) var requestID: String,
                           @Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {


    @Element(name = "Terminal")
    var terminal: Terminal? = null
    @Element(name = "ErrorDetail")
    var errorDetail: ATSErrorDetailType? = null
    @Element(name = "Authorisation")
    var authorisation: Authorisation? = null
    @Element(name = "Reconciliation")
    var reconciliation: Reconciliation? = null
    @Element(name = "Submission")
    var submission: Submission? = null
    @Element(name = "DiagnosisResult")
    var diagnosisResult: String? = null
    @Element(name = "OriginalHeader")
    var originalHeader: OriginalHeader? = null
    @Element(name = "PrivateData")
    var privateData: List<Any>? = null
    @Element(name = "EncryptionModulus", type = String::class)
    var encryptionModulus: ByteArray? = null
    @Element(name = "EncryptionExponent", type = String::class)
    var encryptionExponent: ByteArray? = null
    @Element(name = "Reference")
    var reference: String? = null
    @Element(name = "Versions")
    var versions: List<Versions>? = null
    @Attribute(name = "ApplicationSender")
    var applicationSender: String? = null
    @Attribute(name = "POPID")
    var popid: String? = null


    data class Authorisation(@Attribute(name = "AcquirerID", required = true) var acquirerID: String,
                             @Attribute(name = "TimeStamp", required = true) var timeStamp: XMLGregorianCalendar) {
        @Attribute(name = "ApprovalCode")
        var approvalCode: String? = null
        @Attribute(name = "AcquirerBatch")
        var acquirerBatch: String? = null
    }

    data class OriginalHeader(@Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                              @Attribute(name = "WorkstationID", required = true) var workstationID: String,
                              @Attribute(name = "RequestID", required = true) var requestID: String,
                              @Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) {

        @Attribute(name = "ApplicationSender")
        var applicationSender: String? = null
        @Attribute(name = "POPID")
        var popid: String? = null
    }

    @Order(elements = ["totalAmount"])
    data class Reconciliation(@Element(name = "TotalAmount", required = true) var totalAmount: List<TotalAmount>) {

        @Attribute(name = "LanguageCode")
        var languageCode: LanguageCodeType? = null

        data class TotalAmount(var value: BigDecimal,
                               @Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger,
                               @Attribute(name = "PaymentType", required = true) var paymentType: TransactionType) {

            @Attribute(name = "Currency")
            var currency: CurrencyCode? = null
            @Attribute(name = "CardCircuit")
            var cardCircuit: String? = null
            @Attribute(name = "Acquirer")
            var acquirer: String? = null
            @Attribute(name = "NumberHeld")
            var numberHeld: BigInteger? = null
            @Attribute(name = "AmountHeld")
            var amountHeld: BigDecimal? = null
        }
    }

    @Order(elements = ["successful", "failed"])
    data class Submission(@Element(name = "Successful", required = true) var successful: Successful,
                          @Element(name = "Failed", required = true) var failed: Failed) {

        @Attribute(name = "LanguageCode")
        var languageCode: LanguageCodeType? = null

        data class Failed(var value: BigDecimal,
                          @Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger)

        data class Successful(var value: BigDecimal,
                              @Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger)
    }

    data class Terminal(@Attribute(name = "TerminalID", required = true) var terminalID: String) {
        @Attribute(name = "TerminalBatch")
        var terminalBatch: String? = null
        @Attribute(name = "STAN")
        var stan: Long? = null
    }

    @Order(elements = ["devices"])
    data class Versions(@Element(name = "Devices", required = true) var devices: List<Devices>) {

        @Order(elements = ["device"])
        data class Devices(@Element(name = "Device", required = true) var device: List<Device>) {

            @Order(elements = ["serialNumber", "timeStamp", "ptid", "manufacturer", "model", "applicationName", "applicationVersion", "contactlessKernelName", "contactlessKernelVersion", "firmwareName", "firmwareVersion", "osName", "osVersion", "encryptionModuleName", "encryptionModuleVersion", "product"])
            data class Device(@Attribute(name = "POPID", required = true) var popid: String) {

                @Element(name = "SerialNumber")
                var serialNumber: String? = null
                @Element(name = "TimeStamp")
                var timeStamp: XMLGregorianCalendar? = null
                @Element(name = "PTID")
                var ptid: String? = null
                @Element(name = "Manufacturer")
                var manufacturer: String? = null
                @Element(name = "Model")
                var model: String? = null
                @Element(name = "ApplicationName")
                var applicationName: String? = null
                @Element(name = "ApplicationVersion")
                var applicationVersion: String? = null
                @Element(name = "ContactlessKernelName")
                var contactlessKernelName: String? = null
                @Element(name = "ContactlessKernelVersion")
                var contactlessKernelVersion: String? = null
                @Element(name = "FirmwareName")
                var firmwareName: String? = null
                @Element(name = "FirmwareVersion")
                var firmwareVersion: String? = null
                @Element(name = "OSName")
                var osName: String? = null
                @Element(name = "OSVersion")
                var osVersion: String? = null
                @Element(name = "EncryptionModuleName")
                var encryptionModuleName: String? = null
                @Element(name = "EncryptionModuleVersion")
                var encryptionModuleVersion: String? = null
                @Element(name = "Product")
                var product: String? = null
            }

        }

    }

}
