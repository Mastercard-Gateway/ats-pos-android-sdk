//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Element
import org.simpleframework.xml.Order
import org.simpleframework.xml.Root


/**
 * Hex chain (encrypted) data - Format free, TLV advised
 *
 *
 *
 * Java class for SecureDataFlow complex type.
 *
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 *
 * <pre>
 * &lt;complexType name="SecureDataFlow"&gt;
 * &lt;complexContent&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 * &lt;sequence&gt;
 * &lt;element name="Hex" type="{http://www.w3.org/2001/XMLSchema}hexBinary" maxOccurs="unbounded"/&gt;
 * &lt;/sequence&gt;
 * &lt;/restriction&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
</pre> *
 */
@Root(name = "SecureDataFlow")
@Order(elements = arrayOf("hex"))
data class SecureDataFlow(
    @Element(name = "Hex", required = true, type = String::class)
    protected var hex: List<ByteArray>? = null)
