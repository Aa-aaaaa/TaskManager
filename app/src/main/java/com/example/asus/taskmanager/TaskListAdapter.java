package com.example.asus.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter
{
    @Override
    public int getCount() {
        return 10;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_task_view, viewGroup, false);
        ((TextView)view.findViewById(R.id.taskName)).setText("Name " + i/*Марина допишет*/);
        ((TextView)view.findViewById(R.id.taskDescription)).setText("Desc " + i + " lol!");
        return view;
    }
}
