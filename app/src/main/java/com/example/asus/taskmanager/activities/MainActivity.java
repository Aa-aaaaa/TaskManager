package com.example.asus.taskmanager.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.asus.taskmanager.FragmentsNow;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.fragments.MyProfileFragment;
import com.example.asus.taskmanager.fragments.TaskListFragment;
import com.example.asus.taskmanager.fragments.TaskShowFragment;
import com.example.asus.taskmanager.User;

import static com.example.asus.taskmanager.R.*;

public class MainActivity extends AppCompatActivity implements TaskListFragment.OnTaskListDataListener, TaskShowFragment.OnTaskShowDataListener, MyProfileFragment.OnMyProfileDataListener
{
    static private final String serverName = "http://salty-springs-72589.herokuapp.com/api/";

    static private User user = new User();
    static private FragmentsNow fragmentsNow = FragmentsNow.getInstance();
    private BottomNavigationView bottomNavigationView;

    static public final String PREFERENCES_FILE_NAME = "Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        user.setInfoFromSP(MainActivity.this);
        if (user.getToken() == null)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class).
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        bottomNavigationView = (BottomNavigationView)findViewById(id.bottom_navigation);
        startAll();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.one:
                        startMyTasks();
                        return true;
                    case R.id.two:
                        startMyProfile();
                        return true;
                    case R.id.three:
                        return true;
                    case id.four:
                        return true;
                    case id.five:
                        fragmentsNow.setNumber_of_fragment_block(5);
                        startAll();
                        return true;
                }
                return false;
            }
        });
    }

    void startAll() {
        switch (fragmentsNow.getNumber_of_fragment_block()) {
            case 5:
                fragmentsNow.setMyTasks(false, true, false);
                fragmentsNow.setNumber_of_fragment_block(1);
            case 1:
                startMyTasks();
                bottomNavigationView.setSelectedItemId(id.one);
                return;
            case 2:
                startMyProfile();
                bottomNavigationView.setSelectedItemId(id.two);
                return;
            case 3:
                bottomNavigationView.setSelectedItemId(id.three);
                return;
            case 4:
                bottomNavigationView.setSelectedItemId(id.four);
                return;
        }
    }

    private boolean check_land()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void startMyTasks()
    {
        fragmentsNow.setNumber_of_fragment_block(1);
        if (getFragmentManager().findFragmentById(id.all_screen) != null)
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(id.all_screen)).commit();
        if (fragmentsNow.getTSF() == null) {
            if (check_land()) {
                fragmentsNow.setMyTasks(true, false, true);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getEF()).commit();
                getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            }
            else {
                fragmentsNow.setMyTasks(true, false, false);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTLF()).commit();
            }
        }
        else {
            if (check_land())
            {
                fragmentsNow.setMyTasks(true, true, false);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
                getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            }
            else
            {
                fragmentsNow.setMyTasks(false, true, false);
                getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
            }
        }
    }

    private void startMyProfile()
    {
        fragmentsNow.setNumber_of_fragment_block(2);
        if (getFragmentManager().findFragmentById(id.other) != null)
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(id.other)).commit();
        if (check_land() && getFragmentManager().findFragmentById(id.list) != null)
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(id.list)).commit();
        fragmentsNow.setMyProfile();
        getFragmentManager().beginTransaction().replace(id.all_screen, fragmentsNow.getMPF()).commit();
        fragmentsNow.setCloseAll(false);
    }

    @Override
    public void onTaskListDataListener(Bundle bundle) {
        fragmentsNow.setMyTasks(false, true, false);
        fragmentsNow.setTSF(bundle.getLong("index"));
        startAll();
        fragmentsNow.setCloseAll(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTaskShowDataListener() {
        fragmentsNow.setMyTasks(true, false, true);
        startAll();
        fragmentsNow.setCloseAll(false);
    }

    @Override
    public void onBackPressed() {
        if (fragmentsNow.getNumber_of_fragment_block() == 1)
        {
            fragmentsNow.setMyTasks(false, false, false);
            startMyTasks();
        }
        if (fragmentsNow.isCloseAll())
            super.onBackPressed();
        fragmentsNow.setCloseAll(true);
    }

    @Override
    public void goLoginActivityListener() {
        MainActivity.getUser().logout(MainActivity.this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
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
