package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import javax.xml.datatype.XMLGregorianCalendar

@Root(name = "E2EEValues", strict = false)
@Order(elements = ["encryptionIvDt", "encryptionKeyData", "encryptedTrack2", "encryptedCardPAN"])
class E2EEValues {
    @field:Element(name = "EncryptionIvDt", required=false) var encryptionIvDt: XMLGregorianCalendar? = null
    @field:Element(name = "EncryptionKeyData", type = String::class, required=false) var encryptionKeyData: ByteArray? = null
    @field:Element(name = "EncryptedTrack2", type = String::class, required=false) var encryptedTrack2: ByteArray? = null
    @field:Element(name = "EncryptedCardPAN", type = String::class, required=false) var encryptedCardPAN: ByteArray? = null
}
