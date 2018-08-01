package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "AlignmentType")
enum class AlignmentType {
    Left,
    Right,
    Center,
    Justified
}
