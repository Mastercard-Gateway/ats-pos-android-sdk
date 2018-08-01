package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "HeightType")
enum class HeightType {
    Single,
    Double,
    Half
}
