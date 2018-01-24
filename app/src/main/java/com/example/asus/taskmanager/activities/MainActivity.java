package com.example.asus.taskmanager.activities;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.asus.taskmanager.FragmentsNow;
import com.example.asus.taskmanager.MyDate;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.ServerTask;
import com.example.asus.taskmanager.fragments.FeedFragment;
import com.example.asus.taskmanager.TalkingToServerService;
import com.example.asus.taskmanager.Task;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.Utils;
import com.example.asus.taskmanager.fragments.MyProfileFragment;
import com.example.asus.taskmanager.fragments.TaskListFragment;
import com.example.asus.taskmanager.fragments.TaskShowFragment;
import com.example.asus.taskmanager.fragments.UserListFragment;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.asus.taskmanager.R.id;
import static com.example.asus.taskmanager.R.layout;

public class MainActivity extends AppCompatActivity implements TaskListFragment.OnTaskListDataListener, TaskShowFragment.OnTaskShowDataListener, MyProfileFragment.OnMyProfileDataListener
{
    final static private String serverName = "http://salty-springs-72589.herokuapp.com/api/";
    private static String addToToken = "Token ";
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(serverName).addConverterFactory(GsonConverterFactory.create()).build();
    private static TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
    private static String TAG = "DebugMax";

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
        User.getMyself(new TaskList.PerformObject() {
            @Override
            public void perform(Object object) {
                User user1 = (User) object;
                user1.setToken(user.getToken());
                user1.setPassword(user.getPassword());
                MainActivity.setUser(user1);
                MainActivity.getUser().successfullGotId(MainActivity.this, user.getId());
                TaskList.getInstance(MainActivity.this).getAllTasksFromGlobalDataBase(new TaskList.PerformObject() {
                    @Override
                    public void perform(Object object) {
                        List<ServerTask> list = (List<ServerTask>)object;
                        Log.d(TAG, "perform: " + list.toString());
                        for (ServerTask serverTask: list)
                        {
                            Task task = TaskList.getInstance(MainActivity.this).getDataBase().getTaskByGlobalId(serverTask.getId());
                            if (task == null)
                            {
                                Log.d(TAG, "perform: " + serverTask.getEndTime());
                                MyDate myDate = new MyDate();
                                myDate.setTimeFromDB(serverTask.getEndTime());
                                Task task1 = new Task(serverTask.getName(), myDate, serverTask.getDescription());
                                TaskList.getInstance(MainActivity.this).getDataBase().addTask(task1);
                            }
                            else
                            {
                                Log.e(TAG, "perform: " + serverTask.getEndTime());
                                task.setDescription(serverTask.getDescription());
                                task.setName(serverTask.getName());
                                task.getTime().setTimeFromDB(serverTask.getEndTime());
                                TaskList.getInstance(MainActivity.this).getDataBase().updateTask(task);
                            }
                        }
                    }
                }, new Utils.OnErrorCallback() {
                    @Override
                    public void perform() {
                        //
                        Log.e("GettingAllTaskFailed", "perform: gETTINGaLLtASKSfAILED");
                    }
                });
            }
        }, new Utils.OnErrorCallback() {
            @Override
            public void perform() {
                Log.e("GetMySelfError", "perform: Error Happened");
            }
        });


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
                        startFeed();
                        return true;
                    case id.four:
                        startFind();
                        return true;
                    case id.five:
                        startMyTasks();
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
                startMyTasks();
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
                startFeed();
                bottomNavigationView.setSelectedItemId(id.three);
                return;
            case 4:
                startFind();
                bottomNavigationView.setSelectedItemId(id.four);
                return;
        }
    }

    private boolean check_land()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void clearAll()
    {
        if (fragmentsNow.getTSF() != null)
            getFragmentManager().beginTransaction().remove(fragmentsNow.getTSF()).commit();
        if (fragmentsNow.getMFF() != null)
            getSupportFragmentManager().beginTransaction().remove(fragmentsNow.getMFF()).commit();
        if (fragmentsNow.getFUF() != null)
            getSupportFragmentManager().beginTransaction().remove(fragmentsNow.getFUF()).commit();
        clearNotAll();
      }

    private void clearNotAll() {
        if (fragmentsNow.getEF() != null)
            getFragmentManager().beginTransaction().remove(fragmentsNow.getEF()).commit();
        if (fragmentsNow.getFF() != null)
            getFragmentManager().beginTransaction().remove(fragmentsNow.getFF()).commit();
        if (fragmentsNow.getMPF() != null)
            getSupportFragmentManager().beginTransaction().remove(fragmentsNow.getMPF()).commit();
        if (fragmentsNow.getTLF() != null)
            getFragmentManager().beginTransaction().remove(fragmentsNow.getTLF()).commit();
    }

    private void startMyTasks()
    {
        clearNotAll();
        if (fragmentsNow.getFUF() != null)
            getSupportFragmentManager().beginTransaction().remove(fragmentsNow.getFUF()).commit();
        if (fragmentsNow.getMFF() != null)
            getSupportFragmentManager().beginTransaction().remove(fragmentsNow.getMFF()).commit();
        fragmentsNow.setNumber_of_fragment_block(1);
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
        clearNotAll();
        if (fragmentsNow.getTSF() != null)
            getFragmentManager().beginTransaction().remove(fragmentsNow.getTSF()).commit();
        if (fragmentsNow.getFUF() != null)
            getSupportFragmentManager().beginTransaction().remove(fragmentsNow.getFUF()).commit();
        if (fragmentsNow.getMFF() == null)
        {
            fragmentsNow.setMyProfile(false, true);
            getSupportFragmentManager().beginTransaction().replace(id.all_screen, fragmentsNow.getMPF()).commit();
        }
        else
        {
            fragmentsNow.setMyProfile(true, false);
            getSupportFragmentManager().beginTransaction().replace(id.all_screen, fragmentsNow.getMFF()).commit();
        }
        fragmentsNow.setCloseAll(false);
    }

    private void startFeed()
    {
        clearAll();
        fragmentsNow.setFeed();
        getFragmentManager().beginTransaction().replace(id.all_screen, fragmentsNow.getFF()).commit();
        fragmentsNow.setCloseAll(false);
    }

    private void startFind()
    {
        clearNotAll();
        if (fragmentsNow.getTSF() != null)
            getFragmentManager().beginTransaction().remove(fragmentsNow.getTSF()).commit();
        if (fragmentsNow.getMFF() != null)
            getSupportFragmentManager().beginTransaction().remove(fragmentsNow.getMFF()).commit();
        fragmentsNow.setFind();
        fragmentsNow.setFUF("search");
        getSupportFragmentManager().beginTransaction().replace(id.all_screen, fragmentsNow.getFUF()).commit();
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
    public void goFriendsListener(String s) {
        fragmentsNow.setMyProfile(true, false);
        fragmentsNow.setMFF(s);
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
        else if (fragmentsNow.getNumber_of_fragment_block() == 2)
        {
            fragmentsNow.setMyProfile(false, true);
            startMyProfile();
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

    public static String getAddToToken(){
        return addToToken;
    }
    public static Retrofit getRetrofit(){
        return retrofit;
    }

    public static TalkingToServerService getTalkingToServerService(){
        return talkingToServerService;
    }
}
