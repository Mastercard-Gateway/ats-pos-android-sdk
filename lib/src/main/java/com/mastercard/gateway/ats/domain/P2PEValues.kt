package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Suppress("ArrayInDataClass")
@Root(name = "P2PEValues")
@Order(elements = ["dataIV", "dataKSN", "encryptedData", "paniv", "panksn", "encryptedPAN"])
class P2PEValues {
    @Element(name = "DataIV")
    var dataIV: ByteArray? = null
    @Element(name = "DataKSN")
    var dataKSN: ByteArray? = null
    @Element(name = "EncryptedData")
    var encryptedData: ByteArray? = null
    @Element(name = "PANIV")
    var paniv: ByteArray? = null
    @Element(name = "PANKSN")
    var panksn: ByteArray? = null
    @Element(name = "EncryptedPAN")
    var encryptedPAN: ByteArray? = null
}
