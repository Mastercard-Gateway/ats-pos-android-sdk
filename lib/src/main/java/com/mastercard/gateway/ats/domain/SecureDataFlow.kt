package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "SecureDataFlow")
class SecureDataFlow {
    @field:ElementList(name = "Hex", required = true, type = String::class, inline = true) lateinit var hex: List<ByteArray>
}
