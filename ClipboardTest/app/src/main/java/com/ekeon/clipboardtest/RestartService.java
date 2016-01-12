package com.ekeon.clipboardtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Ekeon on 2016. 1. 7..
 */
public class RestartService extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RestartService", "RestartService called! :" + intent.getAction());

        if(intent.getAction().equals("ACTION.RESTART.PERSISTENTSERVICE")){
            Log.d("RestartService", "ACTION_RESTART_PERSISTENTSERVICE");
            Intent i = new Intent(context, PersistentService.class);
            context.startService(i);
        }

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("RestartService", "ACTION_BOOT_COMPLETED");
            Intent i = new Intent(context, PersistentService.class);
            context.startService(i);
        }
    }
}
