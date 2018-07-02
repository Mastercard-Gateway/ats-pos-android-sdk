package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal
import java.math.BigInteger

@Root(name = "DeviceResponse", strict = false)
@Order(elements = ["output", "input", "eventResult", "errorDetail"])
data class DeviceResponse(@field:Element(name = "Output") var output: List<Output>? = null,
                          @field:Element(name = "Input") var input: DeviceResponse.Input? = null,
                          @field:Element(name = "EventResult") var eventResult: DeviceResponse.EventResult? = null,
                          @field:Element(name = "ErrorDetail") var errorDetail: ATSErrorDetailType? = null,
                          @field:Attribute(name = "RequestType", required = true) var requestType: DeviceRequestType,
                          @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
                          @field:Attribute(name = "WorkstationID") var workstationID: String? = null,
                          @field:Attribute(name = "TerminalID") var terminalID: String? = null,
                          @field:Attribute(name = "POPID") var popid: String? = null,
                          @field:Attribute(name = "RequestID", required = true) var requestID: String,
                          @field:Attribute(name = "SequenceID") var sequenceID: Int? = null,
                          @field:Attribute(name = "ReferenceRequestID") var referenceRequestID: String? = null,
                          @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {

    @Order(elements = ["dispenser", "productCode", "modifiedRequest", "totalAmount", "saleItem", "acquirer", "authResponse", "transactionLimits", "oaCentreDetails"])
    data class EventResult(@field:Element(name = "Dispenser", type = Short::class) var dispenser: List<Short>? = null,
                           @field:Element(name = "ProductCode") var productCode: List<BigInteger>? = null,
                           @field:Element(name = "ModifiedRequest") var modifiedRequest: CardRequestType? = null,
                           @field:Element(name = "TotalAmount") var totalAmount: TotalAmountType? = null,
                           @field:Element(name = "SaleItem") var saleItem: List<SaleItemType>? = null,
                           @field:Element(name = "Acquirer") var acquirer: Acquirer? = null,
                           @field:Element(name = "AuthResponse") var authResponse: AuthResponse? = null,
                           @field:Element(name = "TransactionLimits") var transactionLimits: TransactionLimits? = null,
                           @field:Element(name = "OACentreDetails") var oaCentreDetails: OACentreDetails? = null) {

        data class Acquirer(@field:Attribute(name = "MerchantID") var merchantID: String? = null,
                            @field:Attribute(name = "AcquirerID") var acquirerID: String? = null,
                            @field:Attribute(name = "MerchantReference") var merchantReference: String? = null,
                            @field:Attribute(name = "CreditPlan") var creditPlan: String? = null)

        @Order(elements = ["authARC", "authCode", "authResult", "icc"])
        data class AuthResponse(@field:Element(name = "AuthARC") var authARC: String? = null,
                                @field:Element(name = "AuthCode") var authCode: String? = null,
                                @field:Element(name = "AuthResult") var authResult: String? = null,
                                @field:Element(name = "ICC") var icc: ICCType? = null)

        @Order(elements = ["oaCentreName", "refundOnline", "refundOnlineCtls", "reversalMode", "reverseOnline", "iccNoTrack2"])
        data class OACentreDetails(@field:Element(name = "OACentreName") var oaCentreName: String? = null,
                                   @field:Element(name = "RefundOnline") var refundOnline: OARefundOnline? = null,
                                   @field:Element(name = "RefundOnlineCtls") var refundOnlineCtls: OARefundOnline? = null,
                                   @field:Element(name = "ReversalMode") var reversalMode: OAReversalMode? = null,
                                   @field:Element(name = "ReverseOnline") var reverseOnline: OAReverseOnline? = null,
                                   @field:Element(name = "ICCNoTrack2") var iccNoTrack2: OAICCNoTrack2? = null)

        @Order(elements = ["authFlags", "limitFloor", "limitPostComms", "ceiling", "ceilingCDCVM", "ceilingCash", "ceilingFPA", "readTLV"])
        data class TransactionLimits(@field:Element(name = "AuthFlags") var authFlags: String? = null,
                                     @field:Element(name = "LimitFloor") var limitFloor: BigDecimal? = null,
                                     @field:Element(name = "LimitPostComms") var limitPostComms: BigDecimal? = null,
                                     @field:Element(name = "Ceiling") var ceiling: BigDecimal? = null,
                                     @field:Element(name = "CeilingCDCVM") var ceilingCDCVM: BigDecimal? = null,
                                     @field:Element(name = "CeilingCash") var ceilingCash: BigDecimal? = null,
                                     @field:Element(name = "CeilingFPA") var ceilingFPA: BigDecimal? = null,
                                     @field:Element(name = "ReadTLV") var readTLV: BigInteger? = null)

    }

    @Order(elements = ["secureData", "inputValue"])
    data class Input(@field:Element(name = "SecureData") var secureData: List<SecureDataFlow>? = null,
                     @field:Element(name = "InputValue") var inputValue: InputValueType? = null,
                     @field:Attribute(name = "InDeviceTarget", required = true) var inDeviceTarget: DeviceType,
                     @field:Attribute(name = "InResult", required = true) var inResult: RequestResultType)

    data class Output(@Attribute(name = "OutDeviceTarget", required = true) var outDeviceTarget: DeviceType,
                      @Attribute(name = "OutResult", required = true) var outResult: RequestResultType)

}
