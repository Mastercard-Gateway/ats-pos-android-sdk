package com.mastercard.gateway.ats.domain

/**
 * <p>Java class for InputType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InputType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="OpenData"/&gt;
 *     &lt;enumeration value="SecureFlow"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */

enum class InputType {
    OpenData,
    SecureFlow
}