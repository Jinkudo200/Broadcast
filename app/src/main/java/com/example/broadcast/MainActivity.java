package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textViewBattery;
    TextView textViewReceiver;
    MyBroadcastBatteryLow myBroadcastBatteryLow = new MyBroadcastBatteryLow();
    MyBroadcastCallReceiver myBroadcastCallReceiver = new MyBroadcastCallReceiver();
    IntentFilter filter = new IntentFilter();
    IntentFilter filter2 = new IntentFilter();


    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = findViewById(R.id.sendbtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("FAKE_EVENT_INFO");
                sendBroadcast(intent);
            }
        });

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }
        }

        IntentFilter filter2 = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);


        filter.addAction(Intent.ACTION_BATTERY_LOW);

        textViewBattery = findViewById(R.id.textViewBattery);

        textViewReceiver = findViewById(R.id.textViewReceiver);



    }

    public class MyBroadcastBatteryLow extends MyReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            textViewBattery.setText("Evenement Batterie faible reçu");
        }
    }

    private class MyBroadcastCallReceiver extends MyReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        textViewReceiver.setText("Incoming call from " + incomingNumber);
                        Toast.makeText(context, "Incoming call from " + incomingNumber, Toast.LENGTH_SHORT).show();
                    }
                }
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
//            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcastBatteryLow, filter);
        registerReceiver(myBroadcastCallReceiver, filter2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastBatteryLow);
        unregisterReceiver(myBroadcastCallReceiver);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]
                                                   grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            } }
    }

}


