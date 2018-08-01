package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "CardVerificationType")
enum class CardVerificationType {
    DataNotChecked,
    AllMatch,
    SecurityCodeMatchOnly,
    AddressMatchOnly,
    NoDataMatches
}
