package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
@Root(name = "ATSResponseType", strict = false)
class ATSResponseType {
    @field:Attribute(name = "Code", required = true)
    lateinit var code: Integer
    @field:Attribute(name = "Category", required = true)
    lateinit var category: Integer
    @field:Attribute(name = "Description", required = false)
    var description: String? = null
}

