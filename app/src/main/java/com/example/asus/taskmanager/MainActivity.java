package com.example.asus.taskmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
    private FragmentsNow fragmentsNow = FragmentsNow.getInstance();

    private boolean check_land()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void startAll()
    {
/*        //для возвращения назад
        if (check_land())
        {
            fragmentsNow.set(true, false, true);
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getEF()).commit();
            getFragmentManager().beginTransaction().replace(id.list, fragmentsNow.getTLF()).commit();
            getFragmentManager().beginTransaction().remove(fragmentsNow.getEF()).addToBackStack(null).commit();
        }
        else
        {
            fragmentsNow.set(true, false, false);
            getFragmentManager().beginTransaction().replace(id.other, fragmentsNow.getTLF()).commit();
            getFragmentManager().beginTransaction().remove(fragmentsNow.getTLF()).addToBackStack(null).commit();
        }
*/
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
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
    }
}
