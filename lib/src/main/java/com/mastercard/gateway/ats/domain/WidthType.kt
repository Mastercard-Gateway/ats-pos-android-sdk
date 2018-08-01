package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name="WidthType")
enum class WidthType {
    Single,
    Double,
    Half
}
