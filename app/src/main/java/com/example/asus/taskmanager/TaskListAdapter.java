package com.example.asus.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskListAdapter extends BaseAdapter
{
    ArrayList <Task> taskList;
    TaskListAdapter(ArrayList<Task> taskList)
    {
        this.taskList = taskList;
        Collections.sort(this.taskList, new Comparator<Task>() {
            public int compare(Task a, Task b)
            {
                return (a.getTime().myGetTime().compareTo(b.getTime().myGetTime()));
            }
        });
    }
    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Task getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public void notifyDataSetChanged() {
        taskList = TaskList.getInstance(null).getDataBase().getAllNotesFromDataBase();
        Collections.sort(taskList, new Comparator<Task>() {
            public int compare(Task a, Task b)
            {
                return (a.getTime().myGetTime().compareTo(b.getTime().myGetTime()));
            }
        });
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_task_list, viewGroup, false);
        ((TextView)view.findViewById(R.id.taskName)).
                setText(taskList.get(i).getName());
        ((TextView)view.findViewById(R.id.taskTime)).
                setText(taskList.get(i).getTime().toString());
        return view;
    }
}
