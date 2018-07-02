package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "OAReversalMode")
enum class OAReversalMode {
    Any,
    SameDay,
    Consecutive,
    Implicit
}
