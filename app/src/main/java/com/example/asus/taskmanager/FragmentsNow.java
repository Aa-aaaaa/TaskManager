package com.example.asus.taskmanager;

import com.example.asus.taskmanager.fragments.FeedFragment;
import com.example.asus.taskmanager.fragments.MyProfileFragment;
import com.example.asus.taskmanager.fragments.EmptyFragment;
import com.example.asus.taskmanager.fragments.TaskListFragment;
import com.example.asus.taskmanager.fragments.TaskShowFragment;
import com.example.asus.taskmanager.fragments.UserListFragment;

public class FragmentsNow {
    private static FragmentsNow fragmentsNow;
    private TaskListFragment taskListFragment;
    private TaskShowFragment taskShowFragment;
    private EmptyFragment emptyFragment;
    private MyProfileFragment myProfileFragment;
    private FeedFragment feedFragment;
    private UserListFragment friendsFragment;
    private UserListFragment findUserFragment;
    private boolean closeAll;
    private int number_of_fragment_block;

    private FragmentsNow() {
        clearAll();
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
        this.feedFragment = null;
        this.myProfileFragment = null;
        this.friendsFragment = null;
        this.findUserFragment = null;
        this.number_of_fragment_block = 1;
    }

    public void setMyProfile(boolean friends, boolean profile) {
        this.taskListFragment = null;
        this.taskShowFragment = null;
        this.emptyFragment = null;
        this.feedFragment = null;
        this.findUserFragment = null;
        if (friends)
            this.friendsFragment = (this.friendsFragment == null) ? new UserListFragment() : this.friendsFragment;
        else
            this.friendsFragment = null;
        if (profile)
            this.myProfileFragment = new MyProfileFragment();
        else
            this.myProfileFragment = null;
        this.number_of_fragment_block = 2;
    }

    public void setFeed() {
        clearAll();
        this.feedFragment = new FeedFragment();
        this.number_of_fragment_block = 3;
    }

    public void setFind() {
        clearAll();
        this.findUserFragment = UserListFragment.newInstance("following");
        this.number_of_fragment_block = 4;
    }


    public void clearAll() {
        this.taskListFragment = null;
        this.taskShowFragment = null;
        this.emptyFragment = null;
        this.myProfileFragment = null;
        this.friendsFragment = null;
        this.feedFragment = null;
        this.findUserFragment = null;
    }

    public UserListFragment getFUF() {
        return findUserFragment;
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

    public UserListFragment getMFF() {
        return friendsFragment;
    }

    public void setTSF(long dataBaseId)
    {
        taskShowFragment = new TaskShowFragment();
        taskShowFragment.setIndex(dataBaseId);
    }

    public void setMFF(String s) {
        friendsFragment = UserListFragment.newInstance(s);
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
