package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "CardValueICCType", strict = false)
@Order(elements = ["track1", "track2", "track3", "barcode", "inString", "cardPAN", "startDate", "expiryDate", "cardCircuit", "issueNumber", "serviceCode", "token", "cv2", "postCode", "address", "icc"])
class CardValueICCType {

    @field:Element(name = "Track1", required=false)
    var track1: CardTrack? = null
    @field:Element(name = "Track2", required=false)
    var track2: CardTrack? = null
    @field:Element(name = "Track3", required=false)
    var track3: CardTrack? = null
    @field:Element(name = "Barcode", required=false)
    var barcode: String? = null
    @field:Element(name = "InString", required=false)
    var inString: String? = null
    @field:Element(name = "CardPAN", required=false)
    var cardPAN: String? = null
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
    @field:Element(name = "CV2", required=false)
    var cv2: String? = null
    @field:Element(name = "PostCode", required=false)
    var postCode: String? = null
    @field:Element(name = "Address", required=false)
    var address: String? = null
    @field:Element(name = "ICC", required=false)
    var icc: ICCType? = null

}

