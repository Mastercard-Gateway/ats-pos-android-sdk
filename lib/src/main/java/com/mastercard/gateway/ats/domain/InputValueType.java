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
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the type of input received from the device (some are card reader specific, other PinPad/Cashier Keyboard specific)
 * 
 * <p>Java class for InputValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InputValueType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Track1" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTrack" minOccurs="0"/&gt;
 *         &lt;element name="Track2" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTrack" minOccurs="0"/&gt;
 *         &lt;element name="Track3" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTrack" minOccurs="0"/&gt;
 *         &lt;element name="ICC" type="{http://www.nrf-arts.org/IXRetail/namespace}SecureDataFlow" minOccurs="0"/&gt;
 *         &lt;element name="Barcode" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="8"/&gt;
 *               &lt;maxLength value="14"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="InBoolean" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="InNumber" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;pattern value="[0-9\.]+"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="InString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CardPAN" type="{http://www.nrf-arts.org/IXRetail/namespace}CardPANType" minOccurs="0"/&gt;
 *         &lt;element name="StartDate" type="{http://www.nrf-arts.org/IXRetail/namespace}CardDate" minOccurs="0"/&gt;
 *         &lt;element name="ExpiryDate" type="{http://www.nrf-arts.org/IXRetail/namespace}CardDate" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InputValueType", propOrder = {
    "track1",
    "track2",
    "track3",
    "icc",
    "barcode",
    "inBoolean",
    "inNumber",
    "inString",
    "cardPAN",
    "startDate",
    "expiryDate"
})
public class InputValueType {

    @XmlElement(name = "Track1")
    protected CardTrack track1;
    @XmlElement(name = "Track2")
    protected CardTrack track2;
    @XmlElement(name = "Track3")
    protected CardTrack track3;
    @XmlElement(name = "ICC")
    protected SecureDataFlow icc;
    @XmlElement(name = "Barcode")
    protected String barcode;
    @XmlElement(name = "InBoolean")
    protected Boolean inBoolean;
    @XmlElement(name = "InNumber")
    protected String inNumber;
    @XmlElement(name = "InString")
    protected String inString;
    @XmlElement(name = "CardPAN")
    protected String cardPAN;
    @XmlElement(name = "StartDate")
    protected String startDate;
    @XmlElement(name = "ExpiryDate")
    protected String expiryDate;

    /**
     * Gets the value of the track1 property.
     * 
     * @return
     *     possible object is
     *     {@link CardTrack }
     *     
     */
    public CardTrack getTrack1() {
        return track1;
    }

    /**
     * Sets the value of the track1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardTrack }
     *     
     */
    public void setTrack1(CardTrack value) {
        this.track1 = value;
    }

    /**
     * Gets the value of the track2 property.
     * 
     * @return
     *     possible object is
     *     {@link CardTrack }
     *     
     */
    public CardTrack getTrack2() {
        return track2;
    }

    /**
     * Sets the value of the track2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardTrack }
     *     
     */
    public void setTrack2(CardTrack value) {
        this.track2 = value;
    }

    /**
     * Gets the value of the track3 property.
     * 
     * @return
     *     possible object is
     *     {@link CardTrack }
     *     
     */
    public CardTrack getTrack3() {
        return track3;
    }

    /**
     * Sets the value of the track3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardTrack }
     *     
     */
    public void setTrack3(CardTrack value) {
        this.track3 = value;
    }

    /**
     * Gets the value of the icc property.
     * 
     * @return
     *     possible object is
     *     {@link SecureDataFlow }
     *     
     */
    public SecureDataFlow getICC() {
        return icc;
    }

    /**
     * Sets the value of the icc property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecureDataFlow }
     *     
     */
    public void setICC(SecureDataFlow value) {
        this.icc = value;
    }

    /**
     * Gets the value of the barcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * Sets the value of the barcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarcode(String value) {
        this.barcode = value;
    }

    /**
     * Gets the value of the inBoolean property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInBoolean() {
        return inBoolean;
    }

    /**
     * Sets the value of the inBoolean property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInBoolean(Boolean value) {
        this.inBoolean = value;
    }

    /**
     * Gets the value of the inNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInNumber() {
        return inNumber;
    }

    /**
     * Sets the value of the inNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInNumber(String value) {
        this.inNumber = value;
    }

    /**
     * Gets the value of the inString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInString() {
        return inString;
    }

    /**
     * Sets the value of the inString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInString(String value) {
        this.inString = value;
    }

    /**
     * Gets the value of the cardPAN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardPAN() {
        return cardPAN;
    }

    /**
     * Sets the value of the cardPAN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardPAN(String value) {
        this.cardPAN = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(String value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the expiryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the value of the expiryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiryDate(String value) {
        this.expiryDate = value;
    }

}
