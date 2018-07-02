package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "OARefundOnline")
enum class OARefundOnline {
    All,
    OfflineA,
    OfflineB,
    IfConnected
}
