package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigInteger

@Suppress("ArrayInDataClass")
@Root(name = "EncryptedCardValueType", strict = false)
data class EncryptedCardValueType(@field:Element(name = "EncryptionID", required = true) var encryptionID: BigInteger,
                                  @field:Element(name = "EncryptionKey", required = true, type = String::class) var encryptionKey: ByteArray) {

    @field:Element(name = "Track1", type = String::class, required=false)
    var track1: ByteArray? = null
    @field:Element(name = "Track2", type = String::class, required=false)
    var track2: ByteArray? = null
    @field:Element(name = "Track3", type = String::class, required=false)
    var track3: ByteArray? = null
    @field:Element(name = "Barcode", type = String::class, required=false)
    var barcode: ByteArray? = null
    @field:Element(name = "CardPAN", type = String::class, required=false)
    var cardPAN: ByteArray? = null
    @field:Element(name = "StartDate", type = String::class, required=false)
    var startDate: ByteArray? = null
    @field:Element(name = "ExpiryDate", type = String::class, required=false)
    var expiryDate: ByteArray? = null
    @field:Element(name = "IssueNumber", type = String::class, required=false)
    var issueNumber: ByteArray? = null
    @field:Element(name = "ServiceCode", type = String::class, required=false)
    var serviceCode: ByteArray? = null
    @field:Element(name = "TokenSalt", type = String::class, required=false)
    var tokenSalt: ByteArray? = null
    @field:Element(name = "CV2", type = String::class, required=false)
    var cv2: ByteArray? = null
    @field:Element(name = "PostCode", type = String::class, required=false)
    var postCode: ByteArray? = null
    @field:Element(name = "Address", type = String::class, required=false)
    var address: ByteArray? = null
    @field:Element(name = "EncryptionRSA", required=false)
    var encryptionRSA: String? = null

}
