package com.mastercard.gateway.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


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
                startActivity(new Intent(MainActivity.this, AmountActivity.class));
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

            String atsIPAddress = preferences.getString("ATS_IP_ADDRESS", "");
            int atsPort = preferences.getInt("ATS_PORT", 0);
            String deviceName = preferences.getString("ATS_DEVICE_NAME", "");
            int adapterPort = preferences.getInt("ATS_DEVICE_PORT", 0);
            String workstationID = preferences.getString("ATS_WORKSTATION_ID", "");
            String popID = preferences.getString("ATS_POP_ID", "");

            ((TextView) findViewById(R.id.ats_server_ip)).setText(atsIPAddress);
            ((TextView) findViewById(R.id.ats_server_port)).setText(String.valueOf(atsPort));

            ((TextView) findViewById(R.id.ats_workstation_id)).setText(workstationID);
            ((TextView) findViewById(R.id.ats_pop_id)).setText(popID);


            TextView bluetoothName = findViewById(R.id.device_name);
            TextView bluetoothNameLabel = findViewById(R.id.device_name_label);
            TextView bluethoothPort = findViewById(R.id.bluetooth_port);
            TextView bluethoothPortLabel = findViewById(R.id.bluetooth_port_label);

            if (preferences.getBoolean("BLUETOOTH", false)) {
                bluetoothNameLabel.setVisibility(View.VISIBLE);
                bluetoothName.setVisibility(View.VISIBLE);
                bluethoothPortLabel.setVisibility(View.VISIBLE);
                bluethoothPort.setVisibility(View.VISIBLE);
                bluetoothName.setText(deviceName);
                bluethoothPort.setText(String.valueOf(adapterPort));
            } else {
                bluetoothNameLabel.setVisibility(View.GONE);
                bluetoothName.setVisibility(View.GONE);
                bluethoothPortLabel.setVisibility(View.GONE);
                bluethoothPort.setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.create_configuration).setVisibility(View.VISIBLE);
            findViewById(R.id.show_configuration).setVisibility(View.GONE);
        }
    }

}
