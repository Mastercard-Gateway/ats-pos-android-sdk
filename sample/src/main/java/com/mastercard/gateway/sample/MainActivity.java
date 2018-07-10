package com.mastercard.gateway.sample;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mastercard.gateway.ats.ATSBluetoothAdapter;

public class MainActivity extends AppCompatActivity {

    String atsIPAddress;
    int atsPort;
    String deviceName;
    int adapterPort;
    String workstationID;
    String popID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.settings) {
                    startActivity(new Intent(MainActivity.this, CreateConfigurationActivity.class));
                    return true;
                }

                return false;
            }
        });

        findViewById(R.id.button_create_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateConfigurationActivity.class));
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SampleApplication) getApplication()).initATSClient(atsIPAddress, atsPort);

                if (deviceName != null && !deviceName.isEmpty()) {

                    BluetoothDevice selectedDevice = null;

                    for (BluetoothDevice supportedDevice : ATSBluetoothAdapter.getSupportedDevices()) {
                        if (supportedDevice.getName().equals(deviceName)) {
                            selectedDevice = supportedDevice;
                            break;
                        }
                    }

                    ATSBluetoothAdapter.start(adapterPort);
                    ATSBluetoothAdapter.setBluetoothDevice(selectedDevice);
                }

                startActivity(new Intent(MainActivity.this, SelectActionActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.contains("ATS_IP_ADDRESS")) {
            //Show config
            findViewById(R.id.create_configuration).setVisibility(View.GONE);
            findViewById(R.id.show_configuration).setVisibility(View.VISIBLE);

            atsIPAddress = preferences.getString("ATS_IP_ADDRESS", "");
            atsPort = preferences.getInt("ATS_PORT", 0);
            deviceName = preferences.getString("ATS_DEVICE_NAME", "");
            adapterPort = preferences.getInt("ATS_DEVICE_PORT", 0);
            workstationID = preferences.getString("ATS_WORKSTATION_ID", "");
            popID = preferences.getString("ATS_POP_ID", "");

            ((TextView) findViewById(R.id.ats_server_ip)).setText(atsIPAddress);
            ((TextView) findViewById(R.id.ats_server_port)).setText(String.valueOf(atsPort));
            ((TextView) findViewById(R.id.device_name)).setText(deviceName);
            ((TextView) findViewById(R.id.bluetooth_port)).setText(String.valueOf(adapterPort));
            ((TextView) findViewById(R.id.ats_workstation_id)).setText(workstationID);
            ((TextView) findViewById(R.id.ats_pop_id)).setText(popID);
        } else {
            findViewById(R.id.create_configuration).setVisibility(View.VISIBLE);
            findViewById(R.id.show_configuration).setVisibility(View.GONE);
        }
    }

}


//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        ((TextView) findViewById(R.id.hello)).setText(Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress()));
//
////        ATSBluetoothAdapter.setBluetoothDevice("Miura 183");
//
//        // start capturing ATS logs
//        ATSDiagnostics.startLogCapture();
//
//        ats = new ATSClient("10.157.193.8", 20002);
////        ats = new ATSClient("10.157.196.212", 20002);
//        ats.addCallback(new ATSCallback());
//        ats.connect();

//
//    @Override
//    protected void onDestroy() {
//        ats.close();
//        super.onDestroy();
//    }


//    void acquireDevice() {
//
////        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ServiceRequest RequestType=\"AcquireDevice\" ApplicationSender=\"ATSClient\" WorkstationID=\"12342\" RequestID=\"9\"/>";
//
//        ServiceRequest request = new ServiceRequest();
//        request.setRequestType(ServiceRequestType.AcquireDevice);
//        request.setWorkstationID("43214321");
//        request.setRequestID("9");
//        request.setApplicationSender("ATS_Testing");
//
//        ats.sendMessage(request);
//    }
//
//    void startTransactionWithReader(String popid) {
//
//
//        CardServiceRequest cardServiceRequest = new CardServiceRequest();
//
//        cardServiceRequest.setRequestType(CardRequestType.CardPayment);
//        cardServiceRequest.setWorkstationID("43214321");
//        cardServiceRequest.setRequestID("9");
//
//        CardServiceRequest.POSdata posData = new CardServiceRequest.POSdata();
//        posData.setPosTimeStamp(new Date());
//        posData.setTransactionNumber(2);
//        cardServiceRequest.setPoSdata(posData);
//
//        cardServiceRequest.setPopid(popid);
//
//        TotalAmountType totalAmountType = new TotalAmountType();
//        totalAmountType.value = new BigDecimal("10.00");
//        totalAmountType.setPaymentAmount(new BigDecimal("10.00"));
//        cardServiceRequest.setTotalAmount(totalAmountType);
//
//        ats.sendMessage(cardServiceRequest);
//
////        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
////                "<CardServiceRequest RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"12342\" RequestID=\"2\" POPID=\"" + popid + "\">\n" +
////                "  <POSdata>\n" +
////                "    <POSTimeStamp>2010-05-19T15:11:31.765625+01:00</POSTimeStamp>\n" +
////                "    <TransactionNumber>2</TransactionNumber>\n" +
////                "  </POSdata>\n" +
////                "  <TotalAmount PaymentAmount=\"10.00\">10.00</TotalAmount>\n" +
////                "</CardServiceRequest>";
////
////        ats.sendMessage(xml);
//    }
//
//    void startTransactionWithBluetoothCardReader() {
//
////        ATSBluetoothAdapter.setBluetoothDevice(bluetoothDeviceName);
//
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<CardServiceRequest RequestType=\"CardPayment\" ApplicationSender=\"ATSClient\" WorkstationID=\"43214321\"  RequestID=\"2\">\n" +
//                "  <POSdata>\n" +
//                "    <POSTimeStamp>2010-05-19T15:11:31.765625+01:00</POSTimeStamp>\n" +
//                "    <TransactionNumber>2</TransactionNumber>\n" +
//                "  </POSdata>\n" +
//                "  <TotalAmount PaymentAmount=\"10.00\">10.00</TotalAmount>\n" +
//                "</CardServiceRequest>";
//
//        ats.sendMessage(xml);
//    }
//
//
//    class ATSCallback implements ATSClient.Callback {
//
//        @Override
//        public void onConnected() {
//            Log.i("MainActivity", "ATS connected");
//
//            acquireDevice();
//        }
//
//
//        @Override
//        public void onMessageReceived(@Nullable ATSMessage message) {
//            Log.i("MainActivity", "Received message:\n" + message);
//
//
//            if (message instanceof ServiceResponse) {
//                ServiceResponse serviceResponse = (ServiceResponse) message;
//
//                if (serviceResponse.getRequestType() == ServiceRequestType.AcquireDevice && serviceResponse.getOverallResult() == RequestResultType.Success) {
//                    String popId = serviceResponse.getPopid();
//                    startTransactionWithReader(popId);
//
////                    startTransactionWithBluetoothCardReader();
//                }
//            } else if (message instanceof CardServiceResponse) {
//                CardServiceResponse cardServiceResponse = (CardServiceResponse) message;
//
//                ats.close();
//            } else if (message instanceof DeviceResponse) {
//                DeviceResponse deviceResponse = (DeviceResponse) message;
//            }
//        }
//
//        @Override
//        public void onError(@NotNull Throwable throwable) {
//            Log.i("MainActivity", "Error communicating to ATS", throwable);
//        }
//
//        @Override
//        public void onDisconnected() {
//            Log.i("MainActivity", "ATS disconnected");
//
//            Log.v("MainActivity", "Total log:\n" + ATSDiagnostics.getLog());
//            ATSDiagnostics.stopLogCapture();
//            ATSDiagnostics.clearLog();
//        }
//    }

