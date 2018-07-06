package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "OAReverseOnline")
enum class OAReverseOnline {

    Any,
    None,
    NotRefunds,
    DPG

}
