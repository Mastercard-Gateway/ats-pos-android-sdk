package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "MACType")
class MACType {
    @field:Element(name = "Hex", required = true, type = String::class)
    lateinit var hex: List<ByteArray>
}
