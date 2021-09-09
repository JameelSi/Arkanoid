package com.majdj_jameels.ex2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;


import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "channel_main";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        switch (action)
        {
            case Intent.ACTION_BATTERY_CHANGED:

                // get battery level from the received Intent
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = level * 100 / (float)scale;
                if(batteryPct<=10 && !isCharging){
                    notify(context,intent);
                }
                break;

        }
    }
        public void notify(Context context, Intent intent) {
        int notificationID=1;
        String title = "Battery Life";
        String text = "Warning! your battery is at 10% , find a charger ASAP!";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE );
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // 3. Create & show the Notification. on Build.VERSION < OREO notification avoid CHANEL_ID
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notify_bell)
                .setContentTitle(title + " ("+ notificationID +")")
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(notificationID++, notification);
    }

}
