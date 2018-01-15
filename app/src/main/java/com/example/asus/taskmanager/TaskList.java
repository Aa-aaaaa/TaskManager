package com.example.asus.taskmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class TaskList {
    private static TaskList taskList;
    private ArrayList<Task> arrayList;

    private TaskList() {
        this.arrayList = new ArrayList<>();
    }

    public static TaskList getInstance() {
        if (taskList == null)
            taskList = new TaskList();
        return taskList;
    }

    public void addTask(Task task)
    {
        MainActivity.getDataBase().addTask(task);
        this.arrayList.add(task);
        Collections.sort(arrayList, new Comparator<Task>() {
            public int compare(Task a, Task b)
            {
                return (a.getTime().myGetTime().compareTo(b.getTime().myGetTime()));
            }
        });
    }

    public Task getTask(int index)
    {
        return arrayList.get(index);
    }

    public int getSize()
    {
        return arrayList.size();
    }

    public void deleteTask(int index) {
        MainActivity.getDataBase().deleteTask(arrayList.get(index));
        this.arrayList.remove(index);
        Collections.sort(arrayList, new Comparator<Task>() {
            public int compare(Task a, Task b)
            {
                return (a.getTime().myGetTime().compareTo(b.getTime().myGetTime()));
            }
        });
    }

    public void changeTask(int index, Task newTask)
    {
        MainActivity.getDataBase().updateTask(arrayList.get(index), newTask);
        deleteTask(index);
        addTask(newTask);
    }
}
