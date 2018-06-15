package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "Authorisation", strict = false)
class Authorisation {

    // required
    @field:Attribute(name = "AcquirerID")
    lateinit var acquirerId: String

    @field:Attribute(name = "TimeStamp")
    lateinit var timeStamp: String


    // optional
    @field:Attribute(name = "IssuerID", required = false)
    var issuerId: String? = null

    @field:Attribute(name = "CardPAN", required = false)
    var cardPan: String? = null

    @field:Attribute(name = "CardPANType", required = false)
    var cardPanType: String? = null

    @field:Attribute(name = "StartDate", required = false)
    var startDate: String? = null

    @field:Attribute(name = "ExpiryDate", required = false)
    var expiryDate: String? = null

    @field:Attribute(name = "IssueNumber", required = false)
    var issueNumber: String? = null

    @field:Attribute(name = "Token", required = false)
    var token: String? = null

    @field:Attribute(name = "ActionCode", required = false)
    var actionCode: String? = null

    @field:Attribute(name = "ApprovalCode", required = false)
    var approvalCode: String? = null

    @field:Attribute(name = "AcquirerBatch", required = false)
    var acquirerBatch: String? = null

    @field:Attribute(name = "CardCircuit", required = false)
    var cardCircuit: String? = null

    @field:Attribute(name = "FiscalReceipt", required = false)
    var fiscalReceipt: Boolean? = null

    @field:Attribute(name = "TimeDisplay", required = false)
    var timeDisplay: Boolean? = null

    @field:Attribute(name = "LoyaltyAllowed", required = false)
    var loyaltyAllowed: Boolean? = null

    @field:Attribute(name = "ReceiptCopies", required = false)
    var receiptCopies: Int? = null

    @field:Attribute(name = "Merchant", required = false)
    var merchant: String? = null

    @field:Attribute(name = "AuthorisationType", required = false)
    var authorisationType: AuthorisationType? = null

    @field:Attribute(name = "ReceiptNumber", required = false)
    var receiptNumber: String? = null

    @field:Attribute(name = "CaptureReference", required = false)
    var captureReference: String? = null

    @field:Attribute(name = "TrackingReference", required = false)
    var trackingReference: String? = null

    @field:Attribute(name = "CardVerification", required = false)
    var cardVerification: CardVerificationType? = null

    @field:Attribute(name = "CV2Result", required = false)
    var cv2Result: AdditionalResponseType? = null

    @field:Attribute(name = "PostCodeResult", required = false)
    var postCodeResult: AdditionalResponseType? = null

    @field:Attribute(name = "AddressResult", required = false)
    var addressResult: AdditionalResponseType? = null
}