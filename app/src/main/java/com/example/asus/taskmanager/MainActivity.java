package com.example.asus.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import static com.example.asus.taskmanager.R.id;
import static com.example.asus.taskmanager.R.layout;

public class MainActivity extends AppCompatActivity implements TaskListFragment.OnTaskListDataListener, TaskShowFragment.OnTaskShowDataListener
{
    static private User user = new User();
    static private TaskListAdapter taskListAdapter;
    public final static String PREFERENCES_FILE_NAME = "Settings";

    private FragmentsNow fragmentsNow = FragmentsNow.getInstance();

    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
    }*/

    public void onResume()
    {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        MainActivity.setToken(sharedPreferences.getString("token", null));
        Intent intent = new Intent(MainActivity.this, LoginActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (getToken() == null)
            startActivity(intent);
        else
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        MainActivity.setToken(sharedPreferences.getString("token", null));
        Intent intent = new Intent(MainActivity.this, LoginActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (getToken() == null)
            startActivity(intent);

        FoneService.getToken(user, MainActivity.this);
        taskListAdapter = new TaskListAdapter(TaskList.getInstance(MainActivity.this).getDataBase().getAllNotesFromDataBase());

        startAll();
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

    public static String getToken()
    {
        return user.getToken();
    }
    public static void setToken(String newToken)
    {
        user.setToken(newToken);
    }

    public static String getUsername(){
        return user.getLogin();
    }
    public static void  setUsername(String newUsername){
        user.setLogin(newUsername);
    }

    public static String getPassword(){
        return user.getPassword();
    }
    public static void  setPassword(String newPassword){
        user.setPassword(newPassword);
    }

    public static User getUser() {
        return user;
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
}
