package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger

import javax.xml.datatype.XMLGregorianCalendar

@Root(name = "CardServiceRequest", strict = false)
@Order(elements = ["poSdata", "loyalty", "cardCircuitCollection", "originalTransaction", "totalAmount", "saleItem", "cardValue", "encryptedCardValue", "acquirer", "privateData", "encryption", "validation"])
data class CardServiceRequest(@field:Element(name = "POSdata", required = true) var poSdata: CardServiceRequest.POSdata,
        @field:Element(name = "Loyalty") var loyalty: CardServiceRequest.Loyalty? = null,
        @field:Element(name = "CardCircuitCollection") var cardCircuitCollection: CardServiceRequest.CardCircuitCollection? = null,
        @field:Element(name = "OriginalTransaction") var originalTransaction: CardServiceRequest.OriginalTransaction? = null,
        @field:Element(name = "TotalAmount") var totalAmount: TotalAmountType? = null,
        @field:Element(name = "SaleItem") var saleItem: List<SaleItemType>? = null,
        @field:Element(name = "CardValue") var cardValue: CardValueType? = null,
        @field:Element(name = "EncryptedCardValue") var encryptedCardValue: EncryptedCardValueType? = null,
        @field:Element(name = "Acquirer") var acquirer: CardServiceRequest.Acquirer? = null,
        @field:Element(name = "PrivateData") var privateData: List<Any>? = null,
        @field:Element(name = "Encryption") var encryption: Any? = null,
        @field:Element(name = "Validation") var validation: Any? = null,
        @field:Attribute(name = "RequestType", required = true) var requestType: CardRequestType,
        @field:Attribute(name = "ApplicationSender") var applicationSender: String? = null,
        @field:Attribute(name = "WorkstationID", required = true) var workstationID: String,
        @field:Attribute(name = "POPID") var popid: String? = null,
        @field:Attribute(name = "RequestID", required = true) var requestID: String? = null,
        @field:Attribute(name = "ReferenceNumber") var referenceNumber: String? = null) {

    data class Acquirer(@field:Attribute(name = "TerminalID") var terminalID: String? = null,
            @field:Attribute(name = "MerchantID") var merchantID: String? = null,
            @field:Attribute(name = "AcquirerID") var acquirerID: String? = null,
            @field:Attribute(name = "MerchantReference") var merchantReference: String? = null,
            @field:Attribute(name = "vTIDClient") var vtidClient: String? = null,
            @field:Attribute(name = "vTIDPassword") var vtidPassword: String? = null,
            @field:Attribute(name = "CreditPlan") var creditPlan: String? = null)

    
    @Order(elements = ["cardCircuit"])
    data class CardCircuitCollection(@field:Element(name = "CardCircuit", required = true) var cardCircuit: List<CardCircuit>? = null) {
        data class CardCircuit(var value: String? = null,
                               @field:Attribute(name = "CardCircuitState") var cardCircuitState: CardCircuitStateType? = null)
    }
    
    @Order(elements = ["loyaltyCard", "moPrule", "loyaltyAmount"])
    data class Loyalty(@field:Element(name = "LoyaltyCard") var loyaltyCard: CardServiceRequest.Loyalty.LoyaltyCard? = null,
            @field:Element(name = "MOPrule") var moPrule: CardServiceRequest.Loyalty.MOPrule? = null,
            @field:Element(name = "LoyaltyAmount") var loyaltyAmount: Float? = null,
            @field:Attribute(name = "LoyaltyFlag", required = true) var loyaltyFlag: Boolean = false) {
        
        data class LoyaltyCard(@field:Attribute(name = "LoyaltyPAN") var loyaltyPAN: String? = null) : CardTrack()

        data class MOPrule(@field:Attribute(name = "CardPAN", required = true) var cardPAN: String,
                @field:Attribute(name = "CardCircuit", required = true) var cardCircuit: String)
    }
    
    data class OriginalTransaction(@field:Attribute(name = "TerminalID") var terminalID: String? = null,
            @field:Attribute(name = "TerminalBatch") var terminalBatch: String? = null,
            @field:Attribute(name = "STAN") var stan: Long? = null,
            @field:Attribute(name = "TimeStamp") var timeStamp: XMLGregorianCalendar? = null,
            @field:Attribute(name = "TrackingReference") var trackingReference: String? = null)
    
    @Order(elements = ["posTimeStamp", "serviceLevel", "shiftNumber", "clerkID", "outdoorPosition", "manualPAN", "contactless", "contactlessReceipt", "chip", "swipe", "clerkPermission", "tightControl", "splitPayment", "voiceReferral", "transactionNumber", "reference", "cardHolderLocation"])
    data class POSdata(@field:Element(name = "POSTimeStamp", required = true) var posTimeStamp: XMLGregorianCalendar,
            @field:Element(name = "ServiceLevel") var serviceLevel: String? = null,
            @field:Element(name = "ShiftNumber") var shiftNumber: BigInteger? = null,
            @field:Element(name = "ClerkID") var clerkID: Int? = null,
            @field:Element(name = "OutdoorPosition") var outdoorPosition: BigInteger? = null,
            @field:Element(name = "ManualPAN") var manualPAN: Boolean? = null,
            @field:Element(name = "Contactless") var contactless: Boolean? = null,
            @field:Element(name = "ContactlessReceipt") var contactlessReceipt: Boolean? = null,
            @field:Element(name = "Chip") var chip: Boolean? = null,
            @field:Element(name = "Swipe") var swipe: Boolean? = null,
            @field:Element(name = "ClerkPermission") var clerkPermission: String? = null,
            @field:Element(name = "TightControl") var tightControl: Boolean? = null,
            @field:Element(name = "SplitPayment") var splitPayment: Boolean? = null,
            @field:Element(name = "VoiceReferral") var voiceReferral: Boolean? = null,
            @field:Element(name = "TransactionNumber") var transactionNumber: BigInteger? = null,
            @field:Element(name = "Reference") var reference: String? = null,
            @field:Element(name = "CardHolderLocation") var cardHolderLocation: String? = null,
            @field:Attribute(name = "LanguageCode") var languageCode: LanguageCodeType? = null)
}
