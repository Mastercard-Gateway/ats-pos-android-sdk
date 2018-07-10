package com.mastercard.gateway.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        FrameLayout back

        String type = getIntent().getStringExtra("Action");

        if (getIntent().getStringExtra("Result").equals("Success")) {
            status.setImageResource(R.drawable.ic_approved_with_background);
            statusText.setText(getString(R.string.status_successful, type));
        } else {
            status.setImageResource(R.drawable.ic_declined_with_background);
            statusText.setText(getString(R.string.status_failed, type));
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
