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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.mastercard.gateway.ats.ATSBluetoothAdapter;
import com.mastercard.gateway.sample.databinding.ActivityConfigurationBinding;

import java.util.ArrayList;
import java.util.List;

public class CreateConfigurationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<BluetoothDevice> bluetoothDevices = ATSBluetoothAdapter.getSupportedDevices();
    private ActivityConfigurationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_configuration);
        binding.toolbar1.setNavigationIcon(R.drawable.ic_close_grey_24dp);
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
                int visible = isChecked ? View.VISIBLE : View.GONE;
                binding.deviceSpinner.setVisibility(visible);
                binding.adapterPortLabel.setVisibility(visible);
                binding.roamingSwitch.setVisibility(visible);
            }
        });

        initSpinner();
        populateScreen();

    }

    private void initSpinner() {
        ArrayList<String> list = new ArrayList<>();
        for (BluetoothDevice device : bluetoothDevices) {
            list.add(device.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, list);
        binding.deviceSpinner.setAdapter(adapter);
        binding.deviceSpinner.setOnItemSelectedListener(this);
    }

    private void save() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("ATS_IP_ADDRESS", binding.atsIpAddress.getText().toString());
        String atsServerPort = binding.atsServerPort.getText().toString();
        editor.putInt("ATS_PORT", atsServerPort.trim().isEmpty() ? 0 : Integer.parseInt(atsServerPort));
        editor.putString("ATS_WORKSTATION_ID", binding.workstationId.getText().toString());
        editor.putString("ATS_POP_ID", binding.popId.getText().toString());

        boolean btChecked = binding.bluetoothSwitch.isChecked();
        editor.putBoolean("BLUETOOTH", btChecked);
        if (btChecked) {
            if (binding.deviceSpinner.getSelectedItem() != null) {
                editor.putInt("ATS_DEVICE_PORT", Integer.valueOf(binding.adapterPort.getText().toString()));
            }
            editor.putBoolean("BLUETOOTH_ROAMING", binding.roamingSwitch.isChecked());
            editor.apply();

            ((SampleApplication) getApplication()).startATSBluetoothAdapter();
        } else {
            editor.apply();
            ((SampleApplication) getApplication()).stopATSBluetoothAdapter();
        }

    }

    private void populateScreen() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.contains("ATS_IP_ADDRESS")) {

            binding.atsIpAddress.setText(preferences.getString("ATS_IP_ADDRESS", ""));
            binding.atsServerPort.setText(String.valueOf(preferences.getInt("ATS_PORT", 0)));
//            binding.deviceSpinner = preferences.getString("ATS_DEVICE_NAME", "");
            binding.workstationId.setText(preferences.getString("ATS_WORKSTATION_ID", ""));
            binding.popId.setText(preferences.getString("ATS_POP_ID", ""));
            binding.adapterPort.setText(String.valueOf(preferences.getInt("ATS_DEVICE_PORT", 0)));

            boolean bluetoothEnabled = preferences.getBoolean("BLUETOOTH", false);
            binding.bluetoothSwitch.setChecked(bluetoothEnabled);

            int visible = bluetoothEnabled ? View.VISIBLE : View.GONE;
            binding.deviceSpinner.setVisibility(visible);
            binding.adapterPortLabel.setVisibility(visible);
            binding.roamingSwitch.setVisibility(visible);

            binding.roamingSwitch.setChecked(preferences.getBoolean("BLUETOOTH_ROAMING", false));
        } else {
            binding.deviceSpinner.setVisibility(View.GONE);
            binding.adapterPortLabel.setVisibility(View.GONE);
            binding.roamingSwitch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ATS_DEVICE_NAME", parent.getItemAtPosition(position).toString());
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
