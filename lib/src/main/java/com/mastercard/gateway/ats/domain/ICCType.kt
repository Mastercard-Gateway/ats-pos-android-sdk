package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

@Root(name = "ICCType", strict = false)
class ICCType {

    @field:Element(name = "AID", required=false)
    var aid: String? = null
    @field:Element(name = "ApplicationLabel", required=false)
    var applicationLabel: String? = null
    @field:Element(name = "AC", type = String::class, required=false)
    var ac: ByteArray? = null
    @field:Element(name = "AIP", type = String::class, required=false)
    var aip: ByteArray? = null
    @field:Element(name = "ARC", required=false)
    var arc: String? = null
    @field:Element(name = "ATC", type = String::class, required=false)
    var atc: ByteArray? = null
    @field:Element(name = "AUC", type = String::class, required=false)
    var auc: ByteArray? = null
    @field:Element(name = "UPN", type = String::class, required=false)
    var upn: ByteArray? = null
    @field:Element(name = "TVR", type = String::class, required=false)
    var tvr: ByteArray? = null
    @field:Element(name = "CTT", type = String::class, required=false)
    var ctt: ByteArray? = null
    @field:Element(name = "CID", type = String::class, required=false)
    var cid: ByteArray? = null
    @field:Element(name = "CVMResults", type = String::class, required=false)
    var cvmResults: ByteArray? = null
    @field:Element(name = "TermCapUsed", type = String::class, required=false)
    var termCapUsed: ByteArray? = null
    @field:Element(name = "ICTYC", required=false)
    var ictyc: String? = null
    @field:Element(name = "IAVN", type = String::class, required=false)
    var iavn: ByteArray? = null
    @field:Element(name = "TAVN", type = String::class, required=false)
    var tavn: ByteArray? = null
    @field:Element(name = "Language", required=false)
    var language: String? = null
    @field:Element(name = "TSI", type = String::class, required=false)
    var tsi: ByteArray? = null
    @field:Element(name = "IACDEF", type = String::class, required=false)
    var iacdef: ByteArray? = null
    @field:Element(name = "IACDNL", type = String::class, required=false)
    var iacdnl: ByteArray? = null
    @field:Element(name = "IACONL", type = String::class, required=false)
    var iaconl: ByteArray? = null
    @field:Element(name = "IAPPD", type = String::class, required=false)
    var iappd: ByteArray? = null
    @field:Element(name = "CtlsFormFactorTag", type = String::class, required=false)
    var ctlsFormFactorTag: ByteArray? = null
    @field:Element(name = "CtlsFormFactorValue", type = String::class, required=false)
    var ctlsFormFactorValue: ByteArray? = null
    @field:Element(name = "CtlsCustExcData", type = String::class, required=false)
    var ctlsCustExcData: ByteArray? = null
    @field:Element(name = "IAUTHD", type = String::class, required=false)
    var iauthd: ByteArray? = null
    @field:Element(name = "ISD", type = String::class, required=false)
    var isd: ByteArray? = null
    @field:Element(name = "ISR", type = String::class, required=false)
    var isr: ByteArray? = null
}
