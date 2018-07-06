package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "ATSErrorDetailType", strict = false)
class ATSErrorDetailType {
    @field:Element(name = "Error", required = true)
    lateinit var error: ATSErrorType
    @field:Element(name = "Response", required = false)
    var response: ATSResponseType? = null
}
