package com.ekeon.clipboardtest;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver receiver = new RestartService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentMyService = new Intent(this, PersistentService.class);
        receiver = new RestartService();

        try{
            IntentFilter mainFilter = new IntentFilter("com.ekeon.clipboardtest.restart");
            registerReceiver(receiver, mainFilter);
            startService(intentMyService);
        }
        catch (Exception e) {
            Log.d("RestartService",e.getMessage()+"");
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
