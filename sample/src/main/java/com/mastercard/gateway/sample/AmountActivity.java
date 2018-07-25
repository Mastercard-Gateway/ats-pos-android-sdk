package com.mastercard.gateway.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.mastercard.gateway.ats.ATSClient;
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


    Action action;

    ATSClient atsClient;

    ActivityAmountBinding binding;

    SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_amount);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        atsClient = ((SampleApplication) getApplication()).getAtsClient();
        atsClient.addCallback(this);

        if (!atsClient.isConnected()) {
            atsClient.connect();
        }

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountActivity.this.finish();
            }
        });
        Button actionButton = findViewById(R.id.button_action);


        action = Action.Payment;
        binding.toolbar.setTitle("Create a payment");
        actionButton.setText("Pay");


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createATSMessage();
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
    protected void onStart() {
        super.onStart();
        hideProgress();
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

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

//            atsClient.close();

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

    //Create a CardServiceRequest for the amount entered
    private void createATSMessage() {

        String amount = binding.amountEditText.getText().toString();


        String workstationID = preferences.getString("ATS_WORKSTATION_ID", "");


        CardServiceRequest request = new CardServiceRequest();
        request.setRequestType(CardRequestType.CardPayment);

        request.setWorkstationID(workstationID);
        request.setRequestID("19");
        request.setApplicationSender("ATSClient");


        CardServiceRequest.POSdata posData = new CardServiceRequest.POSdata();
        posData.setPosTimeStamp(new Date());
        posData.setTransactionNumber(19);


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

        atsClient.sendMessage(request);

        showProgress();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        atsClient.close();
    }

    private void showProgress(){
        findViewById(R.id.transaction_in_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.collect_amount).setVisibility(View.GONE);
    }

    private void hideProgress() {
        findViewById(R.id.transaction_in_progress).setVisibility(View.GONE);
        findViewById(R.id.collect_amount).setVisibility(View.VISIBLE);
    }
    enum Action {
        Authorization,
        Payment
    }
}
