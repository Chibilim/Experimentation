package com.amiskovic.test.btandroidlib;


import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class BluetoothThread extends Thread {
    private BluetoothSocket currentSocket;
    private BluetoothDevice currentDevice;
    private Activity activity;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public BluetoothThread(BluetoothDevice currentDevice, Activity activity) {
        this.currentDevice = currentDevice;
        this.activity = activity;

        if(currentDevice!=null)
        {
            try{
                currentSocket = currentDevice.createInsecureRfcommSocketToServiceRecord(myUUID);
            }catch (IOException e)
            {
                Toast.makeText(this.activity, "createRfcommSocketToServiceRecord failed" , Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Socket's create() method failed", e);
            }
        }
    }

    public void run(){
        if(currentSocket!=null)
        {
            try {
                currentSocket.connect();
            }catch(IOException e){
                Toast.makeText(this.activity, "Connection Failed "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Could not close the client socket", e);
                cleanDeviceConnection();
                throw new RuntimeException(e);
            }
        }
    }

    private void cleanDeviceConnection()
    {
        if(currentDevice!=null)
            currentDevice=null;
        if(currentSocket!=null)
        {
            try {
                currentSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
            currentSocket=null;
        }
    }

    public void cancel()
    {
        cleanDeviceConnection();
    }
}

