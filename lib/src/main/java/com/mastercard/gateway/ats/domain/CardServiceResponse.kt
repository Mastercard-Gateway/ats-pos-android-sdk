package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger

import javax.xml.datatype.XMLGregorianCalendar


@Root(name = "CardServiceResponse")
@Order(elements = ["terminal", "errorDetail", "tender", "loyalty", "saleItem", "originalHeader", "cardValue", "privateData"])
data class CardServiceResponse(@field:Element(name = "Terminal") var terminal: CardServiceResponse.Terminal? = null,
                               @field:Element(name = "ErrorDetail") var errorDetail: ATSErrorDetailType? = null,
                               @field:Element(name = "Tender") var tender: CardServiceResponse.Tender? = null,
                               @field:Element(name = "Loyalty") var loyalty: CardServiceResponse.Loyalty? = null,
                               @field:Element(name = "SaleItem") var saleItem: List<SaleItemType>? = null,
                               @field:Element(name = "OriginalHeader") var originalHeader: CardServiceResponse.OriginalHeader? = null,
                               @field:Element(name = "CardValue") var cardValue: CardValueICCType? = null,
                               @field:Element(name = "PrivateData") var privateData: List<String>? = null,
                               @field:Attribute(name = "RequestType", required = true) var requestType: CardRequestType,
                               @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
                               @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                               @field:Attribute(name = "POPID") var popid: String? = null,
                               @field:Attribute(name = "RequestID", required = true) var requestID: String,
                               @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType) : ATSMessage {

    @Order(elements = ["loyaltyCard", "loyaltyAmount", "loyaltyApprovalCode", "enabled", "customerName", "points", "bonus", "incentive", "updated"])
    data class Loyalty(@field:Element(name = "LoyaltyCard") var loyaltyCard: CardServiceResponse.Loyalty.LoyaltyCard? = null,
                       @field:Element(name = "LoyaltyAmount") var loyaltyAmount: CardServiceResponse.Loyalty.LoyaltyAmount? = null,
                       @field:Element(name = "LoyaltyApprovalCode") var loyaltyApprovalCode: CardServiceResponse.Loyalty.LoyaltyApprovalCode? = null,
                       @field:Element(name = "Enabled") var enabled: Boolean? = null,
                       @field:Element(name = "CustomerName") var customerName: String? = null,
                       @field:Element(name = "Points") var points: BigInteger? = null,
                       @field:Element(name = "Bonus") var bonus: Boolean? = null,
                       @field:Element(name = "Incentive") var incentive: BigInteger? = null,
                       @field:Element(name = "Updated") var updated: XMLGregorianCalendar? = null,
                       @field:Attribute(name = "LoyaltyFlag", required = true) var loyaltyFlag: Boolean = false,
                       @field:Attribute(name = "LoyaltyTimeStamp") var loyaltyTimeStamp: XMLGregorianCalendar? = null) {

        data class LoyaltyAmount(var value: Float = 0.toFloat(),
                                 @field:Attribute(name = "OriginalLoyaltyAmount") var originalLoyaltyAmount: Float? = null)

        data class LoyaltyApprovalCode(var value: String? = null,
                                       @field:Attribute(name = "LoyaltyAcquirerID") var loyaltyAcquirerID: String? = null,
                                       @field:Attribute(name = "LoyaltyAcquirerBatch") var loyaltyAcquirerBatch: String? = null)

        data class LoyaltyCard(@field:Attribute(name = "LoyaltyPAN") var loyaltyPAN: String? = null) : CardTrack()
    }

    data class OriginalHeader(@field:Attribute(name = "RequestType", required = true) var requestType: CardRequestType,
                              @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
                              @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
                              @field:Attribute(name = "POPID") var popid: String? = null,
                              @field:Attribute(name = "RequestID", required = true) var requestID: String,
                              @field:Attribute(name = "OverallResult", required = true) var overallResult: RequestResultType? = null)

    @Order(elements = ["totalAmount", "authorisation", "restrictionCodes"])
    data class Tender(@field:Element(name = "TotalAmount") var totalAmount: TotalAmountType? = null,
                      @field:Element(name = "Authorisation") var authorisation: Authorisation? = null,
                      @field:Element(name = "RestrictionCodes") var restrictionCodes: List<BigInteger>? = null,
                      @field:Attribute(name = "LanguageCode") var languageCode: LanguageCodeType? = null) {

        data class Authorisation(@field:Attribute(name = "AcquirerID", required = true) var acquirerID: String,
                                 @field:Attribute(name = "IssuerID") var issuerID: String? = null,
                                 @field:Attribute(name = "CardPAN") var cardPAN: String? = null,
                                 @field:Attribute(name = "StartDate") var startDate: String? = null,
                                 @field:Attribute(name = "ExpiryDate") var expiryDate: String? = null,
                                 @field:Attribute(name = "IssueNumber") var issueNumber: String? = null,
                                 @field:Attribute(name = "Token") var token: String? = null,
                                 @field:Attribute(name = "TimeStamp", required = true) var timeStamp: XMLGregorianCalendar,
                                 @field:Attribute(name = "ActionCode") var actionCode: String? = null,
                                 @field:Attribute(name = "ApprovalCode") var approvalCode: String? = null,
                                 @field:Attribute(name = "AcquirerBatch") var acquirerBatch: String? = null,
                                 @field:Attribute(name = "CardCircuit") var cardCircuit: String? = null,
                                 @field:Attribute(name = "FiscalReceipt") var fiscalReceipt: Boolean? = null,
                                 @field:Attribute(name = "TimeDisplay") var timeDisplay: Boolean? = null,
                                 @field:Attribute(name = "LoyaltyAllowed") var loyaltyAllowed: Boolean? = null,
                                 @field:Attribute(name = "ReceiptCopies") var receiptCopies: BigInteger? = null,
                                 @field:Attribute(name = "Merchant") var merchant: String? = null,
                                 @field:Attribute(name = "AuthorisationType") var authorisationType: AuthorisationMethodType? = null,
                                 @field:Attribute(name = "ReceiptNumber") var receiptNumber: String? = null,
                                 @field:Attribute(name = "CaptureReference") var captureReference: String? = null,
                                 @field:Attribute(name = "TrackingReference") var trackingReference: String? = null,
                                 @field:Attribute(name = "CardVerification") var cardVerification: CardVerificationType? = null,
                                 @field:Attribute(name = "CV2Result") var cv2Result: AdditionalResponseType? = null,
                                 @field:Attribute(name = "PostCodeResult") var postCodeResult: AdditionalResponseType? = null,
                                 @field:Attribute(name = "AddressResult") var addressResult: AdditionalResponseType? = null)
    }

    data class Terminal(@field:Attribute(name = "TerminalID", required = true) var terminalID: String,
                        @field:Attribute(name = "TerminalBatch") var terminalBatch: String? = null,
                        @field:Attribute(name = "MerchantID") var merchantID: String? = null,
                        @field:Attribute(name = "STAN") var stan: Long? = null,
                        @field:Attribute(name = "APACS50TN") var apacs50TN: BigInteger? = null)

}
