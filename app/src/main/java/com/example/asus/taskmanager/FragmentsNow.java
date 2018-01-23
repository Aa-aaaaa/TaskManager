package com.example.asus.taskmanager;

import com.example.asus.taskmanager.activities.FeedActivity;
import com.example.asus.taskmanager.fragments.FeedFragment;
import com.example.asus.taskmanager.fragments.MyProfileFragment;
import com.example.asus.taskmanager.fragments.EmptyFragment;
import com.example.asus.taskmanager.fragments.TaskListFragment;
import com.example.asus.taskmanager.fragments.TaskShowFragment;

public class FragmentsNow {
    private static FragmentsNow fragmentsNow;
    private boolean list, show, empty, profile, feed;
    private TaskListFragment taskListFragment;
    private TaskShowFragment taskShowFragment;
    private EmptyFragment emptyFragment;
    private MyProfileFragment myProfileFragment;
    private FeedFragment feedFragment;
    private boolean closeAll;
    private int number_of_fragment_block;

    private FragmentsNow() {
        taskListFragment = null;
        taskShowFragment = null;
        emptyFragment = null;
        myProfileFragment = null;
        feedFragment = null;
        list = false;
        show = false;
        empty = false;
        profile = false;
        feed = false;
        number_of_fragment_block = 1;
        closeAll = false;
    }

    public static FragmentsNow getInstance() {
        if (fragmentsNow == null)
            fragmentsNow = new FragmentsNow();
        return fragmentsNow;
    }

    public void setMyTasks(boolean list, boolean show, boolean empty)
    {
        if (list)
            this.taskListFragment = new TaskListFragment();
        else
            this.taskListFragment = null;
        if (show)
            this.taskShowFragment = (this.taskShowFragment == null) ? new TaskShowFragment() : this.taskShowFragment;
        else
            this.taskShowFragment = null;
        if (empty)
            this.emptyFragment = (this.emptyFragment == null) ? new EmptyFragment() : this.emptyFragment;
        else
            this.emptyFragment = null;
        this.list = list;
        this.show = show;
        this.empty = empty;
        this.feed = false;
        this.profile = false;
        this.feedFragment = null;
        this.myProfileFragment = null;
        this.number_of_fragment_block = 1;
    }

    public void setMyProfile() {
        this.list = false;
        this.taskListFragment = null;
        this.show = false;
        this.taskShowFragment = null;
        this.empty = false;
        this.emptyFragment = null;
        this.feed = false;
        this.feedFragment = null;
        this.profile = true;
        this.myProfileFragment = new MyProfileFragment();
        this.number_of_fragment_block = 2;
    }

    public void setFeed() {
        this.list = false;
        this.taskListFragment = null;
        this.show = false;
        this.taskShowFragment = null;
        this.empty = false;
        this.emptyFragment = null;
        this.profile = false;
        this.myProfileFragment = null;
        this.feed = true;
        this.feedFragment = new FeedFragment();
        this.number_of_fragment_block = 3;
    }

    public void setFind() {

        this.number_of_fragment_block = 4;
    }

    public void clearAll() {

    }

    public TaskShowFragment getTSF()
    {
        return taskShowFragment;
    }

    public TaskListFragment getTLF()
    {
        return taskListFragment;
    }

    public EmptyFragment getEF()
    {
        return emptyFragment;
    }

    public MyProfileFragment getMPF() {
        return myProfileFragment;
    }

    public FeedFragment getFF() {
        return feedFragment;
    }

    public void setTSF(long dataBaseId)
    {
        taskShowFragment = new TaskShowFragment();
        taskShowFragment.setIndex(dataBaseId);
    }

    public int getNumber_of_fragment_block()
    {
        return this.number_of_fragment_block;
    }

    public boolean isCloseAll() {
        return closeAll;
    }

    public void setCloseAll(boolean closeAll) {
        this.closeAll = closeAll;
    }

    public void setNumber_of_fragment_block(int number_of_fragment_block) {
        this.number_of_fragment_block = number_of_fragment_block;
    }
}
