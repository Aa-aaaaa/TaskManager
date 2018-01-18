package com.example.asus.taskmanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task
{
    private MyDate time;
    private String description;
    private String name;
    private Long dataBaseId;
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
    }

    public Task(String name, MyDate time, String description, Long dataBaseId) {
        this.time = time;
        this.description = description;
        this.name = name;
        this.dataBaseId = dataBaseId;
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

    public Long getDataBaseId()
    {
        return dataBaseId;
    }

    public void setDataBaseId(Long dataBaseId)
    {
        this.dataBaseId = dataBaseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(Date time) {
        this.time.setTime(time);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
