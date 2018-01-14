package com.example.asus.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private MyDate time;
    private String description;
    private String name;

    public Task() {
        this.time = new MyDate();
        this.time.setTime(new Date());
        this.description = "";
        this.name = "";
    }

    public Task(String name, Date time, String description) {
        this.time = new MyDate();
        this.time.timeToString(time);
        this.description = description;
        this.name = name;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(Date time) {
        this.time.setTime(time);
    }

    public void setName(String name) {
        this.name = name;
    }
}
