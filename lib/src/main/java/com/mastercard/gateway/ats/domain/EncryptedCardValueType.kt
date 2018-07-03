package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger

@Suppress("ArrayInDataClass")
@Root(name = "EncryptedCardValueType", strict = false)
@Order(elements = ["track1", "track2", "track3", "barcode", "cardPAN", "startDate", "expiryDate", "issueNumber", "serviceCode", "tokenSalt", "cv2", "postCode", "address", "encryptionID", "encryptionKey", "encryptionRSA"])
data class EncryptedCardValueType(@Element(name = "EncryptionID", required = true) var encryptionID: BigInteger,
                                  @Element(name = "EncryptionKey", required = true, type = String::class) var encryptionKey: ByteArray) {

    @Element(name = "Track1", type = String::class)
    var track1: ByteArray? = null
    @Element(name = "Track2", type = String::class)
    var track2: ByteArray? = null
    @Element(name = "Track3", type = String::class)
    var track3: ByteArray? = null
    @Element(name = "Barcode", type = String::class)
    var barcode: ByteArray? = null
    @Element(name = "CardPAN", type = String::class)
    var cardPAN: ByteArray? = null
    @Element(name = "StartDate", type = String::class)
    var startDate: ByteArray? = null
    @Element(name = "ExpiryDate", type = String::class)
    var expiryDate: ByteArray? = null
    @Element(name = "IssueNumber", type = String::class)
    var issueNumber: ByteArray? = null
    @Element(name = "ServiceCode", type = String::class)
    var serviceCode: ByteArray? = null
    @Element(name = "TokenSalt", type = String::class)
    var tokenSalt: ByteArray? = null
    @Element(name = "CV2", type = String::class)
    var cv2: ByteArray? = null
    @Element(name = "PostCode", type = String::class)
    var postCode: ByteArray? = null
    @Element(name = "Address", type = String::class)
    var address: ByteArray? = null
    @Element(name = "EncryptionRSA")
    var encryptionRSA: String? = null

}
