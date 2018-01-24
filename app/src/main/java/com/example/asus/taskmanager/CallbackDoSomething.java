package com.example.asus.taskmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.taskmanager.activities.LoginActivity;
import com.example.asus.taskmanager.activities.MainActivity;

import java.util.List;

/**
 * Created by Maxim on 22.01.2018.
 */

public class CallbackDoSomething {
    private static String TAG = "CallbackDoSomething";
    public static void callbackGetToken(TalkingToServerService.Token token, Context context)
    {
        if (token != null) {
            MainActivity.getUser().succesfullLogin(context, token.getToken());

            context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            Log.d("Token", "getToken: " + token.getToken());
            FoneService.getMyself(context);
        }
        else
        {
            Toast.makeText(context.getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
            Log.d("LOGIN", "Can't sign in");
        }
    }

    public  static void callbackRegistration(TalkingToServerService.RegisterUser registerUser, Context context, User user)
    {
        if (registerUser != null)
        {
            MainActivity.getUser().setUser(user);
            Log.d(TAG, "Registration finished succesfully");
            context.startActivity(new Intent(context, LoginActivity.class));
        }
        else
        {

            Log.e("JSON", "Registration faild");
            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackAddTask(ServerTask serverTask, Context context, Task task)
    {
        if (serverTask != null)
        {
            task.setGlobalDataBaseId(serverTask.getId());
            Log.d(TAG, "callbackAddTask: " + task.getGlobalDataBaseId());
            Log.d(TAG, "callbackAddTask: "  + task.getDataBaseId() + " " + task.getName() + " " + task.getTime().getString() + " " + task.getDescription());
            TaskList.getInstance(context).getDataBase().updateTask(task);
        }
        else
        {
            Toast.makeText(context, "Adding Task Failed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "callbackAddTask: failed");
        }
    }

    public static void callbackUpdateTask(ServerTask serverTask, Context context, Task task)
    {
        if (serverTask != null)
        {
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Updating Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackGetTask(ServerTask serverTask, Context context, Task task)
    {
        if (serverTask != null)
        {
            task = serverTask.getTask();
            Toast.makeText(context, "Task Got Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Getting task Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackDeleteTask(boolean bool, Context context)
    {
        if (bool)
        {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackSubscribe(boolean bool, Context context)
    {
        if (bool)
        {
            Toast.makeText(context, "Successfully Subscribed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Subscription Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackUnsubscribe(boolean bool, Context context)
    {
        if (bool)
        {
            Toast.makeText(context, "Successfully Unsubscribed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Unsubscription Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackLike(boolean bool, Context context)
    {
        if (bool)
        {
            Toast.makeText(context, "Successfully Liked", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Like Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackUnike(boolean bool, Context context)
    {
        if (bool)
        {
            Toast.makeText(context, "Successfully Unliked", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Unlike Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackGetAllTasks(List<ServerTask> serverTaskList, Context context, List<ServerTask> list)
    {
        if (serverTaskList != null)
        {
            list = serverTaskList;
            Toast.makeText(context, "All Task Got Successfully", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "callbackGetAllTasks: " + list.toString());
        }
        else
        {
            Toast.makeText(context, "Getting All Task Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackGetWall(List<ServerTask> serverTaskList, Context context, List<ServerTask> list)
    {
        if (serverTaskList != null)
        {
            list = serverTaskList;
            Toast.makeText(context, "Wall Task Got Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Getting Wall Task Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackGetAllUsers(List<User> userList, Context context, List<User> list)
    {
        if (userList != null)
        {
            list = userList;
            Toast.makeText(context, "All Users Got Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Getting All Users Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackGetUser(User user, Context context, User mUser)
    {
        if (user != null)
        {
            mUser = user;
            Toast.makeText(context, "User Got Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Getting User Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void callbackGetMyself(User user, Context context, User mUser) {
        if (user != null)
        {
            user.setToken(mUser.getToken());
            mUser = user;
            Toast.makeText(context, "Self Got Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Getting Self Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
