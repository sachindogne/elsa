package com.androdev.assistantspeechrecognizer;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

public class BasicTasks {

    public static void changeWifiState(Context context, boolean state) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {
            if (WifiManager.WIFI_STATE_ENABLED == wifiManager.getWifiState()) {
                if (state) {
                    Log.d("BasicTasks", "changeWifiState: wifi already on");
                    new TextToSpeechProcessor(context, "wifi is already on");
                } else {
                    wifiManager.setWifiEnabled(false);
                    Log.d("BasicTasks", "changeWifiState: wifi turned off");
                    new TextToSpeechProcessor(context, "wifi is turned off");
                }
            } else {
                if (state) {
                    wifiManager.setWifiEnabled(true);
                    Log.d("BasicTasks", "changeWifiState: wifi turned off");
                    new TextToSpeechProcessor(context, "wifi is turned off");
                } else {
                    Log.d("BasicTasks", "changeWifiState: wifi already off");
                    new TextToSpeechProcessor(context, "wifi is already off");
                }
            }
        }
    }

    public static void openWifiSettings(Context context) {
        new TextToSpeechProcessor(context, "opening wi-fi settings");
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    public static void changeBluetoothState(Context context, boolean state) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter.isEnabled()) {
            if (state) {
                Log.d("BasicTasks", "changeBluetoothState: bluetooth already on");
                new TextToSpeechProcessor(context, "bluetooth is already on");
            } else {
                bluetoothAdapter.disable();
                Log.d("BasicTasks", "changeBluetoothState: bluetooth turned off");
                new TextToSpeechProcessor(context, "bluetooth is turned off");
            }
        } else {
            if (state) {
                bluetoothAdapter.enable();
                Log.d("BasicTasks", "changeBluetoothState: bluetooth turned on");
                new TextToSpeechProcessor(context, "bluetooth is turned on");
            } else {
                Log.d("BasicTasks", "changeBluetoothState: bluetooth already turned off");
                new TextToSpeechProcessor(context, "bluetooth is already off");
            }
        }
    }

    public static void openBluetoothSettings(Context context) {
        new TextToSpeechProcessor(context, "opening bluetooth settings");
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        context.startActivity(intent);
    }

//    public static void shutdownPhone(Context context) {
//        new TextToSpeechProcessor(context, "switching off the phone");
//        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
//        context.sendBroadcast(intent);
//    }
//
//    public static void rebootPhone(Context context) {
//        new TextToSpeechProcessor(context, "phone will restart now");
//        Intent intent = new Intent(Intent.ACTION_REBOOT);
//        context.startActivity(intent);
//    }
//
    public static void changeAirplaneModeState(Context context) {
        // read the airplane mode setting
        boolean isEnabled = Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;

        // toggle airplane mode
        Settings.System.putInt(
                context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);

        // Post an intent to reload
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", !isEnabled);
        context.sendBroadcast(intent);
    }
}
