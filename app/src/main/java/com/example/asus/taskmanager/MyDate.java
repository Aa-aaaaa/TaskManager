package com.example.asus.taskmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate extends Date{
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private final SimpleDateFormat DBDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat DBTimeFormat = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat DBFullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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

    public Date myGetTime(){
        return time;
    }

    public void setTime(Date time) {
        this.setTime(time.getTime());
    }

    public String getString() {
        return simpleDateFormat.format(time);
    }

    public void setString(String string) {
        this.setTime(string);
    }

    public String getStringForDB()
    {
        return DBDateFormat.format(time) + "T" + DBTimeFormat.format(time);
    }

    public boolean setDateFromDB(String s)
    {
        s.replace('T', ' ');
        s.replace("Z", "");
        Date t = new Date(), timeNow, timeFromString, ans = new Date(0);
        String stringTimeNow = DBFullFormat.format(t);
        try {
            timeNow = DBFullFormat.parse(stringTimeNow);
            timeFromString = DBFullFormat.parse(s);
            if (timeNow.compareTo(timeFromString) >= 0
                    || (!s.equals(DBFullFormat.format(timeFromString))))
                return false;
            this.time =  new Date(t.getTime() + timeFromString.getTime() - timeNow.getTime());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}