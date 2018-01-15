package com.example.asus.taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationPublisher extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationManager notificationManager = (NotificationManager)context.
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)intent.getLongExtra("notificationId", 0),
                (Notification)intent.getParcelableExtra("notification"));
    }
}
