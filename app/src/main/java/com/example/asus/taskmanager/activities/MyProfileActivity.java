package com.example.asus.taskmanager.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.taskmanager.R;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.one:
                        startActivity(new Intent(MyProfileActivity.this, MainActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.two:
                        return true;
                    case R.id.three:
                        startActivity(new Intent(MyProfileActivity.this, MainActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.four:
                        startActivity(new Intent(MyProfileActivity.this, MainActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.five:
                        startActivity(new Intent(MyProfileActivity.this, MainActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                };
                return false;
            }
        });

        TextView tvFullName = findViewById(R.id.tvFullName);
        TextView tvEmail = findViewById(R.id.tvEmail);

        tvFullName.setText(MainActivity.getUser().getToken());
        //tvEmail.setText(MainActivity.getUser().getUsername());

        Button bLogout = findViewById(R.id.bLogout);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getUser().logout(MyProfileActivity.this);
                startActivity(new Intent(MyProfileActivity.this, LoginActivity.class).
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }
}
