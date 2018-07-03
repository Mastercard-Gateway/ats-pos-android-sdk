package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "ICCType", strict = false)
@Order(elements = ["aid", "applicationLabel", "ac", "aip", "arc", "atc", "auc", "upn", "tvr", "ctt", "cid", "cvmResults", "termCapUsed", "ictyc", "iavn", "tavn", "language", "tsi", "iacdef", "iacdnl", "iaconl", "iappd", "ctlsFormFactorTag", "ctlsFormFactorValue", "ctlsCustExcData", "iauthd", "isd", "isr"])
class ICCType {

    @Element(name = "AID")
    var aid: String? = null
    @Element(name = "ApplicationLabel")
    var applicationLabel: String? = null
    @Element(name = "AC", type = String::class)
    var ac: ByteArray? = null
    @Element(name = "AIP", type = String::class)
    var aip: ByteArray? = null
    @Element(name = "ARC")
    var arc: String? = null
    @Element(name = "ATC", type = String::class)
    var atc: ByteArray? = null
    @Element(name = "AUC", type = String::class)
    var auc: ByteArray? = null
    @Element(name = "UPN", type = String::class)
    var upn: ByteArray? = null
    @Element(name = "TVR", type = String::class)
    var tvr: ByteArray? = null
    @Element(name = "CTT", type = String::class)
    var ctt: ByteArray? = null
    @Element(name = "CID", type = String::class)
    var cid: ByteArray? = null
    @Element(name = "CVMResults", type = String::class)
    var cvmResults: ByteArray? = null
    @Element(name = "TermCapUsed", type = String::class)
    var termCapUsed: ByteArray? = null
    @Element(name = "ICTYC")
    var ictyc: String? = null
    @Element(name = "IAVN", type = String::class)
    var iavn: ByteArray? = null
    @Element(name = "TAVN", type = String::class)
    var tavn: ByteArray? = null
    @Element(name = "Language")
    var language: String? = null
    @Element(name = "TSI", type = String::class)
    var tsi: ByteArray? = null
    @Element(name = "IACDEF", type = String::class)
    var iacdef: ByteArray? = null
    @Element(name = "IACDNL", type = String::class)
    var iacdnl: ByteArray? = null
    @Element(name = "IACONL", type = String::class)
    var iaconl: ByteArray? = null
    @Element(name = "IAPPD", type = String::class)
    var iappd: ByteArray? = null
    @Element(name = "CtlsFormFactorTag", type = String::class)
    var ctlsFormFactorTag: ByteArray? = null
    @Element(name = "CtlsFormFactorValue", type = String::class)
    var ctlsFormFactorValue: ByteArray? = null
    @Element(name = "CtlsCustExcData", type = String::class)
    var ctlsCustExcData: ByteArray? = null
    @Element(name = "IAUTHD", type = String::class)
    var iauthd: ByteArray? = null
    @Element(name = "ISD", type = String::class)
    var isd: ByteArray? = null
    @Element(name = "ISR", type = String::class)
    var isr: ByteArray? = null
}
