package com.mastercard.gateway.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.domain.ATSMessage;
import com.mastercard.gateway.ats.domain.RequestResultType;
import com.mastercard.gateway.ats.domain.ServiceRequest;
import com.mastercard.gateway.ats.domain.ServiceRequestType;
import com.mastercard.gateway.ats.domain.ServiceResponse;

import org.jetbrains.annotations.NotNull;

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

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        ServiceRequest request = new ServiceRequest();
        request.setRequestType(ServiceRequestType.AcquireDevice);
        request.setWorkstationID(preferences.getString("ATS_WORKSTATION_ID", ""));
        request.setRequestID("9786");

        final ATSClient atsClient = ((SampleApplication) getApplication()).getAtsClient();
        atsClient.addCallback(new ATSClient.Callback() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onMessageReceived(@org.jetbrains.annotations.Nullable ATSMessage message) {

                if (message instanceof ServiceResponse) {
                    ServiceResponse serviceResponse = (ServiceResponse) message;

                    if (serviceResponse.getOverallResult() == RequestResultType.Success) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("ATS_POP_ID", serviceResponse.getPopid());
                        editor.apply();

                        Toast.makeText(SelectActionActivity.this,"Device Acquired Successfully !", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(SelectActionActivity.this,"Device Acquired Failed !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(@NotNull Throwable throwable) {

            }
        });
        atsClient.sendMessage(request);
    }

}
