package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigInteger

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
@Root(name = "ATSErrorType", strict = false)
class ATSErrorType {

    @field:Attribute(name = "Category", required = true)
    lateinit var category: Integer
    @field:Attribute(name = "Code", required = true)
    lateinit var code: Integer
    @field:Attribute(name = "Description", required = false)
    var description: String? = null
    @field:Attribute(name = "Severity", required = false)
    var severity: BigInteger? = null

}
