package com.example.asus.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter
{
    TasksList tasksList;
    TaskListAdapter(TasksList tasksList)
    {
        this.tasksList = tasksList;
    }
    @Override
    public int getCount() {
        return tasksList.getSize();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_task_view, viewGroup, false);
        ((TextView)view.findViewById(R.id.taskName)).setText(tasksList.getTask(i).getName());
        ((TextView)view.findViewById(R.id.taskDescription)).setText(tasksList.getTask(i).getDescription());
        return view;
    }
}
