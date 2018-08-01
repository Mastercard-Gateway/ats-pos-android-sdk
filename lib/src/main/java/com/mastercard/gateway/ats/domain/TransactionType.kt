package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "TransactionType")
enum class TransactionType {
    Credit,
    Debit,
    Gratuity,
    GratuityRefund,
    Donation
}
