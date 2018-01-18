package com.example.asus.taskmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class TaskShowFragment extends Fragment  {

    private int index;

    public TaskShowFragment() {
        index = -1;
    }

    public interface OnTaskShowDataListener {
        void onTaskShowDataListener();
    }

    private OnTaskShowDataListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnTaskShowDataListener) activity;
    }

    private void go_next()
    {
        mListener.onTaskShowDataListener();
    }

    private boolean checkData(int index, String name, String time, String description){
        MyDate myDate = new MyDate();
        if (!myDate.stringToTime(time)) {
            Toast.makeText(getActivity(), "Bad date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (name.length() > 50 || name.length() < 1) {
            Toast.makeText(getActivity(), "Bad name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (index < 0)
            TaskList.getInstance().addTask(new Task(name, myDate, description));
        else
            TaskList.getInstance().changeTask(index, new Task(name, myDate, description));
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_fragment, container, false);

        final TaskList taskList = TaskList.getInstance();
        final TextView name = (TextView)view.findViewById(R.id.name);
        final TextView time = (TextView)view.findViewById(R.id.time);
        final TextView description = (TextView)view.findViewById(R.id.description);
        if (index >= 0) {
            name.setText(taskList.getTask(index).getName());
            time.setText(taskList.getTask(index).getTime().getString());
            description.setText(taskList.getTask(index).getDescription());
        }
        else
        {
            name.setText("");
            time.setText((new MyDate()).getString());
            description.setText("");
        }

        ((Button)view.findViewById(R.id.buttonDeleteTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index >= 0)
                    taskList.deleteTask(index);
                go_next();
            }
        });

        ((Button)view.findViewById(R.id.buttonSaveChanges)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData(index, name.getText().toString(), time.getText().toString(), description.getText().toString()))
                    go_next();
            }
        });

        return view;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setRetainInstance(true);
    }

}
