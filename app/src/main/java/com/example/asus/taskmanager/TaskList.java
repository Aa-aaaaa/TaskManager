package com.example.asus.taskmanager;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class TaskList {
    private static TaskList taskList;

    public static TaskList getInstance() {
        if (taskList == null)
            taskList = new TaskList();
        return taskList;
    }

    public void addTask(Task task)
    {
        MainActivity.getDataBase().addTask(task);
    }

    public Task getTask(long dataBaseId)
    {
        return MainActivity.getDataBase().getTaskById(dataBaseId);
    }

    public void deleteTask(long dataBaseId) {
        MainActivity.getDataBase().deleteTask(dataBaseId);
    }

    public void deleteTask(Task task) {
        MainActivity.getDataBase().deleteTask(task.getDataBaseId());
    }

    public void changeTask(Task newTask)
    {
        MainActivity.getDataBase().updateTask(newTask);
    }
}
