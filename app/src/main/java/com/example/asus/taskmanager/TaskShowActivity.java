package com.example.asus.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        final Long dataBaseId = getIntent().getLongExtra("dataBaseId", 0);
        Log.d("Deb", dataBaseId.toString());
        final EditText name = (EditText)findViewById(R.id.name);
        name.setText(TaskList.getInstance(getApplicationContext()).getTask(dataBaseId).getName());
        final EditText time = (EditText)findViewById(R.id.time);
        time.setText(TaskList.getInstance(getApplicationContext()).getTask(dataBaseId).getTime().getString());
        final EditText description = (EditText)findViewById(R.id.description);
        description.setText(TaskList.getInstance(getApplicationContext()).getTask(dataBaseId).getDescription());

        ((Button)findViewById(R.id.buttonDeleteTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskList.getInstance(getApplicationContext()).deleteTask(dataBaseId);
                MainActivity.getTaskListAdapter().notifyDataSetChanged();
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
                TaskList.getInstance(getApplicationContext()).changeTask(new Task(name.getText().toString(), myDate, description.getText().toString(), dataBaseId, TaskList.getInstance(getApplicationContext()).getTask(dataBaseId).getGlobalDataBaseId()));
                MainActivity.getTaskListAdapter().notifyDataSetChanged();
                finish();
            }
        });
    }
}
