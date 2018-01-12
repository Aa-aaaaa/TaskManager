package com.example.asus.taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_task_view, strings);
        TaskListAdapter<Task> adapter =;
        ((ListView)findViewById(R.id.taskList)).setAdapter(adapter);
    }
}
