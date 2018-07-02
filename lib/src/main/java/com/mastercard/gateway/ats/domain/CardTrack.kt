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
 * GenericCardTrack image (1, 2, or 3 Ref. ISO)
 *
 *
 * Java class for CardTrack complex type.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CardTrack"&gt;
 * &lt;complexContent&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 * &lt;choice&gt;
 * &lt;element name="Byte" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTrackByteType" minOccurs="0"/&gt;
 * &lt;element name="Ascii" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTrackAsciiType" minOccurs="0"/&gt;
 * &lt;/choice&gt;
 * &lt;/restriction&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
</pre> *
 *
 *
 */
@Root(name = "CardTrack", strict = false)
@Order(elements = arrayOf("_byte", "ascii"))
data class CardTrack(
        @Element(name = "Byte", type = String::class)
        protected var _byte: ByteArray? = null,
        @Element(name = "Ascii")
        protected var ascii: String? = null
)
