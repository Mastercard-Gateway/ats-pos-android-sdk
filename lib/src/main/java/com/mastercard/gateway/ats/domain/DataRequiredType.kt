package com.mastercard.gateway.ats.domain

import org.simpleframework.xml.Root

@Root(name = "DataRequiredType")
enum class DataRequiredType {
    Track2,
    PAN,
    ExpiryDate,
    StartDate,
    IssueNumber,
    Signature,
    Cashback,
    SplitPayment,
    Gratuity,
    Donation,
    Amount,
    Products,
    Mileage,
    Registration,
    Id,
    Supplementary,
    AuthCode,
    CV2,
    PostCode,
    Address,
    FailureAcknowledgement,
    ETUProvider,
    ETUPlan,
    ETUAmount,
    DataNotChecked,
    AllMatch,
    SecurityCodeMatchOnly,
    AddressMatchOnly,
    NoDataMatches,
    FallbackPKE,
    PrintDone,
    PrintRetry,
    DCCQuery,
    DCCConfirm,
    Transaction,
    Password
}
