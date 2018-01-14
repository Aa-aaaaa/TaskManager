package com.example.asus.taskmanager;

import java.util.Date;

public class Task {
    private Date time;
    private String description;
    private String name;
    private int id;

    public Task() {
        this.time = new Date();
        this.description = "";
        this.name = "";
        this.id = 0;
    }

    public Task(String name, Date time, String description, int id) {
        this.time = time;
        this.description = description;
        this.name = name;
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getTime() {
        return this.time;
    }

    public String getName() {
        return this.name;
    }

    public int getId()
    {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }
}
