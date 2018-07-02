package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "CardValueICCType", strict = false)
@Order(elements = ["track1", "track2", "track3", "barcode", "inString", "cardPAN", "startDate", "expiryDate", "cardCircuit", "issueNumber", "serviceCode", "token", "cv2", "postCode", "address", "icc"])
data class CardValueICCType(
        @field:Element(name = "Track1") var track1: CardTrack? = null,
        @field:Element(name = "Track2") var track2: CardTrack? = null,
        @field:Element(name = "Track3") var track3: CardTrack? = null,
        @field:Element(name = "Barcode") var barcode: String? = null,
        @field:Element(name = "InString") var inString: String? = null,
        @field:Element(name = "CardPAN") var cardPAN: String? = null,
        @field:Element(name = "StartDate") var startDate: String? = null,
        @field:Element(name = "ExpiryDate") var expiryDate: String? = null,
        @field:Element(name = "CardCircuit") var cardCircuit: String? = null,
        @field:Element(name = "IssueNumber") var issueNumber: String? = null,
        @field:Element(name = "ServiceCode") var serviceCode: String? = null,
        @field:Element(name = "Token") var token: String? = null,
        @field:Element(name = "CV2") var cv2: String? = null,
        @field:Element(name = "PostCode") var postCode: String? = null,
        @field:Element(name = "Address") var address: String? = null,
        @field:Element(name = "ICC") var icc: ICCType? = null
)

