package com.mastercard.gateway.ats

import android.bluetooth.BluetoothDevice

sealed class ATSBluetoothConfiguration(val bluetoothDevice: BluetoothDevice) {
    class Static(val port: Int, bluetoothDevice: BluetoothDevice) : ATSBluetoothConfiguration(bluetoothDevice)
    class Roaming(val ipAddress: String, val port: Int, bluetoothDevice: BluetoothDevice) : ATSBluetoothConfiguration(bluetoothDevice)
}