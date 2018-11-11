package com.amiskovic.test.btandroidlib;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.util.Set;

public class BluetoothProxy extends BroadcastReceiver {

    private Activity activity;
    private static BluetoothProxy instance;

    private BluetoothAdapter BA;

    private Set<BluetoothDevice> pairedDevices;
    public String[] deviceList;

    private BluetoothDevice currentDevice;


    public int value = 0;

    public int getValue() {
        return value;
    }


    public BluetoothProxy() {
        this.instance = this;
        BA = BluetoothAdapter.getDefaultAdapter();


    }

    public static BluetoothProxy instance() {
        if (instance == null) {
            instance = new BluetoothProxy();
        }
        return instance;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void showMessage(String message) {
        Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show();
    }

    public void on() {
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.activity.registerReceiver(this, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
            this.activity.startActivityForResult(turnOn, 1);


            //Toast.makeText(this, "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.activity, "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off() {
        if (BA.isEnabled())
            BA.disable();
        else
            Toast.makeText(this.activity, "Already Off", Toast.LENGTH_LONG).show();
    }


    public void visible() {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        this.activity.startActivity(getVisible);
    }


    public String[] list() {
        pairedDevices = BA.getBondedDevices();

        deviceList = new String[pairedDevices.size()];

        int i = 0;
        for (BluetoothDevice bt : pairedDevices) {
            deviceList[i] = bt.getName();
            i++;
        }
        Toast.makeText(this.activity, "Showing Paired Devices", Toast.LENGTH_SHORT).show();

        return deviceList;
    }

    public void connect(String deviceName) {
        Toast.makeText(this.activity, "Trying to connect to " + deviceName, Toast.LENGTH_SHORT).show();
        currentDevice = null;

        if (pairedDevices != null) {

            for (BluetoothDevice d : pairedDevices) {
                if (d.getName().equals(deviceName)) {
                    currentDevice = d;
                    break;
                }
            }

            if (currentDevice != null) {
                new Thread(new BluetoothThread(currentDevice, activity)).start();
            } else {
                Toast.makeText(this.activity, "No Device Paired with this name " + deviceName, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this.activity, "No Device Paired", Toast.LENGTH_SHORT).show();
        }


    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == 1) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this.activity, "Turned on", Toast.LENGTH_LONG).show();
//                // The user picked a contact.
//                // The Intent's data Uri identifies which contact was selected.
//
//                // Do something with the contact here (bigger example below)
//            }
//        }
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                switch (state) {
                    case BluetoothAdapter.STATE_ON:
                        Toast.makeText(this.activity, "Turned on", Toast.LENGTH_LONG).show();
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        Toast.makeText(this.activity, "Turned off", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }

}
