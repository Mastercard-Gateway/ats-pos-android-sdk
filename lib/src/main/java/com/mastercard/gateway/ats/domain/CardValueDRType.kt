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

    @field:Element(name = "EncryptionVersion", required=false)
    var encryptionVersion: BigInteger? = null
    @field:Element(name = "TerminalAttributes", type = String::class, required=false)
    var terminalAttributes: ByteArray? = null
    @field:Element(name = "E2EEEncValues", required=false)
    var e2EEEncValues: E2EEValues? = null
    @field:Element(name = "P2PEEncValues", required=false)
    var p2PEEncValues: P2PEValues? = null
    @field:Element(name = "Track2", required=false)
    var track2: CardTrack? = null
    @field:Element(name = "CardPAN", required=false)
    var cardPAN: String? = null
    @field:Element(name = "CADLuhn", required=false)
    var cadLuhn: CADLuhn? = null
    @field:Element(name = "StartDate", required=false)
    var startDate: String? = null
    @field:Element(name = "ExpiryDate", required=false)
    var expiryDate: String? = null
    @field:Element(name = "CardCircuit", required=false)
    var cardCircuit: String? = null
    @field:Element(name = "IssueNumber", required=false)
    var issueNumber: String? = null
    @field:Element(name = "ServiceCode", required=false)
    var serviceCode: String? = null
    @field:Element(name = "Token", required=false)
    var token: String? = null
    @field:Element(name = "Contactless", required=false)
    var contactless: Boolean? = null
    @field:Element(name = "CardType", required=false)
    var cardType: String? = null
    @field:Element(name = "ReadTLV", required=false)
    var readTLV: String? = null
    @field:Element(name = "Features", required=false)
    var features: Features? = null
    @field:Element(name = "TerminalID", required=false)
    var terminalID: String? = null
    @field:Element(name = "ReaderSerialNo", required=false)
    var readerSerialNo: String? = null
    @field:Element(name = "ICC", required=false)
    var icc: ICCType? = null


    class CADLuhn {
        @field:Attribute(name = "Mod10", required=false)
        var mod10: Boolean? = null
        @field:Attribute(name = "Mod11", required=false)
        var mod11: Boolean? = null
        @field:Attribute(name = "MC", required=false)
        var mc: Boolean? = null
        @field:Attribute(name = "BPA", required=false)
        var bpa: Boolean? = null
        @field:Attribute(name = "BPARearranged", required=false)
        var bpaRearranged: Boolean? = null
    }

    @Order(elements = ["feature"])
    class Features {
        @field:Element(name = "Feature", required=false)
        var feature: List<CardFeatureType>? = null
    }

}
