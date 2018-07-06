package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.*
import java.math.BigDecimal
 import java.util.Date

@Suppress("ArrayInDataClass")
@Root(name = "ServiceResponse", strict = false)
class ServiceResponse : ATSMessage {

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
    @field:Attribute(name = "RequestType", required = true)
    lateinit var requestType: ServiceRequestType
    @field:Attribute(name = "WorkstationID", required = true)
    lateinit var workstationID: String
    @field:Attribute(name = "RequestID", required = true)
    lateinit var requestID: String
    @field:Attribute(name = "OverallResult", required = true)
    lateinit var overallResult: RequestResultType


    class Authorisation {
        @field:Attribute(name = "ApprovalCode", required = false)
        var approvalCode: String? = null
        @field:Attribute(name = "AcquirerBatch", required = false)
        var acquirerBatch: String? = null
        @field:Attribute(name = "AcquirerID", required = true)
        lateinit var acquirerID: String
        @field:Attribute(name = "TimeStamp", required = true)
        lateinit var timeStamp: Date
    }

    class OriginalHeader {

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
        @field:Attribute(name = "OverallResult", required = true)
        lateinit var overallResult: RequestResultType
    }

    class Reconciliation {

        @field:ElementList(name = "TotalAmount", required = true)
        lateinit var totalAmount: List<TotalAmount>
        @field:Attribute(name = "LanguageCode", required = false)
        var languageCode: LanguageCodeType? = null

        class TotalAmount {

            @field:Text
            lateinit var value: BigDecimal
            @field:Attribute(name = "NumberPayments", required = true)
            lateinit var numberPayments: Integer
            @field:Attribute(name = "PaymentType", required = true)
            lateinit var paymentType: TransactionType
            @field:Attribute(name = "Currency", required = false)
            var currency: CurrencyCode? = null
            @field:Attribute(name = "CardCircuit", required = false)
            var cardCircuit: String? = null
            @field:Attribute(name = "Acquirer", required = false)
            var acquirer: String? = null
            @field:Attribute(name = "NumberHeld", required = false)
            var numberHeld: Integer? = null
            @field:Attribute(name = "AmountHeld", required = false)
            var amountHeld: BigDecimal? = null
        }
    }

    class Submission {

        @field:Element(name = "Successful", required = true)
        lateinit var successful: Successful
        @field:Element(name = "Failed", required = true)
        lateinit var failed: Failed
        @field:Attribute(name = "LanguageCode", required = false)
        var languageCode: LanguageCodeType? = null

        class Failed {
            @field:Text
            lateinit var value: BigDecimal
            @field:Attribute(name = "NumberPayments", required = true)
            lateinit var numberPayments: Integer
        }

        class Successful {
            @field:Text
            lateinit var value: BigDecimal
            @field:Attribute(name = "NumberPayments", required = true)
            lateinit var numberPayments: Integer
        }
    }

    class Terminal {
        @field:Attribute(name = "TerminalID", required = true)
        lateinit var terminalID: String
        @field:Attribute(name = "TerminalBatch", required = false)
        var terminalBatch: String? = null
        @field:Attribute(name = "STAN", required = false)
        var stan: Long? = null
    }

    class Versions {

        @field:ElementList(name = "Devices", required = true)
        lateinit var devices: List<Devices>

        class Devices {

            @field:ElementList(name = "Device", required = true)
            lateinit var device: List<Device>

            class Device {
                @field:Element(name = "SerialNumber", required = false)
                var serialNumber: String? = null
                @field:Element(name = "TimeStamp", required = false)
                var timeStamp: Date? = null
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
                @field:Attribute(name = "POPID", required = true)
                lateinit var popid: String
            }

        }

    }

}
