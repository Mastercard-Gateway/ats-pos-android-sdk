package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "CardCircuitStateType")
enum class CardCircuitStateType {
    Accepted,
    Denied
}
