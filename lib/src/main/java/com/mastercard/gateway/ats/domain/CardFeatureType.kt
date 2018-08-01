package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "CardFeatureType")
enum class CardFeatureType {

    ATM,
    AllowRefunds,
    AllowDomesticCashback,
    AllowManagerApproval,
    CashOnly,
    ChequeGuarantee,
    ChequeDetails,
    MandatoryOnline,
    PINRequired,
    Fuel,
    AllowCurrency,
    Loyalty,
    ElectronicTopUp,
    Test,
    ICC,
    AllowInternationalCashback,
    Gift,
    Bunkering

}
