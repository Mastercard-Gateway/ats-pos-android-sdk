package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "DeviceRequestType")
enum class DeviceRequestType {

    Input,
    Output,
    SecureInput,
    SecureOutput,
    AbortInput,
    AbortOutput,
    Event,
    RepeatLastMessage

}
