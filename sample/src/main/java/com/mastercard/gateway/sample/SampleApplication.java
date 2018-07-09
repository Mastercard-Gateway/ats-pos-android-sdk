package com.mastercard.gateway.sample;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import com.mastercard.gateway.ats.ATSBluetoothAdapter;
import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.ATSDiagnostics;

import java.util.NoSuchElementException;
import java.util.Set;

public class SampleApplication extends Application {

    private ATSClient atsClient;

    @Override
    public void onCreate() {
        super.onCreate();

        // init ATS diagnostics
        ATSDiagnostics.setLogLevel(Log.VERBOSE);

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

    void initATSClient(String ipAddress, int atsPort) {
        atsClient = new ATSClient(ipAddress, atsPort);
        atsClient.connect();
    }

    ATSClient getAtsClient() {
        return atsClient;
    }
}
