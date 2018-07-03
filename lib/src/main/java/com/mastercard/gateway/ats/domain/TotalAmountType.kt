package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigDecimal

@Root(name = "TotalAmountType")
data class TotalAmountType(var value: BigDecimal? = null) {

    @Attribute(name = "Currency")
    var currency: CurrencyCode? = null
    @Attribute(name = "PaymentAmount")
    var paymentAmount: BigDecimal? = null
    @Attribute(name = "CashBackAmount")
    var cashBackAmount: BigDecimal? = null
    @Attribute(name = "GratuityAmount")
    var gratuityAmount: BigDecimal? = null
    @Attribute(name = "DonationAmount")
    var donationAmount: BigDecimal? = null
    @Attribute(name = "Changeable")
    var changeable: Boolean?? = null
    @Attribute(name = "OriginalAmount")
    var originalAmount: BigDecimal? = null
    @Attribute(name = "AvailableAmount")
    var availableAmount: BigDecimal? = null

}
