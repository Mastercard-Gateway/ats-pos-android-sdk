package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "EventTypes")
enum class EventTypes {
    CardInserted,
    DispenserSelected,
    ObtainAuth
}
