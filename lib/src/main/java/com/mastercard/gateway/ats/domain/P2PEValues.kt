package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Suppress("ArrayInDataClass")
@Root(name = "P2PEValues")
@Order(elements = ["dataIV", "dataKSN", "encryptedData", "paniv", "panksn", "encryptedPAN"])
class P2PEValues {
    @field:Element(name = "DataIV", required=false)
    var dataIV: ByteArray? = null
    @field:Element(name = "DataKSN", required=false)
    var dataKSN: ByteArray? = null
    @field:Element(name = "EncryptedData", required=false)
    var encryptedData: ByteArray? = null
    @field:Element(name = "PANIV", required=false)
    var paniv: ByteArray? = null
    @field:Element(name = "PANKSN", required=false)
    var panksn: ByteArray? = null
    @field:Element(name = "EncryptedPAN", required=false)
    var encryptedPAN: ByteArray? = null
}
