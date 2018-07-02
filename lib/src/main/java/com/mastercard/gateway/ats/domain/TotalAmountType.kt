package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigDecimal

@Root(name = "TotalAmountType")
data class TotalAmountType(var value: BigDecimal? = null,
                           @field:Attribute(name = "Currency") var currency: CurrencyCode? = null,
                           @field:Attribute(name = "PaymentAmount") var paymentAmount: BigDecimal? = null,
                           @field:Attribute(name = "CashBackAmount") var cashBackAmount: BigDecimal? = null,
                           @field:Attribute(name = "GratuityAmount") var gratuityAmount: BigDecimal? = null,
                           @field:Attribute(name = "DonationAmount") var donationAmount: BigDecimal? = null,
                           @field:Attribute(name = "Changeable") var changeable: Boolean?? = null,
                           @field:Attribute(name = "OriginalAmount") var originalAmount: BigDecimal? = null,
                           @field:Attribute(name = "AvailableAmount") var availableAmount: BigDecimal)
