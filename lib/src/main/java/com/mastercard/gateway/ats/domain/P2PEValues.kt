package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Suppress("ArrayInDataClass")
@Root(name = "P2PEValues")
@Order(elements = ["dataIV", "dataKSN", "encryptedData", "paniv", "panksn", "encryptedPAN"])
data class P2PEValues(
        @field:Element(name = "DataIV") var dataIV: ByteArray? = null,
        @field:Element(name = "DataKSN") var dataKSN: ByteArray? = null,
        @field:Element(name = "EncryptedData") var encryptedData: ByteArray? = null,
        @field:Element(name = "PANIV") var paniv: ByteArray? = null,
        @field:Element(name = "PANKSN") var panksn: ByteArray? = null,
        @field:Element(name = "EncryptedPAN") var encryptedPAN: ByteArray? = null
)
