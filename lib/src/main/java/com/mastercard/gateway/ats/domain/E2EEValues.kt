package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import javax.xml.datatype.XMLGregorianCalendar

@Root(name = "E2EEValues", strict = false)
@Order(elements = ["encryptionIvDt", "encryptionKeyData", "encryptedTrack2", "encryptedCardPAN"])
class E2EEValues {
    @Element(name = "EncryptionIvDt") var encryptionIvDt: XMLGregorianCalendar? = null
    @Element(name = "EncryptionKeyData", type = String::class) var encryptionKeyData: ByteArray? = null
    @Element(name = "EncryptedTrack2", type = String::class) var encryptedTrack2: ByteArray? = null
    @Element(name = "EncryptedCardPAN", type = String::class) var encryptedCardPAN: ByteArray? = null
}
