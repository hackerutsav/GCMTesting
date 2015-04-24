package com.example.nagarro.gcm;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    String regId;
    static final String SENDER_ID = "763924717190";


    public String getRegIdFromSharedPreferences() {
        SharedPreferences preferences = this.getSharedPreferences("gcm", Context.MODE_PRIVATE);
        return preferences.getString("gcm_reg", null);
    }

    public void saveRegIdInSharedPreferences(String s) {
        SharedPreferences.Editor preferences = this.getSharedPreferences("gcm", Context.MODE_PRIVATE).edit();
        preferences.putString("gcm_reg", s);
        preferences.commit();
    }

    public void onClick(View v) {
//        final Bundle b = new Bundle();
//        b.putString("Hello", "back world");
//        b.putString("blah", "blah back");
//        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
//        final String msgId = "100";
//        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    gcm.send(SENDER_ID + "@gcm.googleapis.com", msgId, b);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        };
//        task.execute();
        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        NotificationManager nm =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        b.setContentText("received blah");
        b.setContentTitle("YAY");
        b.setSmallIcon(R.drawable.ic_launcher);
        Intent i1 = new Intent();
        i1.setClass(this, MainActivity.class);
        PendingIntent i = PendingIntent.getActivity(this, 1, i1, 0);
        b.setContentIntent(i);
        b.addAction(R.drawable.ic_launcher, "text", i);
        nm.notify(1, b.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regId = getRegIdFromSharedPreferences();
        Log.i("registeredID", regId);
        TextView tv = (TextView)findViewById(R.id.text1);
        tv.setText(regId);
        if (regId == null) {
            final Activity a = this;
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(a);
                    String registerId = null;
                    try {
                        registerId = gcm.register(SENDER_ID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return registerId;
                }

                @Override
                protected void onPostExecute(String s) {
                    Log.i("registration id", s);
                    Toast.makeText(a, s, Toast.LENGTH_LONG).show();
                    saveRegIdInSharedPreferences(s);
                }
            };
            task.execute();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
