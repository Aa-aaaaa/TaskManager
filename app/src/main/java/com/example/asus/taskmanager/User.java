package com.example.asus.taskmanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.taskmanager.activities.MainActivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Maxim on 21.01.2018.
 */

public class User {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("email")
    @Expose
    private String email;
    private String password;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("subscribed_on")
    @Expose
    private List<Long> subscribedOn;
    private String token;
    public User(String login, String password, String firstName, String lastName)
    {
        this.email = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public User(String login, String password)
    {
        this.email = login;
        this.password = password;
    }

    public User()
    {
        this.email = null;
        this.token = null;
        this.lastName = null;
        this.firstName = null;
        this.password = null;
        this.subscribedOn = null;
    }

    public long getId()
    {
        return this.id;
    };
    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return this.email;
    }
    public void setLogin(String login) {
        this.email = login;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Long> getSubscribedOn() {
        return this.subscribedOn;
    }
    public void setSubscribedOn(List<Long> subscribedOn) {
        this.subscribedOn = subscribedOn;
    }

    public String getToken(){
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(User user)
    {
        this.email = user.getLogin();
        this.id = user.getId();
        this.token = user.getToken();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.password = user.getPassword();
        this.subscribedOn = user.getSubscribedOn();
    }

    @Override
    public String toString() {
        return id + " " + email + " " + password + " " + firstName + " " + lastName + " " + token;
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
