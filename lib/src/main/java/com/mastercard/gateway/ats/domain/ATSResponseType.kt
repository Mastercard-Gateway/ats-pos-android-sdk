package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigInteger

@Root(name = "ATSResponseType", strict = false)
data class ATSResponseType(@Attribute(name = "Code", required = true) var code: BigInteger, @Attribute(name = "Category", required = true) var category: BigInteger) {
    @Attribute(name = "Description")
    var description: String? = null
}

