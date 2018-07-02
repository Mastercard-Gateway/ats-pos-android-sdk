package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.util.ArrayList

@Root(name = "MACType")
@Order(elements = ["hex"])
data class MACType(@field:Element(name = "Hex", required = true, type = String::class) var hex: List<ByteArray> = ArrayList<ByteArray>())
