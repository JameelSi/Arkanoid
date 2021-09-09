package com.majdj_jameels.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_main";
    private static final CharSequence CHANNEL_NAME = "Main Channel";
    private NotificationManager notificationManager;
    private BroadcastReceiver batteryReceiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        hide action bar
        getSupportActionBar().hide();
//        set the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        notifcation
        notificationsSetup();
        broadcastSetup();
    }
    private void notificationsSetup()
    {
        // 1. Get reference Notification Manager system Service
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // 2. Create Notification-Channel. ONLY for Android 8.0 (OREO API level 26) and higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, 	// Constant for Channel ID
                    CHANNEL_NAME, 	// Constant for Channel NAME
                    NotificationManager.IMPORTANCE_HIGH);  // for popup use: IMPORTANCE_HIGH

            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    private void broadcastSetup()
    {
        batteryReceiver = new MyReceiver();
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        // 4. Register the receiver to start listening for battery change messages
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        // 5. Un-Register the receiver to stop listening for battery change messages
        unregisterReceiver(batteryReceiver);
    }
}