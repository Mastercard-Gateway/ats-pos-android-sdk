# ATS POS ANDROID SDK

[![Download](https://api.bintray.com/packages/mpgs/Android/ats-pos-android-sdk/images/download.svg) ](https://bintray.com/mpgs/Android/ats-pos-android-sdk/_latestVersion)
[![Build Status](https://travis-ci.org/mastercard-gateway/ats-pos-android-sdk.svg?branch=master)](https://travis-ci.org/mastercard-gateway/ats-pos-android-sdk)

The Android SDK by Mastercard Payment Gateway Services allows you to integrate ATS-driven payments into your Android app. It provides a few components that can be used to communicate with ATS, expose bluetooth-connected card readers to the network, and collect log information for diagnostics reporting.

For complete documentation on all of these components, consult the wiki.

## Requirements
  - API 19 or higher

## Installation

This library is hosted in the jCenter repository. To import the Android SDK, include it as a dependency in your build.gradle file. Be sure to replace X.X.X with the version number in the shield above.

```
implementation 'com.mastercard.gateway:ats-android:X.X.X'
```

## Usage

## Initialize the ATS Client SDK

The `ATS Client SDK` is a communication client for sending messages to/from an ATS server

In order to use the `ATS Client SDK` you need to initialize it with a suitable configuration. This allows you to connect to ATS over a persistent socket connection and send/receive messages.

```kotlin
val atsClient = ATSClient()

val atsIpAddress = "1.1.1.1"
val atsPort = 20002

atsClient.connect(atsIpAddress, atsPort)
```

## Initialize the ATS Device SDK

The `ATS Device SDK` is a communication client for sending messages to/from an Bluetooth PED. When both `ATS Client SDK` and `ATS Device SDK` are initialized the `ATS Device SDK` acts as a proxy between ATS and bluetooth-connected PEDs.

The `ATS Device SDK` should be initialized with a configurations for a paired bluetooth PED.

```kotlin
val btDevice: BluetoothDevice = getDesiredBluetoothDevice() // user-defined method to get bluetooth device
val port = 1234

val staticConfig = ATSBluetoothConfiguration.Static(port, btDevice)

var adapter = ATSBluetoothAdapter()
adapter.start(staticConfig)
```

## Diagnostics

The ATS SDK includes a diagnostic utility that can help troubleshoot any issues that you might encounter during a transaction. initialize the diagnostic utility to gather logs during the transaction lifecycle.

```kotlin
ATSDiagnostics.startLogCapture()
```

To retrieve the log after some activity:

```kotlin
val log: String = ATSDiagnostics.getLog()
```

To stop capturing log data, you can clean it up like this:

```kotlin
ATSDiagnostics.clearLog()
ATSDiagnostics.stopLogCapture()
```


## Starting a Transaction

Once you have initialized an instance of `ATSClient`, you can then construct a `CardServiceRequest` containing the transaction information and send that to the ATS server to start a transaction.

```kotlin
val posData = CardServiceRequest.POSData()
posData.posTimeStamp = Date()
posData.transactionNumber = transactionNumber
posData.reference = reference

val totalAmountType = TotalAmountType()
totalAmountType.value = BigDecimal(amount)
totalAmountType.paymentAmount = BigDecimal(amount)

// create the CardServiceRequest and populate the optional fields that we need for this transaction.
val request = CardServiceRequest()
request.requestType = CardRequestType.CardPayment
request.workstationID = "some workstation ID"
request.requestID = "1"
request.applicationSender = "ATSClient"
request.posData = posData
request.totalAmount = totalAmountType
 
// send the request
atsClient.sendMessage(request)
```

---

## Sample App
This project includes a sample app to demonstrate SDK usage. To run the sample app you will need to ensure you have a suitable ATS Server instance configured and started to accept transactions.
