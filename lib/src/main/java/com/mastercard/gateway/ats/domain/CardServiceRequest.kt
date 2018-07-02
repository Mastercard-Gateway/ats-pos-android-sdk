package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger

import javax.xml.datatype.XMLGregorianCalendar

@Root(name = "CardServiceRequest", strict = false)
@Order(elements = ["poSdata", "loyalty", "cardCircuitCollection", "originalTransaction", "totalAmount", "saleItem", "cardValue", "encryptedCardValue", "acquirer", "privateData", "encryption", "validation"])
data class CardServiceRequest(@Element(name = "POSdata", required = true) var poSdata: POSdata, @Attribute(name = "RequestType", required = true) var requestType: CardRequestType, @Attribute(name = "WorkstationID", required = true) var workstationID: String) : ATSMessage {

    @Element(name = "Loyalty")
    var loyalty: CardServiceRequest.Loyalty? = null
    @Element(name = "CardCircuitCollection")
    var cardCircuitCollection: CardServiceRequest.CardCircuitCollection? = null
    @Element(name = "OriginalTransaction")
    var originalTransaction: CardServiceRequest.OriginalTransaction? = null
    @Element(name = "TotalAmount")
    var totalAmount: TotalAmountType? = null
    @Element(name = "SaleItem")
    var saleItem: List<SaleItemType>? = null
    @Element(name = "CardValue")
    var cardValue: CardValueType? = null
    @Element(name = "EncryptedCardValue")
    var encryptedCardValue: EncryptedCardValueType? = null
    @Element(name = "Acquirer")
    var acquirer: CardServiceRequest.Acquirer? = null
    @Element(name = "PrivateData")
    var privateData: List<Any>? = null
    @Element(name = "Encryption")
    var encryption: Any? = null
    @Element(name = "Validation")
    var validation: Any? = null
    @Attribute(name = "ApplicationSender")
    var applicationSender: String? = null
    @Attribute(name = "POPID")
    var popid: String? = null
    @Attribute(name = "RequestID", required = true)
    var requestID: String? = null
    @Attribute(name = "ReferenceNumber")
    var referenceNumber: String? = null


    class Acquirer {
        @Attribute(name = "TerminalID")
        var terminalID: String? = null
        @Attribute(name = "MerchantID")
        var merchantID: String? = null
        @Attribute(name = "AcquirerID")
        var acquirerID: String? = null
        @Attribute(name = "MerchantReference")
        var merchantReference: String? = null
        @Attribute(name = "vTIDClient")
        var vtidClient: String? = null
        @Attribute(name = "vTIDPassword")
        var vtidPassword: String? = null
        @Attribute(name = "CreditPlan")
        var creditPlan: String? = null
    }


    @Order(elements = ["cardCircuit"])
    data class CardCircuitCollection(@Element(name = "CardCircuit", required = true) var cardCircuit: List<CardCircuit>) {

        data class CardCircuit(var value: String? = null) {
            @Attribute(name = "CardCircuitState")
            var cardCircuitState: CardCircuitStateType? = null
        }
    }

    @Order(elements = ["loyaltyCard", "moPrule", "loyaltyAmount"])
    class Loyalty {

        @Element(name = "LoyaltyCard")
        var loyaltyCard: CardServiceRequest.Loyalty.LoyaltyCard? = null
        @Element(name = "MOPrule")
        var moPrule: CardServiceRequest.Loyalty.MOPrule? = null
        @Element(name = "LoyaltyAmount")
        var loyaltyAmount: Float? = null
        @Attribute(name = "LoyaltyFlag", required = true)
        var loyaltyFlag: Boolean = false

        class LoyaltyCard : CardTrack() {
            @Attribute(name = "LoyaltyPAN")
            var loyaltyPAN: String? = null
        }

        data class MOPrule(@Attribute(name = "CardPAN", required = true) var cardPAN: String,
                           @Attribute(name = "CardCircuit", required = true) var cardCircuit: String)
    }

    class OriginalTransaction() {
        @Attribute(name = "TerminalID")
        var terminalID: String? = null
        @Attribute(name = "TerminalBatch")
        var terminalBatch: String? = null
        @Attribute(name = "STAN")
        var stan: Long? = null
        @Attribute(name = "TimeStamp")
        var timeStamp: XMLGregorianCalendar? = null
        @Attribute(name = "TrackingReference")
        var trackingReference: String? = null
    }

    @Order(elements = ["posTimeStamp", "serviceLevel", "shiftNumber", "clerkID", "outdoorPosition", "manualPAN", "contactless", "contactlessReceipt", "chip", "swipe", "clerkPermission", "tightControl", "splitPayment", "voiceReferral", "transactionNumber", "reference", "cardHolderLocation"])
    data class POSdata(@Element(name = "POSTimeStamp", required = true) var posTimeStamp: XMLGregorianCalendar) {
        @Element(name = "ServiceLevel")
        var serviceLevel: String? = null
        @Element(name = "ShiftNumber")
        var shiftNumber: BigInteger? = null
        @Element(name = "ClerkID")
        var clerkID: Int? = null
        @Element(name = "OutdoorPosition")
        var outdoorPosition: BigInteger? = null
        @Element(name = "ManualPAN")
        var manualPAN: Boolean? = null
        @Element(name = "Contactless")
        var contactless: Boolean? = null
        @Element(name = "ContactlessReceipt")
        var contactlessReceipt: Boolean? = null
        @Element(name = "Chip")
        var chip: Boolean? = null
        @Element(name = "Swipe")
        var swipe: Boolean? = null
        @Element(name = "ClerkPermission")
        var clerkPermission: String? = null
        @Element(name = "TightControl")
        var tightControl: Boolean? = null
        @Element(name = "SplitPayment")
        var splitPayment: Boolean? = null
        @Element(name = "VoiceReferral")
        var voiceReferral: Boolean? = null
        @Element(name = "TransactionNumber")
        var transactionNumber: BigInteger? = null
        @Element(name = "Reference")
        var reference: String? = null
        @Element(name = "CardHolderLocation")
        var cardHolderLocation: String? = null
        @Attribute(name = "LanguageCode")
        var languageCode: LanguageCodeType? = null
    }
}
