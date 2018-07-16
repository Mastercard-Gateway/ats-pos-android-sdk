package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.*
import java.util.*

@Root(name = "CardServiceRequest", strict = false)
class CardServiceRequest : ATSMessage {
    @field:Element(name = "POSdata", required = true)
    lateinit var poSdata: POSdata
    @field:Element(name = "Loyalty", required = false)
    var loyalty: CardServiceRequest.Loyalty? = null
    @field:Element(name = "CardCircuitCollection", required = false)
    var cardCircuitCollection: CardServiceRequest.CardCircuitCollection? = null
    @field:Element(name = "OriginalTransaction", required = false)
    var originalTransaction: CardServiceRequest.OriginalTransaction? = null
    @field:Element(name = "TotalAmount", required = false)
    var totalAmount: TotalAmountType? = null
    @field:Element(name = "SaleItem", required = false)
    var saleItem: List<SaleItemType>? = null
    @field:Element(name = "CardValue", required = false)
    var cardValue: CardValueType? = null
    @field:Element(name = "EncryptedCardValue", required = false)
    var encryptedCardValue: EncryptedCardValueType? = null
    @field:Element(name = "Acquirer", required = false)
    var acquirer: Acquirer? = null
    @field:Element(name = "PrivateData", required = false)
    var privateData: List<Any>? = null
    @field:Element(name = "Encryption", required = false)
    var encryption: Any? = null
    @field:Element(name = "Validation", required = false)
    var validation: Any? = null
    @field:Attribute(name = "ApplicationSender", required = false)
    var applicationSender: String? = null
    @field:Attribute(name = "POPID", required = false)
    var popid: String? = null
    @field:Attribute(name = "RequestType", required = true)
    lateinit var requestType: CardRequestType
    @field:Attribute(name = "WorkstationID", required = true)
    lateinit var workstationID: String
    @field:Attribute(name = "RequestID", required = true)
    lateinit var requestID: String
    @field:Attribute(name = "ReferenceNumber", required = false)
    var referenceNumber: String? = null


    class Acquirer {
        @field:Attribute(name = "TerminalID", required = false)
        var terminalID: String? = null
        @field:Attribute(name = "MerchantID", required = false)
        var merchantID: String? = null
        @field:Attribute(name = "AcquirerID", required = false)
        var acquirerID: String? = null
        @field:Attribute(name = "MerchantReference", required = false)
        var merchantReference: String? = null
        @field:Attribute(name = "vTIDClient", required = false)
        var vtidClient: String? = null
        @field:Attribute(name = "vTIDPassword", required = false)
        var vtidPassword: String? = null
        @field:Attribute(name = "CreditPlan", required = false)
        var creditPlan: String? = null
    }


    class CardCircuitCollection {

        @field:ElementList(name = "CardCircuit", required = true, inline = true)
        lateinit var cardCircuit: List<CardCircuit>

        class CardCircuit {
            @field:Text
            lateinit var value: String
            @field:Attribute(name = "CardCircuitState", required = false)
            var cardCircuitState: CardCircuitStateType? = null
        }
    }

    class Loyalty {

        @field:Element(name = "LoyaltyCard", required = false)
        var loyaltyCard: CardServiceRequest.Loyalty.LoyaltyCard? = null
        @field:Element(name = "MOPrule", required = false)
        var moPrule: CardServiceRequest.Loyalty.MOPrule? = null
        @field:Element(name = "LoyaltyAmount", required = false)
        var loyaltyAmount: Float? = null
        @field:Attribute(name = "LoyaltyFlag", required = true)
        var loyaltyFlag: Boolean = false


        class LoyaltyCard : CardTrack() {
            @field:Attribute(name = "LoyaltyPAN", required = false)
            var loyaltyPAN: String? = null
        }

        class MOPrule {
            @field:Attribute(name = "CardPAN", required = true)
            lateinit var cardPAN: String
            @field:Attribute(name = "CardCircuit", required = true)
            lateinit var cardCircuit: String
        }
    }

    class OriginalTransaction {
        @field:Attribute(name = "TerminalID", required = false)
        var terminalID: String? = null
        @field:Attribute(name = "TerminalBatch", required = false)
        var terminalBatch: String? = null
        @field:Attribute(name = "STAN", required = false)
        var stan: Long? = null
        @field:Attribute(name = "TimeStamp", required = false)
        var timeStamp: Date? = null
        @field:Attribute(name = "TrackingReference", required = false)
        var trackingReference: String? = null
    }

    @Order(elements = ["POSTimeStamp", "TransactionNumber", "Reference"])
    class POSdata {
        @field:Element(name = "POSTimeStamp", required = true)
        lateinit var posTimeStamp: Date
        @field:Element(name = "ServiceLevel", required = false)
        var serviceLevel: String? = null
        @field:Element(name = "ShiftNumber", required = false)
        var shiftNumber: Int? = null
        @field:Element(name = "ClerkID", required = false)
        var clerkID: Int? = null
        @field:Element(name = "OutdoorPosition", required = false)
        var outdoorPosition: Int? = null
        @field:Element(name = "ManualPAN", required = false)
        var manualPAN: Boolean? = null
        @field:Element(name = "Contactless", required = false)
        var contactless: Boolean? = null
        @field:Element(name = "ContactlessReceipt", required = false)
        var contactlessReceipt: Boolean? = null
        @field:Element(name = "Chip", required = false)
        var chip: Boolean? = null
        @field:Element(name = "Swipe", required = false)
        var swipe: Boolean? = null
        @field:Element(name = "ClerkPermission", required = false)
        var clerkPermission: String? = null
        @field:Element(name = "TightControl", required = false)
        var tightControl: Boolean? = null
        @field:Element(name = "SplitPayment", required = false)
        var splitPayment: Boolean? = null
        @field:Element(name = "VoiceReferral", required = false)
        var voiceReferral: Boolean? = null
        @field:Element(name = "TransactionNumber", required = false)
        var transactionNumber: Int? = null
        @field:Element(name = "Reference", required = false)
        var reference: String? = null
        @field:Element(name = "CardHolderLocation", required = false)
        var cardHolderLocation: String? = null
        @field:Attribute(name = "LanguageCode", required = false)
        var languageCode: LanguageCodeType? = null
    }
}
