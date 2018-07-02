package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigInteger

@Root(name = "ATSErrorType", strict = false)
data class ATSErrorType(@Attribute(name = "Category", required = true) val category: BigInteger, 
                        @Attribute(name = "Code", required = true) val code: BigInteger) {
    @Attribute(name = "Description", required = false)
    var description: String? = null
    @Attribute(name = "Severity", required = false)
    var severity: BigInteger? = null
}
