package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root

import java.math.BigDecimal
import java.math.BigInteger

@Root(name = "SaleItemType")
@Order(elements = ["productCode", "amount", "unitMeasure", "unitPrice", "quantity", "taxCode", "additionalProductCode", "additionalProductInfo", "typeMovement", "saleChannel", "vatRate"])
data class SaleItemType(
        @Element(name = "ProductCode", required = true) var productCode: BigInteger,
        @Element(name = "Amount", required = true) var amount: BigDecimal,
        @Attribute(name = "ItemID", required = true) var itemID: String) {

    @Element(name = "UnitMeasure")
    var unitMeasure: UnitOfMeasureCode? = null
    @Element(name = "UnitPrice")
    var unitPrice: BigDecimal? = null
    @Element(name = "Quantity")
    var quantity: BigDecimal? = null
    @Element(name = "TaxCode")
    var taxCode: String? = null
    @Element(name = "AdditionalProductCode")
    var additionalProductCode: Long? = null
    @Element(name = "AdditionalProductInfo")
    var additionalProductInfo: String? = null
    @Element(name = "TypeMovement")
    var typeMovement: String? = null
    @Element(name = "SaleChannel")
    var saleChannel: String? = null
    @Element(name = "VATRate")
    var vatRate: BigDecimal? = null

}


