package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "InputValueType")
@Order(elements = ["track1", "track2", "track3", "icc", "barcode", "inBoolean", "inNumber", "inString", "cardPAN", "startDate", "expiryDate"])
data class InputValueType(
        @field:Element(name = "Track1") var track1: CardTrack? = null,
        @field:Element(name = "Track2") var track2: CardTrack? = null,
        @field:Element(name = "Track3") var track3: CardTrack? = null,
        @field:Element(name = "ICC") var icc: SecureDataFlow? = null,
        @field:Element(name = "Barcode") var barcode: String? = null,
        @field:Element(name = "InBoolean") var inBoolean: Boolean? = null,
        @field:Element(name = "InNumber") var inNumber: String? = null,
        @field:Element(name = "InString") var inString: String? = null,
        @field:Element(name = "CardPAN") var cardPAN: String? = null,
        @field:Element(name = "StartDate") var startDate: String? = null,
        @field:Element(name = "ExpiryDate") var expiryDate: String? = null)
