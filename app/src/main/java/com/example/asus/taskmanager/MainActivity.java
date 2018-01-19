package com.example.asus.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    static private TaskListAdapter taskListAdapter;
    //static private DataBase dataBase;
    //static private FoneService foneService = new FoneService();
    //static private String token = "503712e87969da1ab86c6eafa9b0e6d1ac81441b";
    static private String token = null;
    static private String serverName = "http://siriustaskmanager.herokuapp.com/api/";
    static private String username = "admin@admin.com";
    static private String password = "123456AB";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final TaskList taskList = TaskList.getInstance(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoneService.getToken(username, password, MainActivity.this);
        //dataBase = new DataBase(MainActivity.this);
        //dataBase.deleteAll();
        //TaskList.getInstance(MainActivity.this).clearDataBase();
        //TaskList.getInstance(MainActivity.this).getDataBase().addAllToGlobalDB();
        //MyDate myDate = new MyDate();
        //myDate.stringToTime("28.02.2019 12:00");
        //TaskList.getInstance(MainActivity.this).addTask(new Task("Check loging",  myDate, "It will be doing until it works"));
        //token = FoneService.getToken("admin@admin.com", "123456AB", MainActivity.this);
        //FoneService.registration("megamax143.13@gmail.com", "123456Aa", "Maksim", "Nyashin", MainActivity.this);
        taskListAdapter = new TaskListAdapter(TaskList.getInstance(MainActivity.this).getDataBase().getAllNotesFromDataBase());

        ((ListView)findViewById(R.id.taskList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TaskShowActivity.class);
                Log.d("neBl", Integer.toString(taskListAdapter.getCount()));
                Log.d("neBl", "" + taskListAdapter.getItem(i).getDataBaseId());
                //Log.d("BiLET", taskListAdapter.getItem(i).getDataBaseId().toString());
                intent.putExtra("dataBaseId", taskListAdapter.getItem(i).getDataBaseId());
                startActivity(intent);
            }
        });
        ((ListView)findViewById(R.id.taskList)).setAdapter(taskListAdapter);

        ((Button)findViewById(R.id.buttonGoMakeNewTask)).
                setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        taskListAdapter.notifyDataSetChanged();
        super.onResume();
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

    public static String getServerName()
    {
        return serverName;
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
}
