package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "Text")
data class Text(var value: String? = null)
