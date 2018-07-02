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
        @field:Element(name = "ProductCode", required = true)  var productCode: BigInteger,
        @field:Element(name = "Amount", required = true)  var amount: BigDecimal,
        @field:Element(name = "UnitMeasure")  var unitMeasure: UnitOfMeasureCode? = null,
        @field:Element(name = "UnitPrice")  var unitPrice: BigDecimal? = null,
        @field:Element(name = "Quantity")  var quantity: BigDecimal? = null,
        @field:Element(name = "TaxCode")  var taxCode: String? = null,
        @field:Element(name = "AdditionalProductCode")  var additionalProductCode: Long? = null,
        @field:Element(name = "AdditionalProductInfo")  var additionalProductInfo: String? = null,
        @field:Element(name = "TypeMovement")  var typeMovement: String? = null,
        @field:Element(name = "SaleChannel")  var saleChannel: String? = null,
        @field:Element(name = "VATRate")  var vatRate: BigDecimal? = null,
        @field:Attribute(name = "ItemID", required = true)  var itemID: String
)


