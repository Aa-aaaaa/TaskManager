package com.example.asus.taskmanager.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.taskmanager.FoneService;
import com.example.asus.taskmanager.MyDate;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.ServerTask;
import com.example.asus.taskmanager.Task;
import com.example.asus.taskmanager.activities.FeedActivity;
import com.example.asus.taskmanager.adapters.FeedAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeedFragment extends Fragment {
    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        RecyclerView recycler = view.findViewById(R.id.rvFeed);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        List<ServerTask> serverTasks = new ArrayList<>();/* = FoneService.getWall(getActivity())*/;
        ServerTask task = new ServerTask();
        task.setTask(new Task("Soka", new MyDate(Calendar.getInstance().getTimeInMillis()), "Okau"));
        for (int i = 0; i < 5; i++)
            serverTasks.add(task);


        FeedAdapter adapter;
        adapter = new FeedAdapter(getActivity(), serverTasks);

        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
