package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "ATSErrorDetailType", strict = false)
data class ATSErrorDetailType @JvmOverloads constructor(@Element(name = "Error", required = true) var error: ATSErrorType,
                                                        @Element(name = "Response") var response: ATSResponseType? = null)
