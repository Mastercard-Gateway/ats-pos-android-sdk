package com.mastercard.gateway.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.domain.ServiceRequest;
import com.mastercard.gateway.ats.domain.ServiceRequestType;

public class SelectActionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_close_grey_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectActionActivity.this.finish();
            }
        });

        findViewById(R.id.button_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle options = new Bundle();
                options.putString("Action", "Payment");
                startActivity(new Intent(SelectActionActivity.this, AmountActivity.class), options);
            }
        });

        findViewById(R.id.button_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle options = new Bundle();
                options.putString("Action", "Authorization");
                startActivity(new Intent(SelectActionActivity.this, AmountActivity.class), options);
            }
        });

        findViewById(R.id.button_acquire_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acquireDevice();
            }
        });
    }

    void acquireDevice() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        ServiceRequest request = new ServiceRequest();
        request.setRequestType(ServiceRequestType.AcquireDevice);
        request.setWorkstationID(preferences.getString("ATS_WORKSTATION_ID", ""));
        request.setRequestID("9786");

        ATSClient atsClient = ((SampleApplication) getApplication()).getAtsClient();
        atsClient.sendMessage(request);
    }

}
