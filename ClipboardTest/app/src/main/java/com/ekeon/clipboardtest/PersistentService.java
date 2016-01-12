package com.ekeon.clipboardtest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

/**
 * Created by Ekeon on 2016. 1. 7..
 */
public class PersistentService extends Service {

    private ClipboardManager clipboardManager;
    private String cliptext = "";
    private String translated = "";

    @Override
    public void onCreate() {
        Log.d("PersistentService", "onCreate");
        unregisterRestartAlarm();

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(clipChangedListener);

        super.onCreate();
    }

    private ClipboardManager.OnPrimaryClipChangedListener clipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        @Override
        public void onPrimaryClipChanged() {
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            cliptext = item.getText().toString();
            new TranslateAsync() {
                @Override
                protected void onPostExecute(Void aVoid) {
                    CustomToast customToast = new CustomToast(getApplicationContext());
                    customToast.showToast(translated, Toast.LENGTH_LONG);

                }
            }.execute();
        }
    };

    @Override
    public void onDestroy() {
        Log.d("PersistentService","onDestroy");

        registerRestartAlarm();
        super.onDestroy();
    }

    public void registerRestartAlarm() {
        Intent intent = new Intent(PersistentService.this, RestartService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this, 0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 1000 * 10;

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime * 10, 1000, sender);
    }

    public void unregisterRestartAlarm() {
        Intent intent = new Intent(PersistentService.this, RestartService.class);
        intent.setAction("ACTION.RESTART.PersistentService");
        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this, 0, intent, 0);

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification());
        return super.onStartCommand(intent, flags, startId);
    }

    public class TranslateAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Translate.setClientId("copy_translation");
            Translate.setClientSecret("j552UA79pOo3XsDAmRt6KSVB2Fr9q26hY+iakrU1yJM=");
            try
            {
                translated = Translate.execute(cliptext, Language.ENGLISH, Language.KOREAN);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TAG", "RESTART SERVICE");
        return null;
    }
}
