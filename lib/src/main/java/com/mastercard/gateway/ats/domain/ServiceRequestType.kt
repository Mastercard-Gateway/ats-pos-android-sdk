package com.mastercard.gateway.ats.domain

enum class ServiceRequestType {
    Diagnosis,
    SendOfflineTransactions,
    Reconciliation,
    ReconciliationWithClosure,
    GlobalReconciliation,
    GlobalReconciliationWithClosure,
    Administration,
    Login,
    Logoff,
    OnlineAgent,
    RepeatLastMessage,
    GetReference,
    EncryptionRSAPublicKey,
    AcquireDevice,
    ReleaseDevice,
    RuleLookup,
    PINPadProgramLoad,
    QuerySoftwareVersions,
    MemoryReport
}
