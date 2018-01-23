package com.example.asus.taskmanager.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.adapters.TaskListAdapter;

public class TaskListFragment extends Fragment {

    public interface OnTaskListDataListener {
        void onTaskListDataListener(Bundle bundle);
    }

    private OnTaskListDataListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnTaskListDataListener)activity;
    }

    private boolean check_land()
    {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void go_next(long index) {
        Bundle bundle = new Bundle();
        bundle.putLong("index", index);
        mListener.onTaskListDataListener(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final TaskListAdapter taskListAdapter = new TaskListAdapter(TaskList.
                getInstance(getActivity()).getDataBase().getAllNotesFromDataBase());
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        TextView textView = view.findViewById(R.id.taskHead);
        if (TaskList.getInstance(getActivity()).getDataBase().getAllNotesFromDataBase().size() == 0)
            textView.setText(R.string.iWantNewTask);
        else
            textView.setText(R.string.iHaveTask);
        ((ListView)view.findViewById(R.id.taskList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                go_next(taskListAdapter.getItem(i).getDataBaseId());
            }
        });
        ((ListView)view.findViewById(R.id.taskList)).setAdapter(taskListAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
