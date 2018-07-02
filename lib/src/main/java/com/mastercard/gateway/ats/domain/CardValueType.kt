package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger


@Suppress("ArrayInDataClass")
@Root(name = "CardValueType", strict = false)
@Order(elements = arrayOf("track1", "track2", "track3", "icc", "barcode", "inString", "cardPAN", "startDate", "expiryDate", "endDate", "cardCircuit", "issueNumber", "serviceCode", "tokenSalt", "token", "cv2", "postCode", "address", "extendedPolicy", "mileage", "registration", "idChecked", "supplementary"))
data class CardValueType(@field:Element(name = "Track1") var track1: CardTrack? = null,
        @field:Element(name = "Track2") var track2: CardTrack? = null,
        @field:Element(name = "Track3") var track3: CardTrack? = null,
        @field:Element(name = "ICC") var icc: SecureDataFlow? = null,
        @field:Element(name = "Barcode") var barcode: String? = null,
        @field:Element(name = "InString") var inString: String? = null,
        @field:Element(name = "CardPAN") var cardPAN: String? = null,
        @field:Element(name = "StartDate") var startDate: String? = null,
        @field:Element(name = "ExpiryDate") var expiryDate: String? = null,
        @field:Element(name = "EndDate") var endDate: String? = null,
        @field:Element(name = "CardCircuit") var cardCircuit: String? = null,
        @field:Element(name = "IssueNumber") var issueNumber: String? = null,
        @field:Element(name = "ServiceCode") var serviceCode: String? = null,
        @field:Element(name = "TokenSalt", type = String::class) var tokenSalt: ByteArray? = null,
        @field:Element(name = "Token") var token: String? = null,
        @field:Element(name = "CV2") var cv2: String? = null,
        @field:Element(name = "PostCode") var postCode: String? = null,
        @field:Element(name = "Address") var address: String? = null,
        @field:Element(name = "ExtendedPolicy") var extendedPolicy: Any? = null,
        @field:Element(name = "Mileage") var mileage: BigInteger? = null,
        @field:Element(name = "Registration") var registration: String? = null,
        @field:Element(name = "IDChecked") var idChecked: Boolean? = null,
        @field:Element(name = "Supplementary") var supplementary: String? = null
)
