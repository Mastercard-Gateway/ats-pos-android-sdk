//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Specifies the card identification to be transmitted to the application
 * 
 * <p>Java class for CardValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardValueType"&gt;
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
 *         &lt;element name="InString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CardPAN" type="{http://www.nrf-arts.org/IXRetail/namespace}CardPANType" minOccurs="0"/&gt;
 *         &lt;element name="StartDate" type="{http://www.nrf-arts.org/IXRetail/namespace}CardDate" minOccurs="0"/&gt;
 *         &lt;element name="ExpiryDate" type="{http://www.nrf-arts.org/IXRetail/namespace}CardDate" minOccurs="0"/&gt;
 *         &lt;element name="EndDate" type="{http://www.nrf-arts.org/IXRetail/namespace}CardDate" minOccurs="0"/&gt;
 *         &lt;element name="CardCircuit" type="{http://www.nrf-arts.org/IXRetail/namespace}CardCircuitType" minOccurs="0"/&gt;
 *         &lt;element name="IssueNumber" type="{http://www.nrf-arts.org/IXRetail/namespace}IssueNumberType" minOccurs="0"/&gt;
 *         &lt;element name="ServiceCode" type="{http://www.nrf-arts.org/IXRetail/namespace}ServiceCodeType" minOccurs="0"/&gt;
 *         &lt;element name="TokenSalt" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTokenSaltType" minOccurs="0"/&gt;
 *         &lt;element name="Token" type="{http://www.nrf-arts.org/IXRetail/namespace}CardTokenType" minOccurs="0"/&gt;
 *         &lt;element name="CV2" type="{http://www.nrf-arts.org/IXRetail/namespace}CV2Type" minOccurs="0"/&gt;
 *         &lt;element name="PostCode" type="{http://www.nrf-arts.org/IXRetail/namespace}PostCodeType" minOccurs="0"/&gt;
 *         &lt;element name="Address" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="35"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ExtendedPolicy" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="Mileage" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"&gt;
 *               &lt;totalDigits value="6"/&gt;
 *               &lt;fractionDigits value="0"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Registration" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="IDChecked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="Supplementary" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="20"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardValueType", propOrder = {
    "track1",
    "track2",
    "track3",
    "icc",
    "barcode",
    "inString",
    "cardPAN",
    "startDate",
    "expiryDate",
    "endDate",
    "cardCircuit",
    "issueNumber",
    "serviceCode",
    "tokenSalt",
    "token",
    "cv2",
    "postCode",
    "address",
    "extendedPolicy",
    "mileage",
    "registration",
    "idChecked",
    "supplementary"
})
public class CardValueType {

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
    @XmlElement(name = "InString")
    protected String inString;
    @XmlElement(name = "CardPAN")
    protected String cardPAN;
    @XmlElement(name = "StartDate")
    protected String startDate;
    @XmlElement(name = "ExpiryDate")
    protected String expiryDate;
    @XmlElement(name = "EndDate")
    protected String endDate;
    @XmlElement(name = "CardCircuit")
    protected String cardCircuit;
    @XmlElement(name = "IssueNumber")
    protected String issueNumber;
    @XmlElement(name = "ServiceCode")
    protected String serviceCode;
    @XmlElement(name = "TokenSalt", type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] tokenSalt;
    @XmlElement(name = "Token")
    protected String token;
    @XmlElement(name = "CV2")
    protected String cv2;
    @XmlElement(name = "PostCode")
    protected String postCode;
    @XmlElement(name = "Address")
    protected String address;
    @XmlElement(name = "ExtendedPolicy")
    protected Object extendedPolicy;
    @XmlElement(name = "Mileage")
    protected BigInteger mileage;
    @XmlElement(name = "Registration")
    protected String registration;
    @XmlElement(name = "IDChecked")
    protected Boolean idChecked;
    @XmlElement(name = "Supplementary")
    protected String supplementary;

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

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the cardCircuit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardCircuit() {
        return cardCircuit;
    }

    /**
     * Sets the value of the cardCircuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardCircuit(String value) {
        this.cardCircuit = value;
    }

    /**
     * Gets the value of the issueNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueNumber() {
        return issueNumber;
    }

    /**
     * Sets the value of the issueNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueNumber(String value) {
        this.issueNumber = value;
    }

    /**
     * Gets the value of the serviceCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * Sets the value of the serviceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceCode(String value) {
        this.serviceCode = value;
    }

    /**
     * Gets the value of the tokenSalt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getTokenSalt() {
        return tokenSalt;
    }

    /**
     * Sets the value of the tokenSalt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenSalt(byte[] value) {
        this.tokenSalt = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the cv2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCV2() {
        return cv2;
    }

    /**
     * Sets the value of the cv2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCV2(String value) {
        this.cv2 = value;
    }

    /**
     * Gets the value of the postCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Sets the value of the postCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostCode(String value) {
        this.postCode = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the extendedPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getExtendedPolicy() {
        return extendedPolicy;
    }

    /**
     * Sets the value of the extendedPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setExtendedPolicy(Object value) {
        this.extendedPolicy = value;
    }

    /**
     * Gets the value of the mileage property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMileage() {
        return mileage;
    }

    /**
     * Sets the value of the mileage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMileage(BigInteger value) {
        this.mileage = value;
    }

    /**
     * Gets the value of the registration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistration() {
        return registration;
    }

    /**
     * Sets the value of the registration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistration(String value) {
        this.registration = value;
    }

    /**
     * Gets the value of the idChecked property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIDChecked() {
        return idChecked;
    }

    /**
     * Sets the value of the idChecked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIDChecked(Boolean value) {
        this.idChecked = value;
    }

    /**
     * Gets the value of the supplementary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplementary() {
        return supplementary;
    }

    /**
     * Sets the value of the supplementary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplementary(String value) {
        this.supplementary = value;
    }

}
