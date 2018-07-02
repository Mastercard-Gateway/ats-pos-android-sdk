package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root
import java.math.BigInteger

@Suppress("ArrayInDataClass")
@Root(name = "CardValueDRType", strict = false)
@Order(elements = ["encryptionVersion", "e2EEEncValues", "p2PEEncValues", "track2", "cardPAN", "cadLuhn", "startDate", "expiryDate", "cardCircuit", "issueNumber", "serviceCode", "token", "contactless", "cardType", "readTLV", "features", "terminalAttributes", "terminalID", "readerSerialNo", "icc"])
data class CardValueDRType(@field:Element(name = "EncryptionVersion") var encryptionVersion: BigInteger? = null,
                           @field:Element(name = "E2EEEncValues") var e2EEEncValues: E2EEValues? = null,
                           @field:Element(name = "P2PEEncValues") var p2PEEncValues: P2PEValues? = null,
                           @field:Element(name = "Track2") var track2: CardTrack? = null,
                           @field:Element(name = "CardPAN") var cardPAN: String? = null,
                           @field:Element(name = "CADLuhn") var cadLuhn: CADLuhn? = null,
                           @field:Element(name = "StartDate") var startDate: String? = null,
                           @field:Element(name = "ExpiryDate") var expiryDate: String? = null,
                           @field:Element(name = "CardCircuit") var cardCircuit: String? = null,
                           @field:Element(name = "IssueNumber") var issueNumber: String? = null,
                           @field:Element(name = "ServiceCode") var serviceCode: String? = null,
                           @field:Element(name = "Token") var token: String? = null,
                           @field:Element(name = "Contactless") var contactless: Boolean? = null,
                           @field:Element(name = "CardType") var cardType: String? = null,
                           @field:Element(name = "ReadTLV") var readTLV: String? = null,
                           @field:Element(name = "Features") var features: Features? = null,
                           @field:Element(name = "TerminalAttributes", type = String::class) var terminalAttributes: ByteArray? = null,
                           @field:Element(name = "TerminalID") var terminalID: String? = null,
                           @field:Element(name = "ReaderSerialNo") var readerSerialNo: String? = null,
                           @field:Element(name = "ICC") var icc: ICCType? = null) {

    data class CADLuhn(@field:Attribute(name = "Mod10") var mod10: Boolean? = null,
                       @field:Attribute(name = "Mod11") var mod11: Boolean? = null,
                       @field:Attribute(name = "MC") var mc: Boolean? = null,
                       @field:Attribute(name = "BPA") var bpa: Boolean? = null,
                       @field:Attribute(name = "BPARearranged") var bpaRearranged: Boolean? = null)

    @Order(elements = ["feature"])
    data class Features(@field:Element(name = "Feature") var feature: List<CardFeatureType>? = null)

}
