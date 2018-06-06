package com.mastercard.ats.i2b

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.mastercard.ats.R


class I2BController {

    companion object {
        const val REQUEST_ENABLE_BT = 1
    }

    private lateinit var activity: Activity

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // If it's already paired, skip it, because it's been listed already
                if (device.bondState != BluetoothDevice.BOND_BONDED) {
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {

            }
        }
    }

    fun init(activity: Activity) {

        this.activity = activity

        if (bluetoothAdapter == null) {
            println("Bluetooth Adapter not supported")
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        /* // Register for broadcasts when a device is discovered
         var filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
         activity.registerReceiver(receiver, filter)

         // Register for broadcasts when discovery has finished
         filter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
         activity.registerReceiver(receiver, filter)*/

    }


    fun scan() {
        // If we're already discovering, stop it
        if (bluetoothAdapter.isDiscovering) {
            bluetoothAdapter.cancelDiscovery()
        }

        // Request discover from BluetoothAdapter
        bluetoothAdapter.startDiscovery()
    }

    /**
     *  Displays a list of paired devices as a dialog
     */
    fun displayPairedDevices() {

        // fetch the list of paired devices first
        val pairedDevices = bluetoothAdapter.bondedDevices

        if (pairedDevices.isNotEmpty()) {

            val list = mutableListOf<String>()
            for (device in pairedDevices) {
                list.add(device.name + "\n${device.address}")
            }

            // Display a dialog with a list of devices
            AlertDialog.Builder(activity)
                    .setSingleChoiceItems(list.toTypedArray(), -1, null)
                    .setNeutralButton(R.string.scan, DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                        val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                        val info = list.get(selectedPosition)
                        val address = info.substring(info.length - 17)
                        println("address : $address")
                    })
                    .show()
        }

    }


}