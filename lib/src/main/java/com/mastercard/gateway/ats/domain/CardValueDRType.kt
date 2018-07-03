package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root
import java.math.BigInteger

@Suppress("ArrayInDataClass")
@Root(name = "CardValueDRType", strict = false)
@Order(elements = ["encryptionVersion", "e2EEEncValues", "p2PEEncValues", "track2", "cardPAN", "cadLuhn", "startDate", "expiryDate", "cardCircuit", "issueNumber", "serviceCode", "token", "contactless", "cardType", "readTLV", "features", "terminalAttributes", "terminalID", "readerSerialNo", "icc"])
class CardValueDRType {

    @Element(name = "EncryptionVersion")
    var encryptionVersion: BigInteger? = null
    @Element(name = "TerminalAttributes", type = String::class)
    var terminalAttributes: ByteArray? = null
    @Element(name = "E2EEEncValues")
    var e2EEEncValues: E2EEValues? = null
    @Element(name = "P2PEEncValues")
    var p2PEEncValues: P2PEValues? = null
    @Element(name = "Track2")
    var track2: CardTrack? = null
    @Element(name = "CardPAN")
    var cardPAN: String? = null
    @Element(name = "CADLuhn")
    var cadLuhn: CADLuhn? = null
    @Element(name = "StartDate")
    var startDate: String? = null
    @Element(name = "ExpiryDate")
    var expiryDate: String? = null
    @Element(name = "CardCircuit")
    var cardCircuit: String? = null
    @Element(name = "IssueNumber")
    var issueNumber: String? = null
    @Element(name = "ServiceCode")
    var serviceCode: String? = null
    @Element(name = "Token")
    var token: String? = null
    @Element(name = "Contactless")
    var contactless: Boolean? = null
    @Element(name = "CardType")
    var cardType: String? = null
    @Element(name = "ReadTLV")
    var readTLV: String? = null
    @Element(name = "Features")
    var features: Features? = null
    @Element(name = "TerminalID")
    var terminalID: String? = null
    @Element(name = "ReaderSerialNo")
    var readerSerialNo: String? = null
    @Element(name = "ICC")
    var icc: ICCType? = null


    class CADLuhn {
        @Attribute(name = "Mod10")
        var mod10: Boolean? = null
        @Attribute(name = "Mod11")
        var mod11: Boolean? = null
        @Attribute(name = "MC")
        var mc: Boolean? = null
        @Attribute(name = "BPA")
        var bpa: Boolean? = null
        @Attribute(name = "BPARearranged")
        var bpaRearranged: Boolean? = null
    }

    @Order(elements = ["feature"])
    class Features {
        @Element(name = "Feature")
        var feature: List<CardFeatureType>? = null
    }

}
