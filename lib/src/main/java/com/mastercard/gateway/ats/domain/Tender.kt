package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Tender", strict = false)
class Tender {

    // optional
    @field:Attribute(name = "LanguageCode", required = false)
    var languageCode: String? = null

    @field:Element(name = "TotalAmount", required = false)
    var totalAmount: TotalAmount? = null

    @field:Element(name = "Authorisation", required = false)
    var authorisation: Authorisation? = null
}