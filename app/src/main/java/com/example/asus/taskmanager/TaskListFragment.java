package com.example.asus.taskmanager;

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
        ((ListView)view.findViewById(R.id.taskList)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                go_next(taskListAdapter.getItem(i).getDataBaseId());
            }
        });
        ((ListView)view.findViewById(R.id.taskList)).setAdapter(taskListAdapter);

        ((Button)view.findViewById(R.id.buttonGoMakeNewTask)).setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           go_next(-1);
                                       }
                                   });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }
}
