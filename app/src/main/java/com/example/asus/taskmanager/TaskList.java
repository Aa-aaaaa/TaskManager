package com.example.asus.taskmanager;

import android.content.Context;
import android.util.Log;

public class TaskList {
    private static TaskList taskList;
    private static DataBase dataBase;
    private static Context thisContext;

    public static TaskList getInstance(Context context) {
        if (taskList == null) {
            taskList = new TaskList();
            makeDB(context);
            thisContext = context;
        }
        return taskList;
    }

    public void addTask(Task task)
    {
        this.getDataBase().addTask(task);
        //Log.d("KEK");
        FoneService.addTask(task, thisContext);
    }

    public Task getTask(long dataBaseId)
    {
        return this.getDataBase().getTaskById(dataBaseId);
    }

    public void deleteTask(long dataBaseId) {
        Long globalId = this.getDataBase().getTaskById(dataBaseId).getGlobalDataBaseId();
        this.getDataBase().deleteTask(dataBaseId);
        if (globalId != -1)
            FoneService.deleteTask(globalId, thisContext);
    }

    public void deleteTask(Task task) {
        this.getDataBase().deleteTask(task.getDataBaseId());
    }

    public void changeTask(Task newTask)
    {
        this.getDataBase().updateTask(newTask);
        FoneService.updateTask(newTask, thisContext);
    }

    private static void makeDB(Context context)
    {
        dataBase = new DataBase(context);
    }

    public DataBase getDataBase()
    {
        return this.dataBase;
    }

    public void clearDataBase()
    {
        dataBase.onUpgrade(null, 0, 0);
    }
}
