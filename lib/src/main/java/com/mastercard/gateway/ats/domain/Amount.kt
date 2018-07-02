package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root
import org.simpleframework.xml.Text
import java.math.BigDecimal

@Root(name = "Amount", strict = false)
data class Amount(@Text(required = true) var value: BigDecimal)
