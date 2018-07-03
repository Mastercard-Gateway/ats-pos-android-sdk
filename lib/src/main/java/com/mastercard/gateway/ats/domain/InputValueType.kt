package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "InputValueType")
@Order(elements = ["track1", "track2", "track3", "icc", "barcode", "inBoolean", "inNumber", "inString", "cardPAN", "startDate", "expiryDate"])
class InputValueType {

    @Element(name = "Track1")
    var track1: CardTrack? = null
    @Element(name = "Track2")
    var track2: CardTrack? = null
    @Element(name = "Track3")
    var track3: CardTrack? = null
    @Element(name = "ICC")
    var icc: SecureDataFlow? = null
    @Element(name = "Barcode")
    var barcode: String? = null
    @Element(name = "InBoolean")
    var inBoolean: Boolean? = null
    @Element(name = "InNumber")
    var inNumber: String? = null
    @Element(name = "InString")
    var inString: String? = null
    @Element(name = "CardPAN")
    var cardPAN: String? = null
    @Element(name = "StartDate")
    var startDate: String? = null
    @Element(name = "ExpiryDate")
    var expiryDate: String? = null

}