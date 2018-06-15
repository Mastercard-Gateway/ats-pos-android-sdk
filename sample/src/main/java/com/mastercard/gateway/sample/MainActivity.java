package com.mastercard.gateway.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mastercard.gateway.ats.ATSClient;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    ATSClient ats;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ats = new ATSClient("10.157.193.8", 20002);
        ats.addCallback(new ATSCallback());
        ats.connect();
    }

    @Override
    protected void onDestroy() {
        ats.close();
        super.onDestroy();
    }


    class ATSCallback implements ATSClient.Callback {

        @Override
        public void onConnected() {
            Log.i("MainActivity", "ATS connected");

            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<CardServiceRequest RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"12341234\" RequestID=\"2\">\n" +
                    "  <POSdata>\n" +
                    "    <POSTimeStamp>2010-05-19T15:11:31.765625+01:00</POSTimeStamp>\n" +
                    "    <TransactionNumber>2</TransactionNumber>\n" +
                    "  </POSdata>\n" +
                    "  <TotalAmount PaymentAmount=\"10.00\">10.00</TotalAmount>\n" +
                    "</CardServiceRequest>";

            Log.i(MainActivity.class.getSimpleName(), "Sending message:\n" + xml);

            ats.sendMessage(xml);
        }

        @Override
        public void onDisconnected() {
            Log.i("MainActivity", "ATS disconnected");
        }

        @Override
        public void onMessageReceived(@NotNull String message) {
            Log.i("MainActivity", "Received message:\n" + message);

            ats.close();
        }

        @Override
        public void onError(@NotNull Throwable throwable) {
            Log.i("MainActivity", "Error communicating to ATS", throwable);
        }
    }
}
