package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
         if (intent.getAction().equals("FAKE_EVENT_INFO")) {
             Log.i("BR_TAG"," Event Received ");
        }
    }
}