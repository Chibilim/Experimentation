package com.amiskovic.test.btandroidlib;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.widget.Toast;

import java.util.Set;

public class ToastExample {

    private Activity activity;
    private static ToastExample instance;

    private BluetoothAdapter BA;

    private Set<BluetoothDevice> pairedDevices;
    public String[] deviceList;

    public int value = 0;

    public int getValue(){
        return value;
    }


    public ToastExample() {
        this.instance = this;
        BA = BluetoothAdapter.getDefaultAdapter();
    }

    public static ToastExample instance() {
        if(instance == null) {
            instance = new ToastExample();
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
        BA = BluetoothAdapter.getDefaultAdapter();
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            this.activity.startActivityForResult(turnOn, 1);
            


            Toast.makeText(this.activity, "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.activity, "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off() {
        BA = BluetoothAdapter.getDefaultAdapter();
        Toast.makeText(this.activity, "Trying off", Toast.LENGTH_LONG).show();
        BA.disable();
        Toast.makeText(this.activity, "Turned off", Toast.LENGTH_LONG).show();
    }


    public void visible() {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        this.activity.startActivity(getVisible);
    }


    public String[] list() {
        pairedDevices = BA.getBondedDevices();

        deviceList = new String[pairedDevices.size()];

        int i = 0;
        for (BluetoothDevice bt : pairedDevices)
        {
            deviceList[i] = bt.getName();
            i++;
        }
        Toast.makeText(this.activity, "Showing Paired Devices", Toast.LENGTH_SHORT).show();

        return deviceList;
    }

    public void connect(){
    }
}
