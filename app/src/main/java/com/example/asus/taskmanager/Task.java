package com.example.asus.taskmanager;

import java.util.Date;

public class Task
{
    private MyDate time;
    private String description;
    private String name;
    private Long dataBaseId;
    private Long globalDataBaseId;
//    private static int amount;

    public Task() {
        this.time = new MyDate();
        this.description = "";
        this.name = "";
//        this.id = amount++;
    }

    public Task(String name, MyDate time, String description) {
        this.time = time;
        this.description = description;
        this.name = name;
        this.globalDataBaseId = new Long(-1);
    }

    public Task(String name, MyDate time, String description, Long dataBaseId) {
        this.time = time;
        this.description = description;
        this.name = name;
        this.dataBaseId = dataBaseId;
        this.globalDataBaseId = new Long(-1);
    }

    public Task(String name, MyDate time, String description, Long dataBaseId, Long globalDataBaseId) {
        this.time = time;
        this.description = description;
        this.name = name;
        this.dataBaseId = dataBaseId;
        this.globalDataBaseId = globalDataBaseId;
    }

    public String getDescription() {
        return this.description;
    }

    public MyDate getTime() {
        return this.time;
    }

    public String getName() {
        return this.name;
    }

    public long getDataBaseId()
    {
        return dataBaseId;
    }

    public Long getGlobalDataBaseId()
    {
        return this.globalDataBaseId;
    }

    public void setDataBaseId(Long dataBaseId)
    {
        this.dataBaseId = dataBaseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(long time) {
        this.time.setTime(time);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGlobalDataBaseId(Long globalDataBaseId) {
        this.globalDataBaseId = globalDataBaseId;
    }
}
