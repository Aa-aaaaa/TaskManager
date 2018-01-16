package com.example.asus.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class TaskShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        final TaskList taskList = TaskList.getInstance();
        final int index = getIntent().getIntExtra("index", 0);
        final TextView name = (TextView)findViewById(R.id.name);
        name.setText(taskList.getTask(index).getName());
        final TextView time = (TextView)findViewById(R.id.time);
        time.setText(taskList.getTask(index).getTime().getString());
        final TextView description = (TextView)findViewById(R.id.description);
        description.setText(taskList.getTask(index).getDescription());

        ((Button)findViewById(R.id.buttonDeleteTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskList.deleteTask(index);
      //          MainActivity.getTaskListAdapter().notifyDataSetChanged();
                finish();
            }
        });

        ((Button)findViewById(R.id.buttonSaveChanges)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDate myDate = new MyDate();
                if (!myDate.stringToTime(time.getText().toString()))
                {
                    Toast.makeText(TaskShowActivity.this, "Bad date", Toast.LENGTH_SHORT).show();
                    return;
                }
                taskList.changeTask(index, new Task(name.getText().toString(), myDate, description.getText().toString(), MainActivity.getDataBase().getSize()));
                finish();
            }
        });
    }
}
