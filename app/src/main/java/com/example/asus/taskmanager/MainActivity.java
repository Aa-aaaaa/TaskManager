package com.example.asus.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    TaskList taskList = new TaskList();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        taskList.insert(new Task("MyTask1", new Date(1337 * 1000 * 60 * 60), "Easy"));
        taskList.insert(new Task("MyTask2", new Date(1488 * 1000 * 60 * 60), "Hard"));
        taskList.insert(new Task("MyTask3", new Date(322 * 1000 * 60 * 60), "Very-Hard"));
        setContentView(R.layout.activity_main);
        ((ListView)findViewById(R.id.taskList)).setAdapter(new TaskListAdapter(taskList));
    }
}
