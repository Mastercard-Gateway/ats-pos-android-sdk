package com.mastercard.gateway.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.ATSDiagnostics;
import com.mastercard.gateway.ats.domain.ATSMessage;
import com.mastercard.gateway.ats.domain.CardRequestType;
import com.mastercard.gateway.ats.domain.CardServiceRequest;
import com.mastercard.gateway.ats.domain.CardServiceResponse;
import com.mastercard.gateway.ats.domain.DeviceRequest;
import com.mastercard.gateway.ats.domain.DeviceResponse;
import com.mastercard.gateway.ats.domain.RequestResultType;
import com.mastercard.gateway.ats.domain.TotalAmountType;
import com.mastercard.gateway.sample.databinding.ActivityAmountBinding;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AmountActivity extends Activity implements ATSClient.Callback {

    ATSClient atsClient = new ATSClient();
    Action action = Action.Payment;

    ActivityAmountBinding binding;
    SharedPreferences preferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_amount);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        showProgress(false);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountActivity.this.finish();
            }
        });

        binding.toolbar.setTitle("Create a payment");

        binding.buttonAction.setText("Pay");
        binding.buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectATSClient();
            }
        });

        String popID = preferences.getString("ATS_POP_ID", "");
        binding.popIDEditText.setText(popID);

        binding.switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.reference.setVisibility(View.VISIBLE);
                    binding.popId.setVisibility(View.GONE);
                } else {
                    binding.reference.setVisibility(View.GONE);
                    binding.popId.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        atsClient.addCallback(this);

        ATSDiagnostics.startLogCapture();
    }

    @Override
    protected void onPause() {
        // if this screen backgrounds, close the client
        // which will kill any transaction in progress
        atsClient.close();

        atsClient.removeCallback(this);

        ATSDiagnostics.stopLogCapture();
        ATSDiagnostics.clearLog();

        showProgress(false);

        super.onPause();
    }

    @Override
    public void onConnected() {
        CardServiceRequest request = createPaymentRequest();

        atsClient.sendMessage(request);

        showProgress(true);
    }

    @Override
    public void onDisconnected() {
        keepScreenOn(false);

        String log = ATSDiagnostics.getLog();
        Log.d(AmountActivity.class.getSimpleName(), "ATS Log (" + log.length() + " chars):\n" + log);
    }

    @Override
    public void onMessageReceived(@Nullable ATSMessage message) {
        if (message instanceof CardServiceResponse) {
            CardServiceResponse response = (CardServiceResponse) message;

            Intent intent = new Intent(AmountActivity.this, ResultActivity.class);
            intent.putExtra("Action", action.equals(Action.Payment) ? "Payment" : "Authorization");

            if (response.overallResult.equals(RequestResultType.Success)) {
                intent.putExtra("Result", "Success");
            } else {
                intent.putExtra("Result", "Error");
            }

            //atsClient is supposed to be closed after every transaction due to its short timeout period.
            atsClient.close();

            startActivity(intent);

        } else if (message instanceof DeviceRequest) {
            DeviceRequest request = (DeviceRequest) message;

            List<DeviceRequest.Output> output = request.getOutput();
            if (output != null && !output.isEmpty()) {
                List<DeviceRequest.Output.TextLine> textLines = output.get(0).getTextLine();

                if (textLines != null && !textLines.isEmpty()) {
                    String text = textLines.get(0).getValue();
                    binding.textMessage.setText(text);
                }
            }

            DeviceResponse response = DeviceResponse.createSuccessfulResponse(request);

            atsClient.sendMessage(response);
        }
    }

    @Override
    public void onError(@NotNull Throwable throwable) {
        Intent intent = new Intent(AmountActivity.this, ResultActivity.class);
        intent.putExtra("Action", action.equals(Action.Payment) ? "Payment" : "Authorization");
        intent.putExtra("Result", "Error");
        startActivity(intent);
    }

    private void connectATSClient() {
        // keep the screen on while we process
        keepScreenOn(true);

        String atsIPAddress = preferences.getString("ATS_IP_ADDRESS", "");
        int atsPort = preferences.getInt("ATS_PORT", 0);
        atsClient.connect(atsIPAddress, atsPort);
    }

    //Create a CardServiceRequest for the amount entered
    private CardServiceRequest createPaymentRequest() {
        String amount = binding.amountEditText.getText().toString();

        String workstationID = preferences.getString("ATS_WORKSTATION_ID", "");

        CardServiceRequest request = new CardServiceRequest();
        request.setRequestType(CardRequestType.CardPayment);

        request.setWorkstationID(workstationID);
        request.setRequestID("1");
        request.setApplicationSender("ATSClient");

        CardServiceRequest.POSdata posData = new CardServiceRequest.POSdata();
        posData.setPosTimeStamp(new Date());
        posData.setTransactionNumber(1);

        if (binding.switchMode.isChecked()) {
            posData.setReference(binding.referenceEditText.getText().toString());
        } else {
            request.setPopid(binding.popIDEditText.getText().toString());
        }

        request.setPoSdata(posData);

        TotalAmountType totalAmountType = new TotalAmountType();
        totalAmountType.value = new BigDecimal(amount);
        totalAmountType.setPaymentAmount(new BigDecimal(amount));
        request.setTotalAmount(totalAmountType);

        return request;
    }

    private void showProgress(boolean show) {
        findViewById(R.id.transaction_in_progress).setVisibility(show ? View.VISIBLE : View.GONE);
        findViewById(R.id.collect_amount).setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void keepScreenOn(boolean keepOn) {
        if (keepOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    enum Action {
        Authorization,
        Payment
    }
}
