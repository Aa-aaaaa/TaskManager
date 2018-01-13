package com.example.asus.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    static private TaskList taskList = new TaskList();
    static private TaskListAdapter taskListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList.insert(new Task("MyTask1", new Date(1337 * 1000 * 60 * 60), "Easy"));
        taskList.insert(new Task("MyTask2", new Date(1488 * 1000 * 60 * 60), "Hard"));
        taskList.insert(new Task("MyTask3", new Date(322 * 1000 * 60 * 60), "Very-Hard"));
        taskListAdapter = new TaskListAdapter(taskList);

        ((ListView)findViewById(R.id.taskList)).setAdapter(taskListAdapter);
        ((Button)findViewById(R.id.buttonGoMakeNewTask)).
                setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public static TaskList getTaskList() {
        return taskList;
    }

    public static TaskListAdapter getTaskListAdapter() {
        return taskListAdapter;
    }
}
