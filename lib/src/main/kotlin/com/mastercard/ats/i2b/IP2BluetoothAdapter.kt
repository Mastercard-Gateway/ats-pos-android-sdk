package com.mastercard.ats.i2b

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

@SuppressLint("MissingPermission")
class IP2BluetoothAdapter(private val activity: Activity) {

    companion object {
        const val REQUEST_ENABLE_BT = 1
    }

    interface OnDevicesDiscoveryListener {
        fun onDevicesDiscovered(devices: List<String>)
    }

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val connectionService = BluetoothConnectionService()

    private var callback: OnDevicesDiscoveryListener? = null
    private val discoveredDevicesListSubject: PublishSubject<List<String>> = PublishSubject.create()
    private val discoveredDevicesReceiver = object : BroadcastReceiver() {

        val list = mutableListOf<String>()


        override fun onReceive(context: Context?, intent: Intent?) {

            val action = intent?.action

            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if (isDeviceAllowed(device)) list.add(device.name)
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    //append paired devices on the top
                    list.addAll(0, getPairedDevices())

                    callback?.onDevicesDiscovered(list)
                    discoveredDevicesListSubject.onNext(list)
                }
                else -> Unit
            }
        }
    }

    fun init() {

        if (bluetoothAdapter == null) {
            //TODO : Notify user or throw exception
            println("Bluetooth Adapter not supported")
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        // Register for broadcasts when a device is discovered
        var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity.registerReceiver(discoveredDevicesReceiver, filter)

        // Register for broadcasts when discovery has finished
        filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        activity.registerReceiver(discoveredDevicesReceiver, filter)

    }

    fun getBluetoothDevices(): Observable<List<String>> {
        scanForBluetoothDevices()
        return discoveredDevicesListSubject
    }

    fun getBluetoothDevices(listener: OnDevicesDiscoveryListener) {
        this.callback = listener
        scanForBluetoothDevices()
    }

    private fun scanForBluetoothDevices() {

        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }

        bluetoothAdapter.startDiscovery()
    }

    fun isDeviceAllowed(device: BluetoothDevice?): Boolean {

        device?.name?.apply {
            return true
        }

        return false
    }

    fun getPairedDevices(): List<String> {
        val pairedDevices = mutableListOf<String>()
        bluetoothAdapter.bondedDevices.filter { isDeviceAllowed(it) }.map { it.name }.toCollection(pairedDevices)
        return pairedDevices
    }

    fun cancel() {
        activity.unregisterReceiver(discoveredDevicesReceiver)
    }

    fun connectTo(deviceName : String) {
        connectionService.connectTo(deviceName = deviceName, secure = true, attempts = 2)
    }
}