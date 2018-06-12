package com.mastercard.gateway.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.CardServiceRequest;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    ATSClient ats = new ATSClient("10.157.193.8", 20002);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ats.addCallback(new ATSClient.Callback() {
            @Override
            public void connected() {
                Log.i("MainActivity", "Socket connected");

                CardServiceRequest request = new CardServiceRequest(CardServiceRequest.RequestType.CardPayment,"asdasd","asdasd","1.00");
                String xml = request.toString();

                ats.sendMessage(xml);
            }

            @Override
            public void disconnected() {

            }

            @Override
            public void messageReceived(@NotNull String message) {
                Log.i("MainActivity", "Received message:\n" + message);

                ats.close();
            }

            @Override
            public void error(@NotNull Throwable throwable) {
                Log.i("MainActivity", "Error communicating to ATS", throwable);
            }
        });

        ats.connect();
    }

    @Override
    protected void onDestroy() {
        ats.close();
        super.onDestroy();
    }
}
