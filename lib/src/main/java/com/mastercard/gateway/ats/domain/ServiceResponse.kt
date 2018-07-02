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
data class ServiceResponse(@field:Element(name = "Terminal") var terminal: Terminal? = null,
                           @field:Element(name = "ErrorDetail") var errorDetail: ATSErrorDetailType? = null,
                           @field:Element(name = "Authorisation") var authorisation: Authorisation? = null,
                           @field:Element(name = "Reconciliation") var reconciliation: Reconciliation? = null,
                           @field:Element(name = "Submission") var submission: Submission? = null,
                           @field:Element(name = "DiagnosisResult") var diagnosisResult: String? = null,
                           @field:Element(name = "OriginalHeader") var originalHeader: OriginalHeader? = null,
                           @field:Element(name = "PrivateData") var privateData: List<Any>? = null,
                           @field:Element(name = "EncryptionModulus", type = String::class) var encryptionModulus: ByteArray? = null,
                           @field:Element(name = "EncryptionExponent", type = String::class) var encryptionExponent: ByteArray? = null,
                           @field:Element(name = "Reference") var reference: String? = null,
                           @field:Element(name = "Versions") var versions: List<Versions>? = null,
                           @field:Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                           @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
                           @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                           @field:Attribute(name = "POPID") var popid: String? = null,
                           @field:Attribute(name = "RequestID", required = true) var requestID: String,
                           @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) {


    data class Authorisation(@field:Attribute(name = "AcquirerID", required = true) var acquirerID: String,
                             @field:Attribute(name = "TimeStamp", required = true) var timeStamp: XMLGregorianCalendar,
                             @field:Attribute(name = "ApprovalCode") var approvalCode: String? = null,
                             @field:Attribute(name = "AcquirerBatch") var acquirerBatch: String? = null)


    data class OriginalHeader(@field:Attribute(name = "RequestType", required = true) var requestType: ServiceRequestType,
                              @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
                              @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                              @field:Attribute(name = "POPID") var popid: String? = null,
                              @field:Attribute(name = "RequestID", required = true) var requestID: String,
                              @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType)

    @Order(elements = arrayOf("totalAmount"))
    data class Reconciliation(@field:Element(name = "TotalAmount", required = true) var totalAmount: List<TotalAmount>,
                              @field:Attribute(name = "LanguageCode") var languageCode: LanguageCodeType? = null) {

        data class TotalAmount(var value: BigDecimal,
                               @field:Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger,
                               @field:Attribute(name = "PaymentType", required = true) var paymentType: TransactionType,
                               @field:Attribute(name = "Currency") var currency: CurrencyCode? = null,
                               @field:Attribute(name = "CardCircuit") var cardCircuit: String? = null,
                               @field:Attribute(name = "Acquirer") var acquirer: String? = null,
                               @field:Attribute(name = "NumberHeld") var numberHeld: BigInteger? = null,
                               @field:Attribute(name = "AmountHeld") var amountHeld: BigDecimal? = null)
    }

    @Order(elements = ["successful", "failed"])
    data class Submission(@field:Element(name = "Successful", required = true) var successful: Successful,
                          @field:Element(name = "Failed", required = true) var failed: Failed,
                          @field:Attribute(name = "LanguageCode") var languageCode: LanguageCodeType? = null) {

        data class Failed(var value: BigDecimal? = null,
                          @field:Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger)

        data class Successful(var value: BigDecimal? = null,
                              @field:Attribute(name = "NumberPayments", required = true) var numberPayments: BigInteger)
    }

    data class Terminal(@field:Attribute(name = "TerminalID", required = true) var terminalID: String,
                        @field:Attribute(name = "TerminalBatch") var terminalBatch: String? = null,
                        @field:Attribute(name = "STAN") var stan: Long? = null)

    @Order(elements = ["devices"])
    data class Versions(@field:Element(name = "Devices", required = true) var devices: List<Devices>) {

        @Order(elements = ["device"])
        data class Devices(@field:Element(name = "Device", required = true) var device: List<Device>) {

            @Order(elements = ["serialNumber", "timeStamp", "ptid", "manufacturer", "model", "applicationName", "applicationVersion", "contactlessKernelName", "contactlessKernelVersion", "firmwareName", "firmwareVersion", "osName", "osVersion", "encryptionModuleName", "encryptionModuleVersion", "product"])
            data class Device(@field:Element(name = "SerialNumber") var serialNumber: String? = null,
                              @field:Element(name = "TimeStamp") var timeStamp: XMLGregorianCalendar? = null,
                              @field:Element(name = "PTID") var ptid: String? = null,
                              @field:Element(name = "Manufacturer") var manufacturer: String? = null,
                              @field:Element(name = "Model") var model: String? = null,
                              @field:Element(name = "ApplicationName") var applicationName: String? = null,
                              @field:Element(name = "ApplicationVersion") var applicationVersion: String? = null,
                              @field:Element(name = "ContactlessKernelName") var contactlessKernelName: String? = null,
                              @field:Element(name = "ContactlessKernelVersion") var contactlessKernelVersion: String? = null,
                              @field:Element(name = "FirmwareName") var firmwareName: String? = null,
                              @field:Element(name = "FirmwareVersion") var firmwareVersion: String? = null,
                              @field:Element(name = "OSName") var osName: String? = null,
                              @field:Element(name = "OSVersion") var osVersion: String? = null,
                              @field:Element(name = "EncryptionModuleName") var encryptionModuleName: String? = null,
                              @field:Element(name = "EncryptionModuleVersion") var encryptionModuleVersion: String? = null,
                              @field:Element(name = "Product") var product: String? = null,
                              @field:Attribute(name = "POPID", required = true) var popid: String)

        }

    }

}
