package com.example.asus.taskmanager;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    //commemt to commit
                    finish();
                }
            }
        });
    }
}
