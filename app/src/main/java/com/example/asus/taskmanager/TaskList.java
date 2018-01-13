package com.example.asus.taskmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class TaskList {
    private ArrayList<Task> arrayList;

    public TaskList() {
        this.arrayList = new ArrayList<>();
    }

    public void addTask(Task task)
    {
        this.arrayList.add(task);
        Collections.sort(arrayList, new Comparator<Task>() {
            public int compare(Task a, Task b)
            {
                return (a.getTime().compareTo(b.getTime()));
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
        this.arrayList.remove(index);
        Collections.sort(arrayList, new Comparator<Task>() {
            public int compare(Task a, Task b)
            {
                return (a.getTime().compareTo(b.getTime()));
            }
        });
    }

    public void changeTask(int index, Task newTask)
    {
        deleteTask(index);
        addTask(newTask);
    }

}
