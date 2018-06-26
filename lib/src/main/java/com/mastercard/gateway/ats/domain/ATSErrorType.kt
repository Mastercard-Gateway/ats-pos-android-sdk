//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.math.BigInteger


/**
 *
 * Java class for ATSErrorType complex type.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ATSErrorType"&gt;
 * &lt;complexContent&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 * &lt;attribute name="Category" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 * &lt;attribute name="Code" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 * &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 * &lt;attribute name="Severity" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 * &lt;/restriction&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
</pre> *
 *
 *
 */
@Root(name = "ATSErrorType", strict = false)
data class ATSErrorType(
        @field:Attribute(name = "Category", required = true) val category: BigInteger?,
        @field:Attribute(name = "Code", required = true) val code: BigInteger?,
        @field:Attribute(name = "Description", required = false) var description: String? = null,
        @field:Attribute(name = "Severity", required = false) var severity: BigInteger? = null
)
