package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger

import javax.xml.datatype.XMLGregorianCalendar


@Root(name = "CardServiceResponse")
data class CardServiceResponse(@field:Attribute(name = "RequestType", required = true) var requestType: CardRequestType,
                               @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                               @field:Attribute(name = "RequestID", required = true) var requestID: String,
                               @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {

    @field:Element(name = "Terminal", required=false)
    var terminal: CardServiceResponse.Terminal? = null
    @field:Element(name = "ErrorDetail", required=false)
    var errorDetail: ATSErrorDetailType? = null
    @field:Element(name = "Tender", required=false)
    var tender: CardServiceResponse.Tender? = null
    @field:Element(name = "Loyalty", required=false)
    var loyalty: CardServiceResponse.Loyalty? = null
    @field:Element(name = "SaleItem", required=false)
    var saleItem: List<SaleItemType>? = null
    @field:Element(name = "OriginalHeader", required=false)
    var originalHeader: CardServiceResponse.OriginalHeader? = null
    @field:Element(name = "CardValue", required=false)
    var cardValue: CardValueICCType? = null
    @field:Element(name = "PrivateData", required=false)
    var privateData: List<String>? = null
    @field:Attribute(name = "ApplicationSender", required=false)
    var applicationSender: String? = null
    @field:Attribute(name = "POPID", required=false)
    var popid: String? = null

    data class Loyalty(@field:Attribute(name = "LoyaltyFlag", required = true) var loyaltyFlag: Boolean = false) {

        @field:Element(name = "LoyaltyCard", required=false)
        var loyaltyCard: CardServiceResponse.Loyalty.LoyaltyCard? = null
        @field:Element(name = "LoyaltyAmount", required=false)
        var loyaltyAmount: CardServiceResponse.Loyalty.LoyaltyAmount? = null
        @field:Element(name = "LoyaltyApprovalCode", required=false)
        var loyaltyApprovalCode: CardServiceResponse.Loyalty.LoyaltyApprovalCode? = null
        @field:Element(name = "Enabled", required=false)
        var enabled: Boolean? = null
        @field:Element(name = "CustomerName", required=false)
        var customerName: String? = null
        @field:Element(name = "Points", required=false)
        var points: BigInteger? = null
        @field:Element(name = "Bonus", required=false)
        var bonus: Boolean? = null
        @field:Element(name = "Incentive", required=false)
        var incentive: BigInteger? = null
        @field:Element(name = "Updated", required=false)
        var updated: XMLGregorianCalendar? = null
        @field:Attribute(name = "LoyaltyTimeStamp", required=false)
        var loyaltyTimeStamp: XMLGregorianCalendar? = null

        class LoyaltyAmount {
            var value: Float = 0f
            @field:Attribute(name = "OriginalLoyaltyAmount", required=false)
            var originalLoyaltyAmount: Float? = null
        }

        class LoyaltyApprovalCode {
            var value: String? = null
            @field:Attribute(name = "LoyaltyAcquirerID", required=false)
            var loyaltyAcquirerID: String? = null
            @field:Attribute(name = "LoyaltyAcquirerBatch", required=false)
            var loyaltyAcquirerBatch: String? = null
        }

        class LoyaltyCard : CardTrack() {
            @field:Attribute(name = "LoyaltyPAN")
            var loyaltyPAN: String? = null
        }
    }

    data class OriginalHeader(@field:Attribute(name = "RequestType", required = true) var requestType: CardRequestType,
                              @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                              @field:Attribute(name = "RequestID", required = true) var requestID: String,
                              @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) {

        @field:Attribute(name = "ApplicationSender", required=false)
        var applicationSender: String? = null
        @field:Attribute(name = "POPID", required=false)
        var popid: String? = null
    }

    class Tender {

        @field:Element(name = "TotalAmount", required=false)
        var totalAmount: TotalAmountType? = null
        @field:Element(name = "Authorisation", required=false)
        var authorisation: Authorisation? = null
        @field:Element(name = "RestrictionCodes", required=false)
        var restrictionCodes: List<BigInteger>? = null
        @field:Attribute(name = "LanguageCode", required=false)
        var languageCode: LanguageCodeType? = null

        data class Authorisation(@field:Attribute(name = "AcquirerID", required = true) var acquirerID: String,
                                 @field:Attribute(name = "TimeStamp", required = true) var timeStamp: XMLGregorianCalendar) {

            @field:Attribute(name = "IssuerID", required=false)
            var issuerID: String? = null
            @field:Attribute(name = "CardPAN", required=false)
            var cardPAN: String? = null
            @field:Attribute(name = "StartDate", required=false)
            var startDate: String? = null
            @field:Attribute(name = "ExpiryDate", required=false)
            var expiryDate: String? = null
            @field:Attribute(name = "IssueNumber", required=false)
            var issueNumber: String? = null
            @field:Attribute(name = "Token", required=false)
            var token: String? = null
            @field:Attribute(name = "ActionCode", required=false)
            var actionCode: String? = null
            @field:Attribute(name = "ApprovalCode", required=false)
            var approvalCode: String? = null
            @field:Attribute(name = "AcquirerBatch", required=false)
            var acquirerBatch: String? = null
            @field:Attribute(name = "CardCircuit", required=false)
            var cardCircuit: String? = null
            @field:Attribute(name = "FiscalReceipt", required=false)
            var fiscalReceipt: Boolean? = null
            @field:Attribute(name = "TimeDisplay", required=false)
            var timeDisplay: Boolean? = null
            @field:Attribute(name = "LoyaltyAllowed", required=false)
            var loyaltyAllowed: Boolean? = null
            @field:Attribute(name = "ReceiptCopies", required=false)
            var receiptCopies: BigInteger? = null
            @field:Attribute(name = "Merchant", required=false)
            var merchant: String? = null
            @field:Attribute(name = "AuthorisationType", required=false)
            var authorisationType: AuthorisationMethodType? = null
            @field:Attribute(name = "ReceiptNumber", required=false)
            var receiptNumber: String? = null
            @field:Attribute(name = "CaptureReference", required=false)
            var captureReference: String? = null
            @field:Attribute(name = "TrackingReference", required=false)
            var trackingReference: String? = null
            @field:Attribute(name = "CardVerification", required=false)
            var cardVerification: CardVerificationType? = null
            @field:Attribute(name = "CV2Result", required=false)
            var cv2Result: AdditionalResponseType? = null
            @field:Attribute(name = "PostCodeResult", required=false)
            var postCodeResult: AdditionalResponseType? = null
            @field:Attribute(name = "AddressResult", required=false)
            var addressResult: AdditionalResponseType? = null
        }
    }

    data class Terminal(@field:Attribute(name = "TerminalID", required = true) var terminalID: String) {
        @field:Attribute(name = "TerminalBatch", required=false)
        var terminalBatch: String? = null
        @field:Attribute(name = "MerchantID", required=false)
        var merchantID: String? = null
        @field:Attribute(name = "STAN", required=false)
        var stan: Long? = null
        @field:Attribute(name = "APACS50TN", required=false)
        var apacs50TN: BigInteger? = null
    }

}
