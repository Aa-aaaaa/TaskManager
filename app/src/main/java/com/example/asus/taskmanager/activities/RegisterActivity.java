package com.example.asus.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.Utils;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button bRegister = findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.etName)).getText().toString();
                String lastName = ((EditText)findViewById(R.id.etLastName)).getText().toString();
                String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
                String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Name cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(RegisterActivity.this, "Email address is invalid", Toast.LENGTH_LONG ).show();
                    return;
                }
                if (password.length() < 8)
                {
                    Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }
                User.registration(new User(email, password, name, lastName), RegisterActivity.this, new TaskList.PerformObject() {
                    @Override
                    public void perform(Object object) {
                        MainActivity.getUser().setUser((User)object);
                        RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                }, new Utils.OnErrorCallback() {
                    @Override
                    public void perform() {

                    }
                });
            }
        });
    }
}
