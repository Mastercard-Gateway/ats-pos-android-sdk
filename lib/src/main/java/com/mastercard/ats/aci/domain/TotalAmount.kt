package com.mastercard.ats.aci.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "TotalAmount", strict = false)
class TotalAmount {

    // required
    @field:Text
    lateinit var value: String


    // optional
    @field:Attribute(name = "Currency", required = false)
    var currency: String? = null

    @field:Attribute(name = "PaymentAmount", required = false)
    var paymentAmount: String? = null

    @field:Attribute(name = "CashBackAmount", required = false)
    var cashBackAmount: String? = null

    @field:Attribute(name = "GratuityAmount", required = false)
    var gratuityAmount: String? = null

    @field:Attribute(name = "DonationAmount", required = false)
    var donationAmount: String? = null

    @field:Attribute(name = "OriginalAmount", required = false)
    var originalAmount: String? = null

    @field:Attribute(name = "AvailableAmount", required = false)
    var availableAmount: String? = null

    @field:Attribute(name = "Changeable", required = false)
    var changeable: Boolean? = null
}