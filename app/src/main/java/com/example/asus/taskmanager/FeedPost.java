package com.example.asus.taskmanager;

import com.example.asus.taskmanager.adapters.FeedAdapter;
import com.example.asus.taskmanager.fragments.FeedFragment;

/**
 * Created by serge on 24.01.2018.
 */

public class FeedPost
{
    private static Long amountOfPosts;
    private ServerTask serverTask;
    private User user;

    public FeedPost(ServerTask serverTask)
    {
        this.serverTask = serverTask;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Long getAmountOfPosts() {
        return amountOfPosts;
    }

    public ServerTask getServerTask() {
        return serverTask;
    }

    public User getUser() {
        return user;
    }
}
