package com.example.asus.taskmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.taskmanager.FoneService;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.ServerTask;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.adapters.FeedAdapter;
import com.example.asus.taskmanager.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.asus.taskmanager.FoneService.getWall;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);


        //TODO: uncomment it
        /*

        RecyclerView recycler = findViewById(R.id.rvFeed);
        recycler.setLayoutManager(new LinearLayoutManager(FeedActivity.this));


        List<ServerTask> serverTasks = FoneService.getWall(FeedActivity.this);

        FeedAdapter adapter;
        adapter = new FeedAdapter(FeedActivity.this, serverTasks);

        recycler.setAdapter(adapter);*/
    }
}
