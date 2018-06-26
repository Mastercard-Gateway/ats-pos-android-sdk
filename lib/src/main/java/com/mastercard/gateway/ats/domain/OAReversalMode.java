//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OAReversalMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OAReversalMode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Any"/&gt;
 *     &lt;enumeration value="SameDay"/&gt;
 *     &lt;enumeration value="Consecutive"/&gt;
 *     &lt;enumeration value="Implicit"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "OAReversalMode")
@XmlEnum
public enum OAReversalMode {

    @XmlEnumValue("Any")
    ANY("Any"),
    @XmlEnumValue("SameDay")
    SAME_DAY("SameDay"),
    @XmlEnumValue("Consecutive")
    CONSECUTIVE("Consecutive"),
    @XmlEnumValue("Implicit")
    IMPLICIT("Implicit");
    private final String value;

    OAReversalMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OAReversalMode fromValue(String v) {
        for (OAReversalMode c: OAReversalMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
