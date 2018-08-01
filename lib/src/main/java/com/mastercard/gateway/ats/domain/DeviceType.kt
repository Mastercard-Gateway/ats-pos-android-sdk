package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "DeviceType")
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
    Log;
}
