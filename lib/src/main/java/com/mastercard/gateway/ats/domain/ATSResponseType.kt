package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigInteger

@Root(name = "ATSResponseType", strict = false)
data class ATSResponseType(@field:Attribute(name = "Code", required = true) var code: BigInteger,
                           @field:Attribute(name = "Category", required = true) var category: BigInteger,
                           @field:Attribute(name = "Description") var description: String? = null
)

