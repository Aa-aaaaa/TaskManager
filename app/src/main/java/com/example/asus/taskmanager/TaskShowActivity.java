package com.example.asus.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class TaskShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        final int index = getIntent().getIntExtra("index", 0);
        final TextView name = (TextView)findViewById(R.id.name);
        name.setText(MainActivity.getTaskList().getTask(index).getName());
        final TextView time = (TextView)findViewById(R.id.time);
        time.setText(MainActivity.getTaskList().getTask(index).getTime().toString());
        final TextView description = (TextView)findViewById(R.id.description);
        description.setText(MainActivity.getTaskList().getTask(index).getDescription());

        ((Button)findViewById(R.id.buttonDeleteTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getTaskList().deleteTask(index);
                MainActivity.getTaskListAdapter().notifyDataSetChanged();
                finish();
            }
        });

        ((Button)findViewById(R.id.buttonSaveChanges)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getTaskList().changeTask(index, new Task(
                        name.getText().toString(),
                        new Date(Long.parseLong(time.getText().toString())),
                        description.getText().toString()
                ));
                MainActivity.getTaskListAdapter().notifyDataSetChanged();
                finish();
            }
        });
    }
}
