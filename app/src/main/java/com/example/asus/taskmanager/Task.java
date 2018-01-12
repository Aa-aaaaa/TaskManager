package com.example.asus.taskmanager;

import java.util.Date;

public class Task {
    private Date time;
    private String description;
    private String name;

    public Task() {
        this.time = new Date();
        this.description = "";
        this.name = "";
    }

    public Task(Date time, String description, String name) {
        this.time = time;
        this.description = description;
        this.name = name;
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
