package com.example.asus.taskmanager;

import com.example.asus.taskmanager.fragments.EmptyFragment;
import com.example.asus.taskmanager.fragments.TaskListFragment;
import com.example.asus.taskmanager.fragments.TaskShowFragment;

public class FragmentsNow {
    private static FragmentsNow fragmentsNow;
    private boolean list, show, empty;
    private TaskListFragment taskListFragment;
    private TaskShowFragment taskShowFragment;
    private EmptyFragment emptyFragment;
    private boolean closeAll;

    private FragmentsNow() {
        taskListFragment = null;
        taskShowFragment = null;
        emptyFragment = null;
        list = false;
        show = false;
        empty = false;
        closeAll = false;
    }

    public static FragmentsNow getInstance() {
        if (fragmentsNow == null)
            fragmentsNow = new FragmentsNow();
        return fragmentsNow;
    }

    public void set(boolean list, boolean show, boolean empty)
    {
        //для отображения изменений
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

    public void setTSF(long dataBaseId)
    {
        taskShowFragment = new TaskShowFragment();
        taskShowFragment.setIndex(dataBaseId);
    }

    public boolean isCloseAll() {
        return closeAll;
    }

    public void setCloseAll(boolean closeAll) {
        this.closeAll = closeAll;
    }
}
