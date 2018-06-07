package com.mastercard.ats.i2b

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class IP2BluetoothAdapter {

    companion object {
        const val REQUEST_ENABLE_BT = 1
    }

    interface onDevicesDiscoveryListener {
        fun onDevicesDiscovered(devices: List<String>)
    }

    private lateinit var activity: Activity

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private var callback: onDevicesDiscoveryListener? = null

    private val discoveredDevicesListSubject: PublishSubject<List<String>> = PublishSubject.create()

    private val discoveredDevicesReceiver = object : BroadcastReceiver() {

        val list = mutableListOf<String>()

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {

            val action = intent?.action

            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    device?.name?.apply {
                        list.add(this)
                    }
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    callback?.onDevicesDiscovered(list)
                    discoveredDevicesListSubject.onNext(list)
                }
                else -> Unit
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun init(activity: Activity) {

        this.activity = activity

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

    fun getBluetoothDevices(listener: onDevicesDiscoveryListener) {
        this.callback = listener
        scanForBluetoothDevices()
    }

    @SuppressLint("MissingPermission")
    private fun scanForBluetoothDevices() {

        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }

        bluetoothAdapter.startDiscovery()
    }

    fun cancel() {
        println("Adapter Cancel")
        activity.unregisterReceiver(discoveredDevicesReceiver)
    }

}