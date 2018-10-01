package com.mastercard.gateway.sample;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ImageView status = findViewById(R.id.status);
        TextView statusText = findViewById(R.id.status_text);
        Button done = findViewById(R.id.done);
        FrameLayout background = findViewById(R.id.card_background);


        String type = getIntent().getStringExtra("Action");

        if (getIntent().getStringExtra("Result").equals("Success")) {
            status.setImageResource(R.drawable.ic_approved_with_background);
            statusText.setText(getString(R.string.status_successful, type));
            done.setTextColor(ResourcesCompat.getColor(getResources(), R.color.green, null));
            background.setBackgroundResource(R.drawable.gradient_success);
        } else {
            status.setImageResource(R.drawable.ic_declined_with_background);
            statusText.setText(getString(R.string.status_failed, type));
            done.setTextColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
            background.setBackgroundResource(R.drawable.gradient_fail);
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
