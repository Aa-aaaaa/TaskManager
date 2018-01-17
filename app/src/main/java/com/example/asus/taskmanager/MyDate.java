package com.example.asus.taskmanager;

import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate extends Date{
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private Date time;

    public MyDate(Date time) {
        this.time = time;
    }

    public MyDate() {
        this.time = new Date();
    }

    public MyDate(long exactTime)
    {
        time = new Date();
        time.setTime(exactTime);
    }

    @Override
    public String toString() {
        return simpleDateFormat.format(time);
    }

    public void setTime(Long time) {
        this.time.setTime(time);
    }

    public boolean setTime(String s)
    {
        try {
            Date timeFromString = new Date(simpleDateFormat.parse(s).getTime());
            time = new Date(timeFromString.getTime());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    @Override
    public long getTime()
    {
        return time.getTime();
    }
}