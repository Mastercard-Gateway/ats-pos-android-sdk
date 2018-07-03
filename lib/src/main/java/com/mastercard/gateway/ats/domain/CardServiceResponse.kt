package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger

import javax.xml.datatype.XMLGregorianCalendar


@Root(name = "CardServiceResponse")
@Order(elements = ["terminal", "errorDetail", "tender", "loyalty", "saleItem", "originalHeader", "cardValue", "privateData"])
data class CardServiceResponse(@Attribute(name = "RequestType", required = true) var requestType: CardRequestType,
                               @Attribute(name = "WorkstationID", required = true) var workstationID: String,
                               @Attribute(name = "RequestID", required = true) var requestID: String,
                               @Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {

    @Element(name = "Terminal")
    var terminal: CardServiceResponse.Terminal? = null
    @Element(name = "ErrorDetail")
    var errorDetail: ATSErrorDetailType? = null
    @Element(name = "Tender")
    var tender: CardServiceResponse.Tender? = null
    @Element(name = "Loyalty")
    var loyalty: CardServiceResponse.Loyalty? = null
    @Element(name = "SaleItem")
    var saleItem: List<SaleItemType>? = null
    @Element(name = "OriginalHeader")
    var originalHeader: CardServiceResponse.OriginalHeader? = null
    @Element(name = "CardValue")
    var cardValue: CardValueICCType? = null
    @Element(name = "PrivateData")
    var privateData: List<String>? = null
    @Attribute(name = "ApplicationSender")
    var applicationSender: String? = null
    @Attribute(name = "POPID")
    var popid: String? = null

    @Order(elements = ["loyaltyCard", "loyaltyAmount", "loyaltyApprovalCode", "enabled", "customerName", "points", "bonus", "incentive", "updated"])
    data class Loyalty(@Attribute(name = "LoyaltyFlag", required = true) var loyaltyFlag: Boolean = false) {

        @Element(name = "LoyaltyCard")
        var loyaltyCard: CardServiceResponse.Loyalty.LoyaltyCard? = null
        @Element(name = "LoyaltyAmount")
        var loyaltyAmount: CardServiceResponse.Loyalty.LoyaltyAmount? = null
        @Element(name = "LoyaltyApprovalCode")
        var loyaltyApprovalCode: CardServiceResponse.Loyalty.LoyaltyApprovalCode? = null
        @Element(name = "Enabled")
        var enabled: Boolean? = null
        @Element(name = "CustomerName")
        var customerName: String? = null
        @Element(name = "Points")
        var points: BigInteger? = null
        @Element(name = "Bonus")
        var bonus: Boolean? = null
        @Element(name = "Incentive")
        var incentive: BigInteger? = null
        @Element(name = "Updated")
        var updated: XMLGregorianCalendar? = null
        @Attribute(name = "LoyaltyTimeStamp")
        var loyaltyTimeStamp: XMLGregorianCalendar? = null

        class LoyaltyAmount {
            var value: Float = 0f
            @Attribute(name = "OriginalLoyaltyAmount")
            var originalLoyaltyAmount: Float? = null
        }

        class LoyaltyApprovalCode {
            var value: String? = null
            @Attribute(name = "LoyaltyAcquirerID")
            var loyaltyAcquirerID: String? = null
            @Attribute(name = "LoyaltyAcquirerBatch")
            var loyaltyAcquirerBatch: String? = null
        }

        class LoyaltyCard : CardTrack() {
            @Attribute(name = "LoyaltyPAN")
            var loyaltyPAN: String? = null
        }
    }

    data class OriginalHeader(@Attribute(name = "RequestType", required = true) var requestType: CardRequestType,
                              @Attribute(name = "WorkstationID", required = true) var workstationID: String,
                              @Attribute(name = "RequestID", required = true) var requestID: String,
                              @Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) {

        @Attribute(name = "ApplicationSender")
        var applicationSender: String? = null
        @Attribute(name = "POPID")
        var popid: String? = null
    }

    @Order(elements = ["totalAmount", "authorisation", "restrictionCodes"])
    class Tender {

        @Element(name = "TotalAmount")
        var totalAmount: TotalAmountType? = null
        @Element(name = "Authorisation")
        var authorisation: Authorisation? = null
        @Element(name = "RestrictionCodes")
        var restrictionCodes: List<BigInteger>? = null
        @Attribute(name = "LanguageCode")
        var languageCode: LanguageCodeType? = null

        data class Authorisation(@Attribute(name = "AcquirerID", required = true) var acquirerID: String,
                                 @Attribute(name = "TimeStamp", required = true) var timeStamp: XMLGregorianCalendar) {

            @Attribute(name = "IssuerID")
            var issuerID: String? = null
            @Attribute(name = "CardPAN")
            var cardPAN: String? = null
            @Attribute(name = "StartDate")
            var startDate: String? = null
            @Attribute(name = "ExpiryDate")
            var expiryDate: String? = null
            @Attribute(name = "IssueNumber")
            var issueNumber: String? = null
            @Attribute(name = "Token")
            var token: String? = null
            @Attribute(name = "ActionCode")
            var actionCode: String? = null
            @Attribute(name = "ApprovalCode")
            var approvalCode: String? = null
            @Attribute(name = "AcquirerBatch")
            var acquirerBatch: String? = null
            @Attribute(name = "CardCircuit")
            var cardCircuit: String? = null
            @Attribute(name = "FiscalReceipt")
            var fiscalReceipt: Boolean? = null
            @Attribute(name = "TimeDisplay")
            var timeDisplay: Boolean? = null
            @Attribute(name = "LoyaltyAllowed")
            var loyaltyAllowed: Boolean? = null
            @Attribute(name = "ReceiptCopies")
            var receiptCopies: BigInteger? = null
            @Attribute(name = "Merchant")
            var merchant: String? = null
            @Attribute(name = "AuthorisationType")
            var authorisationType: AuthorisationMethodType? = null
            @Attribute(name = "ReceiptNumber")
            var receiptNumber: String? = null
            @Attribute(name = "CaptureReference")
            var captureReference: String? = null
            @Attribute(name = "TrackingReference")
            var trackingReference: String? = null
            @Attribute(name = "CardVerification")
            var cardVerification: CardVerificationType? = null
            @Attribute(name = "CV2Result")
            var cv2Result: AdditionalResponseType? = null
            @Attribute(name = "PostCodeResult")
            var postCodeResult: AdditionalResponseType? = null
            @Attribute(name = "AddressResult")
            var addressResult: AdditionalResponseType? = null
        }
    }

    data class Terminal(@Attribute(name = "TerminalID", required = true) var terminalID: String) {
        @Attribute(name = "TerminalBatch")
        var terminalBatch: String? = null
        @Attribute(name = "MerchantID")
        var merchantID: String? = null
        @Attribute(name = "STAN")
        var stan: Long? = null
        @Attribute(name = "APACS50TN")
        var apacs50TN: BigInteger? = null
    }

}
