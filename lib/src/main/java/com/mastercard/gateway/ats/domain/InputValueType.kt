package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "InputValueType")
class InputValueType {

    @field:Element(name = "Track1", required=false)
    var track1: CardTrack? = null
    @field:Element(name = "Track2", required=false)
    var track2: CardTrack? = null
    @field:Element(name = "Track3", required=false)
    var track3: CardTrack? = null
    @field:Element(name = "ICC", required=false)
    var icc: SecureDataFlow? = null
    @field:Element(name = "Barcode", required=false)
    var barcode: String? = null
    @field:Element(name = "InBoolean", required=false)
    var inBoolean: Boolean? = null
    @field:Element(name = "InNumber", required=false)
    var inNumber: String? = null
    @field:Element(name = "InString", required=false)
    var inString: String? = null
    @field:Element(name = "CardPAN", required=false)
    var cardPAN: String? = null
    @field:Element(name = "StartDate", required=false)
    var startDate: String? = null
    @field:Element(name = "ExpiryDate", required=false)
    var expiryDate: String? = null

}