//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.26 at 04:13:46 PM CDT 
//


package com.mastercard.gateway.ats.domain


/**
 *
 * Java class for DeviceType.
 *
 *
 * The following schema fragment specifies the expected content contained within this class.
 *
 *
 * <pre>
 * &lt;simpleType name="DeviceType"&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 * &lt;enumeration value="CashierDisplay"/&gt;
 * &lt;enumeration value="CustomerDisplay"/&gt;
 * &lt;enumeration value="PrinterReceipt"/&gt;
 * &lt;enumeration value="Printer"/&gt;
 * &lt;enumeration value="ICCrw"/&gt;
 * &lt;enumeration value="CardReader"/&gt;
 * &lt;enumeration value="PinEntryDeviceCardReader"/&gt;
 * &lt;enumeration value="PinPad"/&gt;
 * &lt;enumeration value="PEDReaderPrinter"/&gt;
 * &lt;enumeration value="MSR"/&gt;
 * &lt;enumeration value="RFID"/&gt;
 * &lt;enumeration value="BarcodeScanner"/&gt;
 * &lt;enumeration value="CashierKeyboard"/&gt;
 * &lt;enumeration value="CashierTerminal"/&gt;
 * &lt;enumeration value="CustomerKeyboard"/&gt;
 * &lt;enumeration value="CustomerTerminal"/&gt;
 * &lt;enumeration value="Log"/&gt;
 * &lt;/restriction&gt;
 * &lt;/simpleType&gt;
</pre> *
 *
 */
enum class DeviceType {
    CashierDisplay,
    CustomerDisplay,
    PrinterReceipt,
    Printer,
    ICCrw,
    CardReader,
    PinEntryDeviceCardReader,
    PinPad,
    PEDReaderPrinter,
    MSR,
    RFID,
    BarcodeScanner,
    CashierKeyboard,
    CashierTerminal,
    CustomerKeyboard,
    CustomerTerminal,
    Log
}
