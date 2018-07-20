package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "CharStyleType")
enum class CharStyleType {
    Normal,
    Bold,
    Italic,
    Underlined
}