package com.example.nagarro.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by nagarro on 11/04/15.
 */
public class GCMReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Received", "something");
        Bundle extras = intent.getExtras();
        String a = extras.getString("Hello");
        Set<String> keys = extras.keySet();
        Toast.makeText(context, extras.getString("Hello"), Toast.LENGTH_LONG).show();


    }
}
