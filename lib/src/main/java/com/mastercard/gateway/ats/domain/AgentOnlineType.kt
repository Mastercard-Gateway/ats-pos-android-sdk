package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "AgentOnlineType")
enum class AgentOnlineType {
    /**
     * Recharge of Prepaid cards/accounts of mobile phones. No payment included.
     *
     */
    MobilePhonePrepaid,
    MobilePhoneAuthorization,
    MobilePhoneAuthorisation
}
