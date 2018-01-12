package com.example.asus.taskmanager;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
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
                    Toast.makeText(NewNoteActivity.this, "Too large name", Toast.LENGTH_SHORT).show();
                    b = true;
                }
                if (name.length() < 1)
                {
                    Toast.makeText(NewNoteActivity.this, "Empty name of task", Toast.LENGTH_SHORT).show();
                    b = true;
                }
                if (!b)
                {
                    Task task = new Task(name.getText().toString(), new Date(Long.parseLong(data.getText().toString())), description.getText().toString());
                    MainActivity.getTaskList().insert(task);
                    MainActivity.getTaskListAdapter().notifyDataSetChanged();
                    //commemt to commit
                    finish();
                }
            }
        });
    }
}
