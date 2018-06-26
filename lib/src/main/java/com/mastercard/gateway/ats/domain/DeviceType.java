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
 * <p>Java class for DeviceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DeviceType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CashierDisplay"/&gt;
 *     &lt;enumeration value="CustomerDisplay"/&gt;
 *     &lt;enumeration value="PrinterReceipt"/&gt;
 *     &lt;enumeration value="Printer"/&gt;
 *     &lt;enumeration value="ICCrw"/&gt;
 *     &lt;enumeration value="CardReader"/&gt;
 *     &lt;enumeration value="PinEntryDeviceCardReader"/&gt;
 *     &lt;enumeration value="PinPad"/&gt;
 *     &lt;enumeration value="PEDReaderPrinter"/&gt;
 *     &lt;enumeration value="MSR"/&gt;
 *     &lt;enumeration value="RFID"/&gt;
 *     &lt;enumeration value="BarcodeScanner"/&gt;
 *     &lt;enumeration value="CashierKeyboard"/&gt;
 *     &lt;enumeration value="CashierTerminal"/&gt;
 *     &lt;enumeration value="CustomerKeyboard"/&gt;
 *     &lt;enumeration value="CustomerTerminal"/&gt;
 *     &lt;enumeration value="Log"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DeviceType")
@XmlEnum
public enum DeviceType {

    @XmlEnumValue("CashierDisplay")
    CASHIER_DISPLAY("CashierDisplay"),
    @XmlEnumValue("CustomerDisplay")
    CUSTOMER_DISPLAY("CustomerDisplay"),
    @XmlEnumValue("PrinterReceipt")
    PRINTER_RECEIPT("PrinterReceipt"),
    @XmlEnumValue("Printer")
    PRINTER("Printer"),
    @XmlEnumValue("ICCrw")
    IC_CRW("ICCrw"),
    @XmlEnumValue("CardReader")
    CARD_READER("CardReader"),
    @XmlEnumValue("PinEntryDeviceCardReader")
    PIN_ENTRY_DEVICE_CARD_READER("PinEntryDeviceCardReader"),
    @XmlEnumValue("PinPad")
    PIN_PAD("PinPad"),
    @XmlEnumValue("PEDReaderPrinter")
    PED_READER_PRINTER("PEDReaderPrinter"),
    MSR("MSR"),
    RFID("RFID"),
    @XmlEnumValue("BarcodeScanner")
    BARCODE_SCANNER("BarcodeScanner"),
    @XmlEnumValue("CashierKeyboard")
    CASHIER_KEYBOARD("CashierKeyboard"),
    @XmlEnumValue("CashierTerminal")
    CASHIER_TERMINAL("CashierTerminal"),
    @XmlEnumValue("CustomerKeyboard")
    CUSTOMER_KEYBOARD("CustomerKeyboard"),
    @XmlEnumValue("CustomerTerminal")
    CUSTOMER_TERMINAL("CustomerTerminal"),
    @XmlEnumValue("Log")
    LOG("Log");
    private final String value;

    DeviceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeviceType fromValue(String v) {
        for (DeviceType c: DeviceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
