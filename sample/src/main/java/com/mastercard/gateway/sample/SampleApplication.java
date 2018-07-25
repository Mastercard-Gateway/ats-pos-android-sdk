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

    @Override
    public void onCreate() {
        super.onCreate();

        // init ATS diagnostics
        ATSDiagnostics.setLogLevel(Log.VERBOSE);
    }
}

