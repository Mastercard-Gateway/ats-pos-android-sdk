package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.math.BigDecimal
import java.util.ArrayList

@Root(name = "DeviceResponse", strict = false)
class DeviceResponse : ATSMessage {

    companion object {

        @JvmStatic
        fun createSuccessfulResponse(request: DeviceRequest) :DeviceResponse{
            return DeviceResponse().apply {
                requestID = request.requestID
                requestType = request.requestType
                workstationID = request.workstationID
                popid = request.popid
                applicationSender = request.applicationSender
                overallResult = RequestResultType.Success
                terminalID = request.terminalID
                sequenceID = request.sequenceID

                output = request.output?.map {
                    return@map DeviceResponse.Output().apply {
                        outDeviceTarget = it.outDeviceTarget
                        outResult = RequestResultType.Success
                    }
                }
            }
        }
    }

    @field:ElementList(name = "Output", required = false, inline = true, type = Output::class)
    var output: List<Output>? = null
    @field:Element(name = "Input", required = false)
    var input: DeviceResponse.Input? = null
    @field:Element(name = "EventResult", required = false)
    var eventResult: DeviceResponse.EventResult? = null
    @field:Element(name = "ErrorDetail", required = false)
    var errorDetail: ATSErrorDetailType? = null

    @field:Attribute(name = "ApplicationSender", required = false)
    var applicationSender: String? = null
    @field:Attribute(name = "WorkstationID", required = false)
    var workstationID: String? = null
    @field:Attribute(name = "TerminalID", required = false)
    var terminalID: String? = null
    @field:Attribute(name = "POPID", required = false)
    var popid: String? = null
    @field:Attribute(name = "SequenceID", required = false)
    var sequenceID: Int? = null
    @field:Attribute(name = "ReferenceRequestID", required = false)
    var referenceRequestID: String? = null
    @field:Attribute(name = "RequestType", required = true)
    lateinit var requestType: DeviceRequestType
    @field:Attribute(name = "RequestID", required = true)
    lateinit var requestID: String
    @field:Attribute(name = "OverallResult", required = true)
    lateinit var overallResult: RequestResultType

    @Root(name = "EventResult")
    class EventResult {

        @field:ElementList(name = "Dispenser", type = Short::class, required = false, inline = true)
        var dispenser: List<Short>? = null
        @field:ElementList(name = "ProductCode", required = false, inline = true)
        var productCode: List<Int>? = null
        @field:Element(name = "ModifiedRequest", required = false)
        var modifiedRequest: CardRequestType? = null
        @field:Element(name = "TotalAmount", required = false)
        var totalAmount: TotalAmountType? = null
        @field:ElementList(name = "SaleItem", required = false, inline = true)
        var saleItem: List<SaleItemType>? = null
        @field:Element(name = "Acquirer", required = false)
        var acquirer: Acquirer? = null
        @field:Element(name = "AuthResponse", required = false)
        var authResponse: AuthResponse? = null
        @field:Element(name = "TransactionLimits", required = false)
        var transactionLimits: TransactionLimits? = null
        @field:Element(name = "OACentreDetails", required = false)
        var oaCentreDetails: OACentreDetails? = null

        @Root(name = "Acquirer")
        class Acquirer {
            @field:Attribute(name = "MerchantID", required = false)
            var merchantID: String? = null
            @field:Attribute(name = "AcquirerID", required = false)
            var acquirerID: String? = null
            @field:Attribute(name = "MerchantReference", required = false)
            var merchantReference: String? = null
            @field:Attribute(name = "CreditPlan", required = false)
            var creditPlan: String? = null
        }

        @Root(name = "AuthResponse")
        class AuthResponse {
            @field:Element(name = "AuthARC", required = false)
            var authARC: String? = null
            @field:Element(name = "AuthCode", required = false)
            var authCode: String? = null
            @field:Element(name = "AuthResult", required = false)
            var authResult: String? = null
            @field:Element(name = "ICC", required = false)
            var icc: ICCType? = null
        }

        @Root(name = "OACentreDetails")
        class OACentreDetails {
            @field:Element(name = "OACentreName", required = false)
            var oaCentreName: String? = null
            @field:Element(name = "RefundOnline", required = false)
            var refundOnline: OARefundOnline? = null
            @field:Element(name = "RefundOnlineCtls", required = false)
            var refundOnlineCtls: OARefundOnline? = null
            @field:Element(name = "ReversalMode", required = false)
            var reversalMode: OAReversalMode? = null
            @field:Element(name = "ReverseOnline", required = false)
            var reverseOnline: OAReverseOnline? = null
            @field:Element(name = "ICCNoTrack2", required = false)
            var iccNoTrack2: OAICCNoTrack2? = null
        }

        @Root(name = "TransactionLimits")
        class TransactionLimits {
            @field:Element(name = "AuthFlags", required = false)
            var authFlags: String? = null
            @field:Element(name = "LimitFloor", required = false)
            var limitFloor: BigDecimal? = null
            @field:Element(name = "LimitPostComms", required = false)
            var limitPostComms: BigDecimal? = null
            @field:Element(name = "Ceiling", required = false)
            var ceiling: BigDecimal? = null
            @field:Element(name = "CeilingCDCVM", required = false)
            var ceilingCDCVM: BigDecimal? = null
            @field:Element(name = "CeilingCash", required = false)
            var ceilingCash: BigDecimal? = null
            @field:Element(name = "CeilingFPA", required = false)
            var ceilingFPA: BigDecimal? = null
            @field:Element(name = "ReadTLV", required = false)
            var readTLV: Int? = null
        }

    }

    @Root(name = "Input")
    class Input {

        @field:ElementList(name = "SecureData", required = false, inline = true)
        var secureData: List<SecureDataFlow>? = null
        @field:Element(name = "InputValue", required = false)
        var inputValue: InputValueType? = null
        @field:Attribute(name = "InDeviceTarget", required = true)
        lateinit var inDeviceTarget: DeviceType
        @field:Attribute(name = "InResult", required = true)
        lateinit var inResult: RequestResultType

    }

    @Root(name = "Output", strict = false)
    class Output {
        @field:Attribute(name = "OutDeviceTarget", required = true)
        lateinit var outDeviceTarget: DeviceType
        @field:Attribute(name = "OutResult", required = true)
        lateinit var outResult: RequestResultType
    }

}
