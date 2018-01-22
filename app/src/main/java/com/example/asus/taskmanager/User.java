package com.example.asus.taskmanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.taskmanager.activities.MainActivity;

public class User {
    private String username;
    private String name;
    private String lastName;
    private String password;
    private String token;

    public User()
    {
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public User(String username, String name, String lastName, String password)
    {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setInfoFromSP(Context context) // Gets info from Shared Preferences and adds it to user
    {
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);
    }

    public void succesfullLogin(Context context, String token) // Adds token to Share Preferences, sets this.token
    {
        this.token = token;
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("token", token).commit();
    }

    public void logout(Context context)
    {
        this.token = null;
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("token").commit();
    }
}
