package com.example.asus.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_show);
        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        TextView time = findViewById(R.id.time);
        Intent intent = getIntent();
        String s_name = intent.getStringExtra("name");
        String s_description = intent.getStringExtra("description");
        String s_time = intent.getStringExtra("time");
        name.setText(s_name);
        description.setText(s_description);
        time.setText(s_time);
    }
}
