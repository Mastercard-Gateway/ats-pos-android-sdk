package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger


@Suppress("ArrayInDataClass")
@Root(name = "CardValueType", strict = false)
@Order(elements = ["track1", "track2", "track3", "icc", "barcode", "inString", "cardPAN", "startDate", "expiryDate", "endDate", "cardCircuit", "issueNumber", "serviceCode", "tokenSalt", "token", "cv2", "postCode", "address", "extendedPolicy", "mileage", "registration", "idChecked", "supplementary"])
class CardValueType {
    @Element(name = "Track1") var track1: CardTrack? = null
    @Element(name = "Track2") var track2: CardTrack? = null
    @Element(name = "Track3") var track3: CardTrack? = null
    @Element(name = "ICC") var icc: SecureDataFlow? = null
    @Element(name = "Barcode") var barcode: String? = null
    @Element(name = "InString") var inString: String? = null
    @Element(name = "CardPAN") var cardPAN: String? = null
    @Element(name = "StartDate") var startDate: String? = null
    @Element(name = "ExpiryDate") var expiryDate: String? = null
    @Element(name = "EndDate") var endDate: String? = null
    @Element(name = "CardCircuit") var cardCircuit: String? = null
    @Element(name = "IssueNumber") var issueNumber: String? = null
    @Element(name = "ServiceCode") var serviceCode: String? = null
    @Element(name = "TokenSalt", type = String::class) var tokenSalt: ByteArray? = null
    @Element(name = "Token") var token: String? = null
    @Element(name = "CV2") var cv2: String? = null
    @Element(name = "PostCode") var postCode: String? = null
    @Element(name = "Address") var address: String? = null
    @Element(name = "ExtendedPolicy") var extendedPolicy: Any? = null
    @Element(name = "Mileage") var mileage: BigInteger? = null
    @Element(name = "Registration") var registration: String? = null
    @Element(name = "IDChecked") var idChecked: Boolean? = null
    @Element(name = "Supplementary") var supplementary: String? = null
}
