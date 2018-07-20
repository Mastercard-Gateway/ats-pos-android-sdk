package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "ColorType")
enum class ColorType {
    White,
    Black,
    Red,
    Green,
    Yellow,
    Blue,
    Grey,
    Brown
}
