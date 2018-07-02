package com.mastercard.gateway.ats.domain


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
