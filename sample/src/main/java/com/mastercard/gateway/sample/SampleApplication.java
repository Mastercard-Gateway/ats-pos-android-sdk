package com.mastercard.gateway.sample;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.mastercard.gateway.ats.ATSBluetoothAdapter;
import com.mastercard.gateway.ats.ATSDiagnostics;

import java.util.NoSuchElementException;
import java.util.Set;

public class SampleApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        ATSDiagnostics.setLogLevel(Log.VERBOSE);
        ATSDiagnostics.startLogCapture();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothDevice device = null;

        for (BluetoothDevice d : bondedDevices) {
            if (d.getName().startsWith("Miura")) {
                device = d;
                break;
            }
        }

        if (device == null) {
            throw new RuntimeException("No device found with name: $deviceName");
        } else {
            ATSBluetoothAdapter.start(12345);

            // this could happen later
            ATSBluetoothAdapter.setBluetoothDevice(device);
        }
    }
}
