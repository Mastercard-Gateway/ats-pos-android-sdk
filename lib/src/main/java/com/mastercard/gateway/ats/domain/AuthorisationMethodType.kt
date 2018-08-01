package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "AuthorisationMethodType")
enum class AuthorisationMethodType {
    Online,
    Offline,
    Manual,
    Inhouse
}
