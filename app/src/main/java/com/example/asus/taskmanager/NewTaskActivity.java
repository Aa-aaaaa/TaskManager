package com.example.asus.taskmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

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
            EditText name = findViewById(R.id.editTextName);
            EditText date = findViewById(R.id.editTextDate);
            EditText notificationDate = findViewById(R.id.editTextNotificationDate);
            EditText description = findViewById(R.id.editTextDescription);
            if (name.length() > 50) {
                Toast.makeText(NewTaskActivity.this, "Too large name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (name.length() < 1) {
                Toast.makeText(NewTaskActivity.this, "Empty name of task", Toast.LENGTH_SHORT).show();
                return;
            }
            MyDate myDate = new MyDate();
            if (!myDate.setTime(date.getText().toString()) ||
                    myDate.getTime() < new Date().getTime()) {
                Toast.makeText(NewTaskActivity.this, "Bad date", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = new Task(name.getText().toString(), myDate, description.getText().toString());
            if (((CheckBox) findViewById(R.id.checkBoxNeedNotification)).isChecked()) {
                MyDate myNotificationDate = new MyDate();
                if (!myNotificationDate.setTime(notificationDate.getText().toString()) ||
                        myDate.getTime() <= myNotificationDate.getTime() ||
                        myNotificationDate.getTime() < new Date().getTime()) {
                    Toast.makeText(NewTaskActivity.this, "Bad notification date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    TaskList.getInstance().addTask(task);
                    MyNotification.getInstance().sendNotificationOfUpcomingTask(NewTaskActivity.this, task, myNotificationDate.getTime());
                    finish();
                }
            }
            TaskList.getInstance().addTask(task);
            MainActivity.getTaskListAdapter().notifyDataSetChanged();
            finish();
            }
        });
    }

}

