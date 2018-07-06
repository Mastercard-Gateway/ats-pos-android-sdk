package com.mastercard.gateway.sample;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.ATSDiagnostics;
import com.mastercard.gateway.ats.domain.ATSMessage;
import com.mastercard.gateway.ats.domain.CardRequestType;
import com.mastercard.gateway.ats.domain.CardServiceRequest;
import com.mastercard.gateway.ats.domain.CardServiceResponse;
import com.mastercard.gateway.ats.domain.DeviceResponse;
import com.mastercard.gateway.ats.domain.RequestResultType;
import com.mastercard.gateway.ats.domain.ServiceRequest;
import com.mastercard.gateway.ats.domain.ServiceRequestType;
import com.mastercard.gateway.ats.domain.ServiceResponse;
import com.mastercard.gateway.ats.domain.TotalAmountType;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ATSClient ats;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ((TextView) findViewById(R.id.hello)).setText(Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));

//        ATSBluetoothAdapter.setBluetoothDevice("Miura 183");

        ats = new ATSClient("10.157.193.8", 20002);
//        ats = new ATSClient("10.157.196.212", 20002);
        ats.addCallback(new ATSCallback());
        ats.connect();
    }

    @Override
    protected void onDestroy() {
        ats.close();
        super.onDestroy();
    }

    void acquireDevice() {

//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ServiceRequest RequestType=\"AcquireDevice\" ApplicationSender=\"ATSClient\" WorkstationID=\"12342\" RequestID=\"9\"/>";

        ServiceRequest request = new ServiceRequest();
        request.setRequestType(ServiceRequestType.AcquireDevice);
        request.setWorkstationID("43214321");
        request.setRequestID("9");
        request.setApplicationSender("ATS_Testing");

        ats.sendMessage(request);
    }

    void startTransactionWithReader(String popid) {


        CardServiceRequest cardServiceRequest = new CardServiceRequest();

        cardServiceRequest.setRequestType(CardRequestType.CardPayment);
        cardServiceRequest.setWorkstationID("43214321");
        cardServiceRequest.setRequestID("9");

        CardServiceRequest.POSdata posData = new CardServiceRequest.POSdata();
        posData.setPosTimeStamp(new Date());
        posData.setTransactionNumber(2);
        cardServiceRequest.setPoSdata(posData);

        cardServiceRequest.setPopid(popid);

        TotalAmountType totalAmountType = new TotalAmountType();
        totalAmountType.value = new BigDecimal("10.00");
        totalAmountType.setPaymentAmount(new BigDecimal("10.00"));
        cardServiceRequest.setTotalAmount(totalAmountType);

        ats.sendMessage(cardServiceRequest);

//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<CardServiceRequest RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"12342\" RequestID=\"2\" POPID=\"" + popid + "\">\n" +
//                "  <POSdata>\n" +
//                "    <POSTimeStamp>2010-05-19T15:11:31.765625+01:00</POSTimeStamp>\n" +
//                "    <TransactionNumber>2</TransactionNumber>\n" +
//                "  </POSdata>\n" +
//                "  <TotalAmount PaymentAmount=\"10.00\">10.00</TotalAmount>\n" +
//                "</CardServiceRequest>";
//
//        ats.sendMessage(xml);
    }

    void startTransactionWithBluetoothCardReader() {

//        ATSBluetoothAdapter.setBluetoothDevice(bluetoothDeviceName);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<CardServiceRequest RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"43214321\"  RequestID=\"2\">\n" +
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
        public void onMessageReceived(@Nullable ATSMessage message) {
            Log.i("MainActivity", "Received message:\n" + message);


            if (message instanceof ServiceResponse) {
                ServiceResponse serviceResponse = (ServiceResponse) message;

                if (serviceResponse.getRequestType() == ServiceRequestType.AcquireDevice && serviceResponse.getOverallResult() == RequestResultType.Success) {
                    String popId = serviceResponse.getPopid();
                    startTransactionWithReader(popId);

//                    startTransactionWithBluetoothCardReader();
                }
            } else if (message instanceof CardServiceResponse) {
                CardServiceResponse cardServiceResponse = (CardServiceResponse) message;

                ats.close();
            } else if (message instanceof DeviceResponse) {
                DeviceResponse deviceResponse = (DeviceResponse) message;
            }
        }

        @Override
        public void onError(@NotNull Throwable throwable) {
            Log.i("MainActivity", "Error communicating to ATS", throwable);
        }

        @Override
        public void onDisconnected() {
            Log.i("MainActivity", "ATS disconnected");

            Log.v("MainActivity", "Total log:\n" + ATSDiagnostics.getLog());
            ATSDiagnostics.clearLog();
        }
    }
}
