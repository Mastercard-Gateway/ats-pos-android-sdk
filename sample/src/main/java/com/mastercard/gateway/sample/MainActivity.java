package com.mastercard.gateway.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.ATSDiagnostics;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ATSClient ats;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ATSDiagnostics.setLogLevel(Log.VERBOSE);
        ATSDiagnostics.startLogCapture();

//        ats = new ATSClient("10.157.193.8", 20002);
        ats = new ATSClient("10.157.196.212", 20002);
        ats.addCallback(new ATSCallback());
        ats.connect();
    }

    @Override
    protected void onDestroy() {
        ats.close();
        super.onDestroy();
    }

    void acquireDevice() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ServiceRequest RequestType=\"AcquireDevice\" ApplicationSender=\"ATSClient\" WorkstationID=\"12342\" RequestID=\"9\"/>";

        ats.sendMessage(xml);
    }

    void startTransactionWithReader(String popid) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CardServiceRequest RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"12342\" RequestID=\"2\" POPID=\"" + popid + "\">\n" +
                "  <POSdata>\n" +
                "    <POSTimeStamp>2010-05-19T15:11:31.765625+01:00</POSTimeStamp>\n" +
                "    <TransactionNumber>2</TransactionNumber>\n" +
                "  </POSdata>\n" +
                "  <TotalAmount PaymentAmount=\"10.00\">10.00</TotalAmount>\n" +
                "</CardServiceRequest>";

        ats.sendMessage(xml);
    }


    class ATSCallback implements ATSClient.Callback {

        @Override
        public void onConnected() {
            Log.i("MainActivity", "ATS connected");

            acquireDevice();
        }

        @Override
        public void onMessageReceived(@NotNull String message) {
            Log.i("MainActivity", "Received message:\n" + message);

            if (message.contains("ServiceResponse") && message.contains("AcquireDevice") && message.contains("OverallResult=\"Success\"")) {
                Pattern pattern = Pattern.compile("POPID=\"([^\"]+)\"");
                Matcher m = pattern.matcher(message);
                if (m.find()) {
                    String popId = m.group(1);
                    startTransactionWithReader(popId);
                }
            } else if (message.contains("CardServiceResponse")) {
                ats.close();
            }
        }

        @Override
        public void onError(@NotNull Throwable throwable) {
            Log.i("MainActivity", "Error communicating to ATS", throwable);
        }

        @Override
        public void onDisconnected() {
            Log.i("MainActivity", "ATS disconnected");

            Log.v("MainActivity", "Total log:\n" + ATSDiagnostics.stopLogCapture());
            ATSDiagnostics.clearLog();
        }
    }
}
