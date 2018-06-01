package com.mastercard.ats.aci.domain

enum class CardVerificationType {
    DataNotChecked,
    AllMatch,
    SecurityCodeMatchOnly,
    AddressMatchOnly,
    NoDataMatches
}