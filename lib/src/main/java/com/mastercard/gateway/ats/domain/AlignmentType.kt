//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain

/**
 *
 * Kotlin class for AlignmentType.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 *
 * <pre>
 * &lt;simpleType name="AlignmentType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="Left"/&gt;
 * &lt;enumeration value="Right"/&gt;
 * &lt;enumeration value="Center"/&gt;
 * &lt;enumeration value="Justified"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
</pre> *
 *
 */

enum class AlignmentType {
    Left,
    Right,
    Center,
    Justified
}
