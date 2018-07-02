package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "RequestResultType")
enum class RequestResultType {
    Success,
    PartialFailure,
    Failure,
    DeviceUnavailable,
    Busy,
    LoggedOut,
    Aborted,
    TimedOut,
    CommunicationError,
    FormatError,
    ParsingError,
    ValidationError,
    MissingMandatoryData,
    UnknownCard,
    NotSupported
}
