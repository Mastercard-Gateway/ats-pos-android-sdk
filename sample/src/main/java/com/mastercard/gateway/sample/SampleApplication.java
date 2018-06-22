package com.mastercard.gateway.sample;

import android.app.Application;

import com.mastercard.gateway.ats.ATSBluetoothAdapter;

public class SampleApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        ATSBluetoothAdapter.init(46545);
    }
}
