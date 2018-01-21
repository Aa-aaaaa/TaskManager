package com.example.asus.taskmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.ALARM_SERVICE;
import static android.media.RingtoneManager.TYPE_NOTIFICATION;

public class MyNotification
{
    private static MyNotification myNotification;

    public static MyNotification getInstance()
    {
        return (myNotification == null ? myNotification = new MyNotification() : myNotification);
    }

    public void sendNotificationOfUpcomingTask(Context context, Task task, long exactTime)
    {
        Intent intent = new Intent(context, TaskShowFragment.class);
        intent.putExtra("dataBaseId", task.getDataBaseId());
        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, (int)task.getDataBaseId(), intent,
                        PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context).
                        setContentIntent(pendingIntent).
                        setContentTitle(task.getName() + " At " + task.getTime().toString()).
                        setTicker(task.getName()).
                        setContentText(task.getDescription()).
                        setSmallIcon(R.drawable.ic_launcher_background).
                        setSound(RingtoneManager.getDefaultUri(TYPE_NOTIFICATION)).
                        setAutoCancel(true);

        scheduleNotification(context, builder.build(), task.getDataBaseId(), exactTime);
    }

    public void scheduleNotification(Context context, Notification notification, long notificationId, long exactTime)
    {

        Intent intent = new Intent(context, NotificationPublisher.class);
        intent.putExtra("notification", notification);
        intent.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                (int)notificationId, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, exactTime, pendingIntent);
    }
}
