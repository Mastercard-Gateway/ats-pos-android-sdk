package com.mastercard.gateway.sample;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.mastercard.gateway.ats.ATSBluetoothAdapter;
import com.mastercard.gateway.sample.databinding.ActivityConfigurationBinding;

import java.util.ArrayList;

public class CreateConfigurationActivity extends AppCompatActivity {

    private ActivityConfigurationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_configuration);
        binding.toolbar1.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        binding.toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });

        binding.bluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.deviceSpinner.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        initSpinner();
        populateScreen();

    }

    private void initSpinner() {
        ArrayList<String> list = new ArrayList<>();
        for (BluetoothDevice device : ATSBluetoothAdapter.getSupportedDevices()) {
            list.add(device.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        binding.deviceSpinner.setAdapter(adapter);
    }

    private void save() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("ATS_IP_ADDRESS", binding.atsIpAddress.getText().toString());
        editor.putInt("ATS_PORT", Integer.parseInt(binding.atsServerPort.getText().toString()));
        editor.putString("ATS_WORKSTATION_ID", binding.workstationId.getText().toString());
        editor.putString("ATS_POP_ID", binding.popId.getText().toString());
        editor.putBoolean("BLUETOOTH", binding.bluetoothSwitch.isChecked());
        if (binding.deviceSpinner.getSelectedItem() != null) {
            editor.putString("ATS_DEVICE_NAME", binding.deviceSpinner.getSelectedItem().toString());
            editor.putInt("ATS_DEVICE_PORT", 0);
        }
        editor.apply();
    }

    private void populateScreen() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.contains("ATS_IP_ADDRESS")) {

            binding.atsIpAddress.setText(preferences.getString("ATS_IP_ADDRESS", ""));
            binding.atsServerPort.setText(String.valueOf(preferences.getInt("ATS_PORT", 0)));
//            binding.deviceSpinner = preferences.getString("ATS_DEVICE_NAME", "");
            binding.workstationId.setText(preferences.getString("ATS_WORKSTATION_ID", ""));
            binding.popId.setText(preferences.getString("ATS_POP_ID", ""));
        }
    }
}
