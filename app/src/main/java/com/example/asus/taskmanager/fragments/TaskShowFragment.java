package com.example.asus.taskmanager.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asus.taskmanager.MyDate;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.Task;
import com.example.asus.taskmanager.TaskList;

import java.util.Calendar;

public class TaskShowFragment extends Fragment  {

    private long index;

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

    private boolean checkData(long index, String name, String time, String description) {
        MyDate myDate = new MyDate();
        if (!myDate.setTime(time) || myDate.getTime() < Calendar.getInstance().getTimeInMillis()) {
            Toast.makeText(getActivity(), "Bad date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.length() > 50 || name.length() < 1) {
            Toast.makeText(getActivity(), "Bad name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (index < 0)
            TaskList.getInstance(getActivity()).addTask(new Task(name, myDate, description));
        else
            TaskList.getInstance(getActivity()).changeTask(new Task(name, myDate, description, (long)index,
                    TaskList.getInstance(getActivity()).getDataBase().getTaskById((long)index).getGlobalDataBaseId()));
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_fragment, container, false);

        final TextView name = view.findViewById(R.id.name);
        final TextView date = view.findViewById(R.id.tvDateTime);
        final TextView description = view.findViewById(R.id.description);

        date.setOnClickListener(new View.OnClickListener() {
            Calendar c = Calendar.getInstance();
            TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    c.set(Calendar.MINUTE, minuteOfHour);
                    date.setText(new MyDate(c.getTimeInMillis()).toString());
                }
            }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.HOUR), true);
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth) {
                        c.set(Calendar.YEAR, year1);
                        c.set(Calendar.MONTH, month1);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        timePicker.show();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        if (index > 0) {
            name.setText(TaskList.getInstance(getActivity()).getTask(index).getName());
            date.setText(TaskList.getInstance(getActivity()).getTask(index).getTime().getString());
            description.setText(TaskList.getInstance(getActivity()).getTask(index).getDescription());
        }
        else
        {
            name.setText("");
            date.setText((new MyDate()).getString());
            description.setText("");
        }

        ((Button)view.findViewById(R.id.buttonDeleteTask)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index > 0)
                    TaskList.getInstance(getActivity()).deleteTask(index);
                go_next();
            }
        });

        ((Button)view.findViewById(R.id.buttonSaveChanges)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData(index, name.getText().toString(), date.getText().toString(), description.getText().toString()))
                    go_next();
            }
        });

        return view;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

}
