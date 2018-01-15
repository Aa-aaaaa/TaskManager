package com.example.asus.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private MyDate time;
    private String description;
    private String name;
    private int id;

    public Task() {
        this.time = new MyDate();
        this.description = "";
        this.name = "";
        this.id = 0;
    }

    public Task(String name, MyDate time, String description, int id) {
        this.time = time;
        this.description = description;
        this.name = name;
        this.id = id;
    }

    public boolean stringToTime(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date t = new Date(), timeNow, timeFromString;
        String stringTimeNow = simpleDateFormat.format(time), s2 = "";
        try {
            timeNow = simpleDateFormat.parse(stringTimeNow);
            timeFromString = simpleDateFormat.parse(s);
            if (timeNow.compareTo(timeFromString) >= 0
                    || (simpleDateFormat.format(timeFromString) != s))
                return false;
            time.setTime(t.getTime() + timeFromString.getTime() - timeNow.getTime());
        }
        catch (Exception e) {
            return false;
        };
        return true;
    }

    public String timeToString()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return simpleDateFormat.format(time);
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

    public int getId()
    {
        return this.id;
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
