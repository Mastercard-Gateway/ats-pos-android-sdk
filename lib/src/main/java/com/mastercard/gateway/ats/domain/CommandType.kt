package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "CommandType")
enum class CommandType {

    GetDecimals,
    GetChar,
    GetMenu,
    GetAmount,
    GetAmountNoPrompt,
    GetConfirmation,
    GetAnyKey,
    GetOptionKey,
    CheckPIN,
    ProcessPIN,
    RequestCard,
    RequestTypeCard,
    ReadCard,
    TransferData,
    ValidateMAC,
    CalculateMAC,
    UpdateKeys,
    GetTrack2,
    /**
     * To explicitly address the possibility to extend the commands according to each supplier pin-pad implementation.
     * PinPad independence will be subject for future extension of the interface.
     *
     */
    Other
}
