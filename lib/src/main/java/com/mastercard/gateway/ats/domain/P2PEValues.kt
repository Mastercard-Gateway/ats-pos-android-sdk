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
 * Specifies the P2PE encryption values
 *
 *
 *
 * Java class for P2PEValues complex type.
 *
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 *
 * <pre>
 * &lt;complexType name="P2PEValues"&gt;
 * &lt;complexContent&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 * &lt;sequence&gt;
 * &lt;element name="DataIV" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 * &lt;element name="DataKSN" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 * &lt;element name="EncryptedData" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 * &lt;element name="PANIV" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 * &lt;element name="PANKSN" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 * &lt;element name="EncryptedPAN" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/&gt;
 * &lt;/sequence&gt;
 * &lt;/restriction&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
</pre> *
 */
@Suppress("ArrayInDataClass")
@Root(name = "P2PEValues")
@Order(elements = ["dataIV", "dataKSN", "encryptedData", "paniv", "panksn", "encryptedPAN"])
data class P2PEValues(
        @field:Element(name = "DataIV") var dataIV: ByteArray? = null,
        @field:Element(name = "DataKSN") var dataKSN: ByteArray? = null,
        @field:Element(name = "EncryptedData") var encryptedData: ByteArray? = null,
        @field:Element(name = "PANIV") var paniv: ByteArray? = null,
        @field:Element(name = "PANKSN") var panksn: ByteArray? = null,
        @field:Element(name = "EncryptedPAN") var encryptedPAN: ByteArray? = null
)
