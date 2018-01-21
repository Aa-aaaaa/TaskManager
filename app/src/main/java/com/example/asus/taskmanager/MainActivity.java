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
    static private TaskListAdapter taskListAdapter;
    //static private String token = "503712e87969da1ab86c6eafa9b0e6d1ac81441b";
    static private String token = null;
    //static private String username = "admin@admin.com";
    static private String username = null;
    //static private String password = "123456AB";
    static private String password = null;
    public final static String PREFERENCES_FILE_NAME = "Settings";

    private FragmentsNow fragmentsNow = FragmentsNow.getInstance();

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
        final TaskList taskList = TaskList.getInstance(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        MainActivity.setToken(sharedPreferences.getString("token", null));
        Intent intent = new Intent(MainActivity.this, LoginActivity.class).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (getToken() == null)
            startActivity(intent);

        FoneService.getToken(username, password, MainActivity.this);
        //dataBase = new DataBase(MainActivity.this);
        //TaskList.getInstance(MainActivity.this).getDataBase().onUpgrade("");
        //TaskList.getInstance(MainActivity.this).clearDataBase();
        //TaskList.getInstance(MainActivity.this).getDataBase().addAllToGlobalDB();
        //MyDate myDate = new MyDate();
        //myDate.stringToTime("28.02.2019 12:00");
        //TaskList.getInstance(MainActivity.this).addTask(new Task("Check loging",  myDate, "It will be doing until it works"));
        //token = FoneService.getToken("admin@admin.com", "123456AB", MainActivity.this);
        //FoneService.registration("megamax143@gmail.com", "123456Aa", "Maksim", "Nyashin", MainActivity.this);
        taskListAdapter = new TaskListAdapter(TaskList.getInstance(MainActivity.this).getDataBase().getAllNotesFromDataBase());

        startAll();
    }

    @Override
    public void onTaskListDataListener(Bundle bundle) {
        if (check_land())
        {
            fragmentsNow.set(true, true, false);
            fragmentsNow.setTSF(bundle.getInt("index"));
            getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
        }
        else
        {
            fragmentsNow.set(false, true, false);
            fragmentsNow.setTSF(bundle.getInt("index"));
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTSF()).commit();
        }
        fragmentsNow.setCloseAll(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTaskShowDataListener() {
        taskListAdapter.notifyDataSetChanged();
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

    public static TaskListAdapter getTaskListAdapter() {
        return taskListAdapter;
    }

    public static String getToken()
    {
        return token;
    }
    public static void setToken(String newToken)
    {
        token = newToken;
    }

    public static String getUsername(){
        return username;
    }
    public static void  setUsername(String newUsername){
        username = newUsername;
    }

    public static String getPassword(){
        return password;
    }
    public static void  setPassword(String newPassword){
        password = newPassword;
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
