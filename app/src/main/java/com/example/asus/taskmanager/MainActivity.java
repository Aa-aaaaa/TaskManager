package com.example.asus.taskmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    static private TaskListAdapter taskListAdapter;
    static private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final TaskList taskList = TaskList.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new DataBase(MainActivity.this);
        //dataBase.deleteAll();
        taskListAdapter = new TaskListAdapter(dataBase.getAllNotesFromDataBase());

        ((ListView)findViewById(R.id.taskList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TaskShowActivity.class);
                Log.d("neBl", Integer.toString(taskListAdapter.getCount()));
                Log.d("neBl", "" + taskListAdapter.getItem(i).getDataBaseId());
                //Log.d("BLYAT", taskListAdapter.getItem(i).getDataBaseId().toString());
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

    public static DataBase getDataBase()
    {
        return dataBase;
    }
}
