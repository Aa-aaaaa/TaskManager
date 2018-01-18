package com.example.asus.taskmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static android.media.RingtoneManager.TYPE_NOTIFICATION;
import static java.lang.Math.toIntExact;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Button button = findViewById(R.id.buttonMakeNewNote);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("SingleStatementInBlock")
            @Override
            public void onClick(View view) {
                boolean b = false;
                EditText name = findViewById(R.id.editTextName);
                EditText data = findViewById(R.id.editTextDate);
                EditText description = findViewById(R.id.editTextDescription);
                if (name.length() > 50)
                {
                    Toast.makeText(NewTaskActivity.this, "Too large name", Toast.LENGTH_SHORT).show();
                    b = true;
                }
                if (name.length() < 1)
                {
                    Toast.makeText(NewTaskActivity.this, "Empty name of task", Toast.LENGTH_SHORT).show();
                    b = true;
                }
                MyDate myDate = new MyDate();
                if (!myDate.stringToTime(data.getText().toString()))
                {
                    Toast.makeText(NewTaskActivity.this, "Bad date", Toast.LENGTH_SHORT).show();
                    b = true;
                }
                if (!b)
                {
                    Task task = new Task(name.getText().toString(), myDate, description.getText().toString());
                    TaskList.getInstance().addTask(task);

                    Intent intent = new Intent(NewTaskActivity.this, TaskShowActivity.class);
                    intent.putExtra("dataBaseId", task.getDataBaseId());
                    PendingIntent pendingIntent = PendingIntent.
                            getActivity(NewTaskActivity.this, 0, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(NewTaskActivity.this).
                                    setContentIntent(pendingIntent).
                                    setContentTitle(name.getText().toString() + " " + myDate.getString()).
                                    setTicker(name.getText().toString()).
                                    setContentText(description.getText().toString()).
                                    setSmallIcon(R.drawable.ic_launcher_background).
                                    setSound(RingtoneManager.getDefaultUri(TYPE_NOTIFICATION)).
                                    setAutoCancel(true);

                    MainActivity.getTaskListAdapter().notifyDataSetChanged();
                    scheduleNotification(builder.build(), task.getDataBaseId() , 7 * 1000);
                    finish();
                }
            }
        });
    }
    public void scheduleNotification(Notification notification, long notificationId, long delay)
    {
        long futureTime = Calendar.getInstance().getTimeInMillis() + delay;

        Intent intent = new Intent(this, NotificationPublisher.class);
        intent.putExtra("notification", notification);
        intent.putExtra("notificationId", notificationId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureTime, pendingIntent);
    }
}
