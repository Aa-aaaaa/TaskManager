package com.example.asus.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    static private TaskListAdapter taskListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final TaskList taskList = TaskList.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskListAdapter = new TaskListAdapter(taskList);

        ((ListView)findViewById(R.id.taskList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TaskShowActivity.class);
                intent.putExtra("name", taskList.getTask(i).getName());
                intent.putExtra("time", taskList.getTask(i).getTime().getString());
                intent.putExtra("description", taskList.getTask(i).getDescription());
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

/*    public static TaskList getTaskList() {
        return taskList;
    }
*/
    public static TaskListAdapter getTaskListAdapter() {
        return taskListAdapter;
    }
}
