package com.mastercard.gateway.ats.domain

enum class CardVerificationType {
    DataNotChecked,
    AllMatch,
    SecurityCodeMatchOnly,
    AddressMatchOnly,
    NoDataMatches
}