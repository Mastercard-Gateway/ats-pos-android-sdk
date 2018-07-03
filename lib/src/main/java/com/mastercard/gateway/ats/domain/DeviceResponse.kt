package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal
import java.math.BigInteger

@Root(name = "DeviceResponse", strict = false)
@Order(elements = ["output", "input", "eventResult", "errorDetail"])
data class DeviceResponse(@field:Attribute(name = "RequestType", required = true) var requestType: DeviceRequestType,
                          @field:Attribute(name = "RequestID", required = true) var requestID: String,
                          @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {

    @field:Element(name = "Output", required=false)
    var output: List<Output>? = null
    @field:Element(name = "Input", required=false)
    var input: DeviceResponse.Input? = null
    @field:Element(name = "EventResult", required=false)
    var eventResult: DeviceResponse.EventResult? = null
    @field:Element(name = "ErrorDetail", required=false)
    var errorDetail: ATSErrorDetailType? = null

    @field:Attribute(name = "ApplicationSender", required=false)
    var applicationSender: String? = null
    @field:Attribute(name = "WorkstationID", required=false)
    var workstationID: String? = null
    @field:Attribute(name = "TerminalID", required=false)
    var terminalID: String? = null
    @field:Attribute(name = "POPID", required=false)
    var popid: String? = null
    @field:Attribute(name = "SequenceID", required=false)
    var sequenceID: Int? = null
    @field:Attribute(name = "ReferenceRequestID", required=false)
    var referenceRequestID: String? = null


    @Order(elements = ["dispenser", "productCode", "modifiedRequest", "totalAmount", "saleItem", "acquirer", "authResponse", "transactionLimits", "oaCentreDetails"])
    class EventResult {

        @field:Element(name = "Dispenser", type = Short::class, required=false)
        var dispenser: List<Short>? = null
        @field:Element(name = "ProductCode", required=false)
        var productCode: List<BigInteger>? = null
        @field:Element(name = "ModifiedRequest", required=false)
        var modifiedRequest: CardRequestType? = null
        @field:Element(name = "TotalAmount", required=false)
        var totalAmount: TotalAmountType? = null
        @field:Element(name = "SaleItem", required=false)
        var saleItem: List<SaleItemType>? = null
        @field:Element(name = "Acquirer", required=false)
        var acquirer: Acquirer? = null
        @field:Element(name = "AuthResponse", required=false)
        var authResponse: AuthResponse? = null
        @field:Element(name = "TransactionLimits", required=false)
        var transactionLimits: TransactionLimits? = null
        @field:Element(name = "OACentreDetails", required=false)
        var oaCentreDetails: OACentreDetails? = null

        class Acquirer {
            @field:Attribute(name = "MerchantID", required=false)
            var merchantID: String? = null
            @field:Attribute(name = "AcquirerID", required=false)
            var acquirerID: String? = null
            @field:Attribute(name = "MerchantReference", required=false)
            var merchantReference: String? = null
            @field:Attribute(name = "CreditPlan", required=false)
            var creditPlan: String? = null
        }

        @Order(elements = ["authARC", "authCode", "authResult", "icc"])
        class AuthResponse {
            @field:Element(name = "AuthARC", required=false)
            var authARC: String? = null
            @field:Element(name = "AuthCode", required=false)
            var authCode: String? = null
            @field:Element(name = "AuthResult", required=false)
            var authResult: String? = null
            @field:Element(name = "ICC", required=false)
            var icc: ICCType? = null
        }

        @Order(elements = ["oaCentreName", "refundOnline", "refundOnlineCtls", "reversalMode", "reverseOnline", "iccNoTrack2"])
        class OACentreDetails {
            @field:Element(name = "OACentreName", required=false)
            var oaCentreName: String? = null
            @field:Element(name = "RefundOnline", required=false)
            var refundOnline: OARefundOnline? = null
            @field:Element(name = "RefundOnlineCtls", required=false)
            var refundOnlineCtls: OARefundOnline? = null
            @field:Element(name = "ReversalMode", required=false)
            var reversalMode: OAReversalMode? = null
            @field:Element(name = "ReverseOnline", required=false)
            var reverseOnline: OAReverseOnline? = null
            @field:Element(name = "ICCNoTrack2", required=false)
            var iccNoTrack2: OAICCNoTrack2? = null
        }

        @Order(elements = ["authFlags", "limitFloor", "limitPostComms", "ceiling", "ceilingCDCVM", "ceilingCash", "ceilingFPA", "readTLV"])
        class TransactionLimits {
            @field:Element(name = "AuthFlags", required=false)
            var authFlags: String? = null
            @field:Element(name = "LimitFloor", required=false)
            var limitFloor: BigDecimal? = null
            @field:Element(name = "LimitPostComms", required=false)
            var limitPostComms: BigDecimal? = null
            @field:Element(name = "Ceiling", required=false)
            var ceiling: BigDecimal? = null
            @field:Element(name = "CeilingCDCVM", required=false)
            var ceilingCDCVM: BigDecimal? = null
            @field:Element(name = "CeilingCash", required=false)
            var ceilingCash: BigDecimal? = null
            @field:Element(name = "CeilingFPA", required=false)
            var ceilingFPA: BigDecimal? = null
            @field:Element(name = "ReadTLV", required=false)
            var readTLV: BigInteger? = null
        }

    }

    @Order(elements = ["secureData", "inputValue"])
    data class Input(@field:Attribute(name = "InDeviceTarget", required = true) var inDeviceTarget: DeviceType,
                     @field:Attribute(name = "InResult", required = true) var inResult: RequestResultType) {

        @field:Element(name = "SecureData", required=false)
        var secureData: List<SecureDataFlow>? = null
        @field:Element(name = "InputValue", required=false)
        var inputValue: InputValueType? = null

    }

    data class Output(@field:Attribute(name = "OutDeviceTarget", required = true) var outDeviceTarget: DeviceType,
                      @field:Attribute(name = "OutResult", required = true) var outResult: RequestResultType)

}
