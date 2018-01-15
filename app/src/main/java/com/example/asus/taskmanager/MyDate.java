package com.example.asus.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate extends Date{
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private Date time;
    private String string;

    public MyDate(Date time) {
        this.time = time;
        timeToString(time);
    }

    public MyDate() {
        this.time = new Date();
        timeToString(time);
    }

    public boolean stringToTime(String s) {
        Date t = new Date(), timeNow, timeFromString, ans = new Date(0);
        String stringTimeNow = simpleDateFormat.format(t);
        try {
            timeNow = simpleDateFormat.parse(stringTimeNow);
            timeFromString = simpleDateFormat.parse(s);
            if (timeNow.compareTo(timeFromString) >= 0
                    || (!s.equals(simpleDateFormat.format(timeFromString))))
                return false;
            this.time =  new Date(t.getTime() + timeFromString.getTime() - timeNow.getTime());
            this.string = s;
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public void timeToString(Date time)
    {
        this.string = simpleDateFormat.format(time);
        this.time = time;
    }

    public Date myGetTime(){
        return time;
    }

    public void setTime(Date time) {
        this.timeToString(time);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.stringToTime(string);
    }
}
