package com.example.asus.taskmanager.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.taskmanager.FeedPost;
import com.example.asus.taskmanager.MyDate;
import com.example.asus.taskmanager.R;
import com.example.asus.taskmanager.ServerTask;
import com.example.asus.taskmanager.Task;
import com.example.asus.taskmanager.TaskList;
import com.example.asus.taskmanager.User;
import com.example.asus.taskmanager.Utils;
import com.example.asus.taskmanager.adapters.FeedAdapter;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeedFragment extends Fragment {

    private FeedAdapter adapter;

    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.feed_fragment, container, false);

        RecyclerView recycler = view.findViewById(R.id.rvFeed);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new FeedAdapter(getActivity(), new ArrayList<FeedPost>());

        recycler.setAdapter(adapter);

        TaskList.getInstance(getActivity()).getWall(new TaskList.PerformObject() {
            @Override
            public void perform(Object object) {
                final List<FeedPost> feed = new ArrayList<>();
                final int responseSize = ((List<ServerTask>) object).size();
                for (ServerTask item : (List<ServerTask>) object)
                {
                    final FeedPost post = new FeedPost(item);
                    User.getUser(item.getOwner(), new TaskList.PerformObject() {
                                @Override
                                public void perform(Object object2) {
                                    post.setUser((User)object2);
                                    Log.d("USER SET", post.getUser().getLogin());
                                    feed.add(post);
                                    if (feed.size() == responseSize)
                                    {
                                        adapter.setFeedList(feed);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            },
                            new Utils.OnErrorCallback() {
                                @Override
                                public void perform() {

                                }
                            });
                }
            }
        }, new Utils.OnErrorCallback() {
            @Override
            public void perform() {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
