package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import javax.xml.datatype.XMLGregorianCalendar

@Suppress("ArrayInDataClass")
@Root(name = "E2EEValues", strict = false)
@Order(elements = arrayOf("encryptionIvDt", "encryptionKeyData", "encryptedTrack2", "encryptedCardPAN"))
data class E2EEValues(
        @field:Element(name = "EncryptionIvDt") var encryptionIvDt: XMLGregorianCalendar? = null,
        @field:Element(name = "EncryptionKeyData", type = String::class) var encryptionKeyData: ByteArray? = null,
        @field:Element(name = "EncryptedTrack2", type = String::class) var encryptedTrack2: ByteArray? = null,
        @field:Element(name = "EncryptedCardPAN", type = String::class) var encryptedCardPAN: ByteArray? = null
)
