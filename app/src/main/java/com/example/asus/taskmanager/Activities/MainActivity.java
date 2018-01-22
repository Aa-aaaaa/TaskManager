package com.example.asus.taskmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.asus.taskmanager.FragmentsNow;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.TaskListFragment;
import com.example.asus.taskmanager.TaskShowFragment;
import com.example.asus.taskmanager.User;

import static com.example.asus.taskmanager.R.*;

public class MainActivity extends AppCompatActivity implements TaskListFragment.OnTaskListDataListener, TaskShowFragment.OnTaskShowDataListener
{
    static private final String serverName = "http://salty-springs-72589.herokuapp.com/api/";

    static private User user = new User();
    static private FragmentsNow fragmentsNow = FragmentsNow.getInstance();

    static public final String PREFERENCES_FILE_NAME = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.one:
                        return true;
                    case R.id.two:
                        startActivity(new Intent(MainActivity.this, MyProfileActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        return true;
                    case R.id.three:
                        startActivity(new Intent(MainActivity.this, MyProfileActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        return true;
                    case id.four:
                        startActivity(new Intent(MainActivity.this, MyProfileActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        return true;
                    case id.five:
                        startActivity(new Intent(MainActivity.this, MyProfileActivity.class).
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                        return true;
                };
                return false;
            }
        });
    }

    public void onResume() {
        super.onResume();
        user.setInfoFromSP(MainActivity.this);
        if (user.getToken() == null)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class).
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
        startAll();
    }

    private boolean check_land()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void startAll()
    {
      if (fragmentsNow.getTSF() == null) {
            if (check_land()) {
                fragmentsNow.set(true, false, true);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getEF()).commit();
                getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            }
            else {
                fragmentsNow.set(true, false, false);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTLF()).commit();
            }
        }
        else {
            if (check_land())
            {
                fragmentsNow.set(true, true, false);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
                getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            }
            else
            {
                fragmentsNow.set(false, true, false);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
            }
        }
        fragmentsNow.setCloseAll(false);
    }

    @Override
    public void onTaskListDataListener(Bundle bundle) {
        if (check_land())
        {
            fragmentsNow.set(true, true, false);
            fragmentsNow.setTSF(bundle.getLong("index"));
            getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
        }
        else
        {
            fragmentsNow.set(false, true, false);
            fragmentsNow.setTSF(bundle.getLong("index"));
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
        }
        fragmentsNow.setCloseAll(false);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTaskShowDataListener() {
        if (check_land())
        {
            fragmentsNow.set(true, false, true);
            getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getEF()).commit();
        }
        else
        {
            fragmentsNow.set(true, false, false);
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTLF()).commit();
        }
        fragmentsNow.setCloseAll(false);
    }

    @Override
    public void onBackPressed() {
        if (check_land())
        {
            fragmentsNow.set(true, false, true);
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getEF()).commit();
            getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
        }
        else
        {
            fragmentsNow.set(true, false, false);
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTLF()).commit();
        }
        if (fragmentsNow.isCloseAll())
            super.onBackPressed();
        fragmentsNow.setCloseAll(true);
    }

    public static String getServerName()
    {
        return serverName;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MainActivity.user = user;
    }
}
