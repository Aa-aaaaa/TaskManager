package com.example.asus.taskmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Date;

import static android.media.RingtoneManager.TYPE_NOTIFICATION;

public class MainActivity extends AppCompatActivity
{
    private static TaskListAdapter taskListAdapter;
    private static NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        taskListAdapter = new TaskListAdapter(TaskList.getInstance());

        ((ListView)findViewById(R.id.taskList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TaskShowActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });
        ((ListView)findViewById(R.id.taskList)).setAdapter(taskListAdapter);

        ((Button)findViewById(R.id.buttonGoMakeNewTask)).
                setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        startActivity(new Intent(MainActivity.this, NewTaskActivity.class));
                    }
                }
        );
    }

    public static TaskListAdapter getTaskListAdapter() {
        return taskListAdapter;
    }
}
