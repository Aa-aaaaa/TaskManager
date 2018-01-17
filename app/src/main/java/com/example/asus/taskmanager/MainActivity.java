package com.example.asus.taskmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;

import static com.example.asus.taskmanager.R.*;
import static com.example.asus.taskmanager.R.id.*;

public class MainActivity extends AppCompatActivity implements TaskListFragment.OnTaskListDataListener, TaskShowFragment.OnTaskShowDataListener
{
    final TaskListAdapter taskListAdapter = new TaskListAdapter(TaskList.getInstance());
    TaskListFragment taskListFragment = new TaskListFragment();
    TaskShowFragment taskShowFragment = new TaskShowFragment();
    EmptyFragment emptyFragment = new EmptyFragment();
    private boolean check_land()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        if (check_land())
            getFragmentManager().beginTransaction().replace(id.other, emptyFragment).commit();
        getFragmentManager().beginTransaction().replace(id.list, taskListFragment).commit();
    }

    @Override
    public void onTaskListDataListener(Bundle bundle) {
        if (taskShowFragment.isAdded())
        {
            getFragmentManager().beginTransaction().remove(taskShowFragment).commit();
            if (check_land())
                getFragmentManager().beginTransaction().add(R.id.other, emptyFragment).commit();
        }
        taskShowFragment = new TaskShowFragment();
        taskShowFragment.setIndex(bundle.getInt("index"));
        if (check_land())
            getFragmentManager().beginTransaction().replace(R.id.other, taskShowFragment).addToBackStack(null).commit();
        else
            getFragmentManager().beginTransaction().replace(id.list, taskShowFragment).addToBackStack(null).commit();
    }

    @Override
    public void onTaskShowDataListener() { 
        taskListAdapter.notifyDataSetChanged();
        if (check_land())
            getFragmentManager().beginTransaction().replace(id.other, emptyFragment).commit();
        taskListFragment = new TaskListFragment();
        getFragmentManager().beginTransaction().replace(id.list, taskListFragment).commit();
    }

/*    @Override
    protected void onResume() {
        taskListAdapter.notifyDataSetChanged();
        super.onResume();
    }
*/
}
