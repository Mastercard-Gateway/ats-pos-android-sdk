//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * GenericCardTrack image (1, 2, or 3 Ref. ISO)
 * 
 * <p>Java class for CardTrack complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardTrack"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Byte" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTrackByteType" minOccurs="0"/&gt;
 *         &lt;element name="Ascii" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTrackAsciiType" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardTrack", propOrder = {
    "_byte",
    "ascii"
})
@XmlSeeAlso({
    org.nrf_arts.ixretail.namespace.CardServiceRequest.Loyalty.LoyaltyCard.class,
    org.nrf_arts.ixretail.namespace.CardServiceResponse.Loyalty.LoyaltyCard.class
})
public class CardTrack {

    @XmlElement(name = "Byte", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] _byte;
    @XmlElement(name = "Ascii")
    protected String ascii;

    /**
     * Gets the value of the byte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getByte() {
        return _byte;
    }

    /**
     * Sets the value of the byte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setByte(byte[] value) {
        this._byte = value;
    }

    /**
     * Gets the value of the ascii property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAscii() {
        return ascii;
    }

    /**
     * Sets the value of the ascii property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAscii(String value) {
        this.ascii = value;
    }

}
