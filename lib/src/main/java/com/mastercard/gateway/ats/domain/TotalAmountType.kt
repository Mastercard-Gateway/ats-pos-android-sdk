package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigDecimal

@Root(name = "TotalAmountType")
data class TotalAmountType(var value: BigDecimal? = null) {

    @field:Attribute(name = "Currency", required=false)
    var currency: CurrencyCode? = null
    @field:Attribute(name = "PaymentAmount", required=false)
    var paymentAmount: BigDecimal? = null
    @field:Attribute(name = "CashBackAmount", required=false)
    var cashBackAmount: BigDecimal? = null
    @field:Attribute(name = "GratuityAmount", required=false)
    var gratuityAmount: BigDecimal? = null
    @field:Attribute(name = "DonationAmount", required=false)
    var donationAmount: BigDecimal? = null
    @field:Attribute(name = "Changeable", required=false)
    var changeable: Boolean?? = null
    @field:Attribute(name = "OriginalAmount", required=false)
    var originalAmount: BigDecimal? = null
    @field:Attribute(name = "AvailableAmount", required=false)
    var availableAmount: BigDecimal? = null

}
