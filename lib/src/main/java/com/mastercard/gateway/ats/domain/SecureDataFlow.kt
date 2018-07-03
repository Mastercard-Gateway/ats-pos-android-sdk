package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "SecureDataFlow")
data class SecureDataFlow(@field:Element(name = "Hex", required = true, type = String::class) protected var hex: List<ByteArray>)
