package com.example.asus.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter
{
    TaskList taskList;
    TaskListAdapter(TaskList taskList)
    {
        this.taskList = taskList;
    }
    @Override
    public int getCount() {
        return taskList.getSize();
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_task_list, viewGroup, false);
        ((TextView)view.findViewById(R.id.taskName)).
                setText(taskList.getTask(i).getName());
        ((TextView)view.findViewById(R.id.taskDescription)).
                setText(taskList.getTask(i).getTime().getString());
        return view;
    }
}
