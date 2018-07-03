package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal
import java.math.BigInteger

@Root(name = "DeviceResponse", strict = false)
@Order(elements = ["output", "input", "eventResult", "errorDetail"])
data class DeviceResponse(@Attribute(name = "RequestType", required = true) var requestType: DeviceRequestType,
                          @Attribute(name = "RequestID", required = true) var requestID: String,
                          @Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {

    @Element(name = "Output")
    var output: List<Output>? = null
    @Element(name = "Input")
    var input: DeviceResponse.Input? = null
    @Element(name = "EventResult")
    var eventResult: DeviceResponse.EventResult? = null
    @Element(name = "ErrorDetail")
    var errorDetail: ATSErrorDetailType? = null

    @Attribute(name = "ApplicationSender")
    var applicationSender: String? = null
    @Attribute(name = "WorkstationID")
    var workstationID: String? = null
    @Attribute(name = "TerminalID")
    var terminalID: String? = null
    @Attribute(name = "POPID")
    var popid: String? = null
    @Attribute(name = "SequenceID")
    var sequenceID: Int? = null
    @Attribute(name = "ReferenceRequestID")
    var referenceRequestID: String? = null


    @Order(elements = ["dispenser", "productCode", "modifiedRequest", "totalAmount", "saleItem", "acquirer", "authResponse", "transactionLimits", "oaCentreDetails"])
    class EventResult {

        @Element(name = "Dispenser", type = Short::class)
        var dispenser: List<Short>? = null
        @Element(name = "ProductCode")
        var productCode: List<BigInteger>? = null
        @Element(name = "ModifiedRequest")
        var modifiedRequest: CardRequestType? = null
        @Element(name = "TotalAmount")
        var totalAmount: TotalAmountType? = null
        @Element(name = "SaleItem")
        var saleItem: List<SaleItemType>? = null
        @Element(name = "Acquirer")
        var acquirer: Acquirer? = null
        @Element(name = "AuthResponse")
        var authResponse: AuthResponse? = null
        @Element(name = "TransactionLimits")
        var transactionLimits: TransactionLimits? = null
        @Element(name = "OACentreDetails")
        var oaCentreDetails: OACentreDetails? = null

        class Acquirer {
            @Attribute(name = "MerchantID")
            var merchantID: String? = null
            @Attribute(name = "AcquirerID")
            var acquirerID: String? = null
            @Attribute(name = "MerchantReference")
            var merchantReference: String? = null
            @Attribute(name = "CreditPlan")
            var creditPlan: String? = null
        }

        @Order(elements = ["authARC", "authCode", "authResult", "icc"])
        class AuthResponse {
            @Element(name = "AuthARC")
            var authARC: String? = null
            @Element(name = "AuthCode")
            var authCode: String? = null
            @Element(name = "AuthResult")
            var authResult: String? = null
            @Element(name = "ICC")
            var icc: ICCType? = null
        }

        @Order(elements = ["oaCentreName", "refundOnline", "refundOnlineCtls", "reversalMode", "reverseOnline", "iccNoTrack2"])
        class OACentreDetails {
            @Element(name = "OACentreName")
            var oaCentreName: String? = null
            @Element(name = "RefundOnline")
            var refundOnline: OARefundOnline? = null
            @Element(name = "RefundOnlineCtls")
            var refundOnlineCtls: OARefundOnline? = null
            @Element(name = "ReversalMode")
            var reversalMode: OAReversalMode? = null
            @Element(name = "ReverseOnline")
            var reverseOnline: OAReverseOnline? = null
            @Element(name = "ICCNoTrack2")
            var iccNoTrack2: OAICCNoTrack2? = null
        }

        @Order(elements = ["authFlags", "limitFloor", "limitPostComms", "ceiling", "ceilingCDCVM", "ceilingCash", "ceilingFPA", "readTLV"])
        class TransactionLimits {
            @Element(name = "AuthFlags")
            var authFlags: String? = null
            @Element(name = "LimitFloor")
            var limitFloor: BigDecimal? = null
            @Element(name = "LimitPostComms")
            var limitPostComms: BigDecimal? = null
            @Element(name = "Ceiling")
            var ceiling: BigDecimal? = null
            @Element(name = "CeilingCDCVM")
            var ceilingCDCVM: BigDecimal? = null
            @Element(name = "CeilingCash")
            var ceilingCash: BigDecimal? = null
            @Element(name = "CeilingFPA")
            var ceilingFPA: BigDecimal? = null
            @Element(name = "ReadTLV")
            var readTLV: BigInteger? = null
        }

    }

    @Order(elements = ["secureData", "inputValue"])
    data class Input(@Attribute(name = "InDeviceTarget", required = true) var inDeviceTarget: DeviceType,
                     @Attribute(name = "InResult", required = true) var inResult: RequestResultType) {

        @Element(name = "SecureData")
        var secureData: List<SecureDataFlow>? = null
        @Element(name = "InputValue")
        var inputValue: InputValueType? = null

    }

    data class Output(@Attribute(name = "OutDeviceTarget", required = true) var outDeviceTarget: DeviceType,
                      @Attribute(name = "OutResult", required = true) var outResult: RequestResultType)

}
