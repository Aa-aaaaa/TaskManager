package com.example.asus.taskmanager;

import java.util.Date;

public class Tasks {
    private int Number;
    private Date Time;
    private String Description;

    Tasks() {
        Number = 0;
        Time = new Date();
        Description = "";
    }

    public Tasks(int number, Date time, String description) {
        Number = number;
        Time = time;
        Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public int getNumber() {
        return Number;
    }

    public Date getTime() {
        return Time;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public void setTime(Date time) {
        Time = time;
    }
}
