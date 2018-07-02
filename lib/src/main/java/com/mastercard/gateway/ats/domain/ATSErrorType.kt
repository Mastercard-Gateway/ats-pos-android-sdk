package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigInteger

@Root(name = "ATSErrorType", strict = false)
data class ATSErrorType(
        @field:Attribute(name = "Category", required = true) val category: BigInteger?,
        @field:Attribute(name = "Code", required = true) val code: BigInteger?,
        @field:Attribute(name = "Description", required = false) var description: String? = null,
        @field:Attribute(name = "Severity", required = false) var severity: BigInteger? = null
)
