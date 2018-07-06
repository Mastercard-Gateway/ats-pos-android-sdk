package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal

@Root(name = "SaleItemType")
class SaleItemType {

    @field:Element(name = "UnitMeasure", required=false)
    var unitMeasure: UnitOfMeasureCode? = null
    @field:Element(name = "UnitPrice", required=false)
    var unitPrice: BigDecimal? = null
    @field:Element(name = "Quantity", required=false)
    var quantity: BigDecimal? = null
    @field:Element(name = "TaxCode", required=false)
    var taxCode: String? = null
    @field:Element(name = "AdditionalProductCode", required=false)
    var additionalProductCode: Long? = null
    @field:Element(name = "AdditionalProductInfo", required=false)
    var additionalProductInfo: String? = null
    @field:Element(name = "TypeMovement", required=false)
    var typeMovement: String? = null
    @field:Element(name = "SaleChannel", required=false)
    var saleChannel: String? = null
    @field:Element(name = "VATRate", required=false)
    var vatRate: BigDecimal? = null
    @field:Element(name = "ProductCode", required = true)
    lateinit var productCode: Integer
    @field:Element(name = "Amount", required = true)
    lateinit var amount: BigDecimal
    @field:Attribute(name = "ItemID", required = true)
    lateinit var itemID: String

}


