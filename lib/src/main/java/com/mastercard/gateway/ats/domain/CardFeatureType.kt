//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain


/**
 *
 * Java class for CardFeatureType.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 *
 * <pre>
 * &lt;simpleType name="CardFeatureType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="ATM"/&gt;
 * &lt;enumeration value="AllowRefunds"/&gt;
 * &lt;enumeration value="AllowDomesticCashback"/&gt;
 * &lt;enumeration value="AllowManagerApproval"/&gt;
 * &lt;enumeration value="CashOnly"/&gt;
 * &lt;enumeration value="ChequeGuarantee"/&gt;
 * &lt;enumeration value="ChequeDetails"/&gt;
 * &lt;enumeration value="MandatoryOnline"/&gt;
 * &lt;enumeration value="PINRequired"/&gt;
 * &lt;enumeration value="Fuel"/&gt;
 * &lt;enumeration value="AllowCurrency"/&gt;
 * &lt;enumeration value="Loyalty"/&gt;
 * &lt;enumeration value="ElectronicTopUp"/&gt;
 * &lt;enumeration value="Test"/&gt;
 * &lt;enumeration value="ICC"/&gt;
 * &lt;enumeration value="AllowInternationalCashback"/&gt;
 * &lt;enumeration value="Gift"/&gt;
 * &lt;enumeration value="Bunkering"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
</pre> *
 *
 */
enum class CardFeatureType {

    ATM,
    AllowRefunds,
    AllowDomesticCashback,
    AllowManagerApproval,
    CashOnly,
    ChequeGuarantee,
    ChequeDetails,
    MandatoryOnline,
    PINRequired,
    Fuel,
    AllowCurrency,
    Loyalty,
    ElectronicTopUp,
    Test,
    ICC,
    AllowInternationalCashback,
    Gift,
    Bunkering

}
