package com.example.asus.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class TaskShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        final Long dataBaseId = getIntent().getLongExtra("dataBaseId", 0);
        Log.d("OPENED 1", dataBaseId.toString());
        Task task = TaskList.getInstance().getTask(dataBaseId);
        if (task == null)
        {
            finish();
            startActivity(new Intent(TaskShowActivity.this, MainActivity.class));
            return;
        }
        final EditText name = (EditText)findViewById(R.id.name);
        name.setText(task.getName());
        final EditText time = (EditText)findViewById(R.id.time);
        time.setText(task.getTime().toString());
        final EditText description = (EditText)findViewById(R.id.description);
        description.setText(task.getDescription());

        ((Button)findViewById(R.id.buttonDeleteTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskList.getInstance().deleteTask(dataBaseId);
                MainActivity.getTaskListAdapter().notifyDataSetChanged();
                finish();
            }
        });

        ((Button)findViewById(R.id.buttonSaveChanges)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDate myDate = new MyDate();
                if (!myDate.setTime(time.getText().toString()))
                {
                    Toast.makeText(TaskShowActivity.this, "Bad date", Toast.LENGTH_SHORT).show();
                    return;
                }
                TaskList.getInstance().changeTask(new Task(name.getText().toString(), myDate, description.getText().toString(), dataBaseId));
                MainActivity.getTaskListAdapter().notifyDataSetChanged();
                finish();
            }
        });
    }
}
