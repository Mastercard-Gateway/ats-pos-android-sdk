package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "ATSErrorDetailType", strict = false)
data class ATSErrorDetailType(@field:Element(name = "Error", required = true) var error: ATSErrorType,
        @field:Element(name = "Response", required = false) var response: ATSResponseType
)
