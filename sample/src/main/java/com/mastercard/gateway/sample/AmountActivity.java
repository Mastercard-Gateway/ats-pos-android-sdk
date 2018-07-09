package com.mastercard.gateway.sample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mastercard.gateway.ats.ATSClient;
import com.mastercard.gateway.ats.domain.ATSMessage;
import com.mastercard.gateway.ats.domain.CardRequestType;
import com.mastercard.gateway.ats.domain.CardServiceRequest;
import com.mastercard.gateway.ats.domain.CardServiceResponse;
import com.mastercard.gateway.ats.domain.RequestResultType;
import com.mastercard.gateway.ats.domain.TotalAmountType;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public class AmountActivity extends Activity implements ATSClient.Callback {

    EditText amountEditText;

    Action action;

    ATSClient atsClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        atsClient = ((SampleApplication) getApplication()).getAtsClient();
        amountEditText = findViewById(R.id.amountEditText);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button actionButton = findViewById(R.id.button_action);

        if (getIntent().getStringExtra("Action").equals("Payment")) {
            action = Action.Payment;
            toolbar.setTitle("Create a payment");
            actionButton.setText("Pay");
        } else {
            action = Action.Authorization;
            toolbar.setTitle("Create an authorization");
            actionButton.setText("Auth");
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = amountEditText.getText().toString();

                createATSMessage(amount);
            }
        });
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

            if (response.overallResult.equals(RequestResultType.Success)) {
                //TODO navigate to success screen
            } else {
                //TODO navigate to error screen
            }

            ((SampleApplication) getApplication()).getAtsClient().close();
        }
    }

    @Override
    public void onError(@NotNull Throwable throwable) {
        //TODO Navigate to error screen
    }

    private void createATSMessage(String amount) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String workstationID = preferences.getString("ATS_WORKSTATION_ID", "");
        String popID = preferences.getString("ATS_POP_ID", "");

        CardServiceRequest request = new CardServiceRequest();
        request.setRequestType(action == Action.Payment ? CardRequestType.CardPayment : CardRequestType.CardPreAuthorisation);
        request.setWorkstationID(workstationID);
        request.setRequestID("2");
        request.setPopid(popID);

        CardServiceRequest.POSdata posData = new CardServiceRequest.POSdata();
        posData.setPosTimeStamp(new Date());
        posData.setTransactionNumber(2);
        request.setPoSdata(posData);

        TotalAmountType totalAmountType = new TotalAmountType();
        totalAmountType.value = new BigDecimal(amount);
        totalAmountType.setPaymentAmount(new BigDecimal(amount));
        request.setTotalAmount(totalAmountType);

        atsClient.sendMessage(request);

        findViewById(R.id.transaction_in_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.collect_amount).setVisibility(View.GONE);
    }


    enum Action {
        Authorization,
        Payment
    }
}
