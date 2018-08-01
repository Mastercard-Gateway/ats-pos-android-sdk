package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "AdditionalResponseType")
enum class AdditionalResponseType {
    NotPassed,
    NotChecked,
    Match,
    NotMatched,
    PartialMatch;
}
