package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "SeparatorType")
enum class SeparatorType {
    Dot,
    Comma
}

