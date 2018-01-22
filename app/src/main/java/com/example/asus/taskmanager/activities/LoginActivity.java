package com.example.asus.taskmanager.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asus.taskmanager.FoneService;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button bRegisterHere = (Button)findViewById(R.id.bRegisterHere);

        bRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        Button bLoginButton = findViewById(R.id.bLogin);

        bLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
                String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();

                Log.d("EMAIL + PASSWORD", email + " " + password);
                User user = new User(email, password);
                FoneService.getToken(user, LoginActivity.this);
            }
        });
    }
}
