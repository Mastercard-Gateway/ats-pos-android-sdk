package com.mastercard.gateway.sample;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;

import com.mastercard.gateway.ats.ATSBluetoothAdapter;
import com.mastercard.gateway.ats.ATSBluetoothConfiguration;

public class SampleApplication extends Application {

    ATSBluetoothAdapter adapter;
    SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Attempt to spin up an instance of the ATSBluetoothAdapter
        startATSBluetoothAdapter();
    }

    /**
     * Checks to see if the current adapter instance is not null and running
     * @return if the current adapter instance is running
     */
    public boolean isAdapterRunning() {
        return adapter != null && adapter.isRunning();
    }

    /**
     * Checks to see if SharedPreferences contains necessary information to create an ATSBluetoothAdapter
     * if so then it spins up an instance based on the configuration
     */
    public void startATSBluetoothAdapter() {
        if (preferences.getBoolean("BLUETOOTH", false)) {

            String deviceName = preferences.getString("ATS_DEVICE_NAME", "");
            int adapterPort = preferences.getInt("ATS_DEVICE_PORT", 0);

            if (deviceName.isEmpty()) {
                return;
            }

            BluetoothDevice selectedDevice = getBluetoothDevice(deviceName);

            if (selectedDevice != null) {

                boolean roaming = preferences.getBoolean("BLUETOOTH_ROAMING", false);

                //Create either a Static or Roaming ATSBluetoothConfiguration
                ATSBluetoothConfiguration configuration;

                if (roaming) {
                    String atsIPAddress = preferences.getString("ATS_IP_ADDRESS", "");
                    configuration = new ATSBluetoothConfiguration.Roaming(atsIPAddress, adapterPort, selectedDevice);
                } else {
                    configuration = new ATSBluetoothConfiguration.Static(adapterPort, selectedDevice);
                }

                if (isAdapterRunning()) {
                    adapter.stop();
                    adapter = null;
                }

                //Start ATSBluetooth Adapter
                adapter = new ATSBluetoothAdapter();
                adapter.start(configuration);
            }
        }
    }

    //If ATSBluetoothAdapter is running, stop it and throw away the instance
    public void stopATSBluetoothAdapter() {
        if (isAdapterRunning()) {
            adapter.stop();
            adapter = null;
        }
    }

    @Nullable
    private BluetoothDevice getBluetoothDevice(String deviceName) {
        BluetoothDevice selectedDevice = null;

        for (BluetoothDevice supportedDevice : ATSBluetoothAdapter.getSupportedDevices()) {
            if (supportedDevice.getName().equals(deviceName)) {
                selectedDevice = supportedDevice;
                break;
            }
        }
        return selectedDevice;
    }
}

