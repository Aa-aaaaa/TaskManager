package com.example.asus.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.TalkingToServerService;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.Utils;

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
                User.getToken(user, LoginActivity.this, new TaskList.PerformObject() {
                    @Override
                    public void perform(Object object) {
                        TalkingToServerService.Token token = (TalkingToServerService.Token) object;
                        MainActivity.getUser().succesfullLogin(LoginActivity.this, token.getToken());

                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, new Utils.OnErrorCallback() {
                    @Override
                    public void perform() {
                        Toast.makeText(LoginActivity.this.getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                        Log.d("LOGIN", "Can't sign in");
                    }
                });
            }
        });
    }
//☺
}
