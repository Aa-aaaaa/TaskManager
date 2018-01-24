package com.example.asus.taskmanager;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Maxim on 22.01.2018.
 */

public class CallbackList {
    private static String TAG = "CallbackList";
    private Task task;
    public static void callbackServerTask(Call<ServerTask> call, final Context context, final Task task, final String operation)
    {
        final ServerTask[] serverTask = {new ServerTask()};
        call.enqueue(new Callback<ServerTask>() {
            @Override
            public void onResponse(Call<ServerTask> call, Response<ServerTask> response) {
                if (response.isSuccessful())
                {
                    serverTask[0] = response.body().getServerTask();
                    Log.d(TAG, "onResponse: " + serverTask[0]);
                    switch(operation)
                    {
                        case("addTask"):
                            CallbackDoSomething.callbackAddTask(serverTask[0], context, task);
                            break;
                        case("updateTask"):
                            CallbackDoSomething.callbackUpdateTask(serverTask[0], context, task);
                            break;
                        case("getTask"):
                            CallbackDoSomething.callbackGetTask(serverTask[0], context, task);
                            break;
                    }
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    serverTask[0] = null;
                    switch(operation)
                    {
                        case("addTask"):
                            CallbackDoSomething.callbackAddTask(serverTask[0], context, task);
                            break;
                        case("updateTask"):
                            CallbackDoSomething.callbackUpdateTask(serverTask[0], context, task);
                            break;
                        case("getTask"):
                            CallbackDoSomething.callbackGetTask(serverTask[0], context, task);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerTask> call, Throwable t) {
                Log.e(TAG, "onFailure: "  + t.toString());
                serverTask[0] = null;
                switch(operation)
                {
                    case("addTask"):
                        CallbackDoSomething.callbackAddTask(serverTask[0], context, task);
                        break;
                    case("updateTask"):
                        CallbackDoSomething.callbackUpdateTask(serverTask[0], context, task);
                        break;
                    case("getTask"):
                        CallbackDoSomething.callbackGetTask(serverTask[0], context, task);
                        break;
                }
            }
        });
    }

    public static void calbackListServerTask(Call<List<ServerTask>> call, final Context context, final String operation, final List<ServerTask> retList)
    {
        final List<ServerTask>[] list = new List[]{new ArrayList<>()};
        call.enqueue(new Callback<List<ServerTask>>() {
            @Override
            public void onResponse(Call<List<ServerTask>> call, Response<List<ServerTask>> response) {
                if (response.isSuccessful())
                {
                    list[0] = response.body();
                    Log.d(TAG, "onResponse: " + list[0]);
                    switch (operation)
                    {
                        case ("getAllTasks"):
                            CallbackDoSomething.callbackGetAllTasks(list[0], context, retList);
                            break;
                        case ("getWall"):
                            CallbackDoSomething.callbackGetWall(list[0], context, retList);
                            break;
                    }
                }
                else
                {
                    list[0] = null;
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    switch (operation)
                    {
                        case ("getAllTasks"):
                            CallbackDoSomething.callbackGetAllTasks(list[0], context, retList);
                            break;
                        case ("getWall"):
                            CallbackDoSomething.callbackGetWall(list[0], context, retList);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ServerTask>> call, Throwable t) {
                Log.e(TAG, "onFailure: "  + t.toString());
                list[0] = null;
                switch (operation)
                {
                    case ("getAllTasks"):
                        CallbackDoSomething.callbackGetAllTasks(list[0], context, retList);
                        break;
                    case ("getWall"):
                        CallbackDoSomething.callbackGetWall(list[0], context, retList);
                        break;
                }
            }
        });
    }

    public static void callbackVoid(Call<Void> call, final Context context, final String operation)
    {
        final boolean[] bool = {false};
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                {
                    bool[0] = true;
                    Log.d(TAG, "onResponse: " + response.toString());
                    switch (operation)
                    {
                        case ("deleteTask"):
                            CallbackDoSomething.callbackDeleteTask(bool[0], context);
                            break;
                        case ("subscribe"):
                            CallbackDoSomething.callbackSubscribe(bool[0], context);
                            break;
                        case ("unsubscribe"):
                            CallbackDoSomething.callbackUnsubscribe(bool[0], context);
                            break;
                        case ("like"):
                            CallbackDoSomething.callbackLike(bool[0], context);
                            break;
                        case ("unlike"):
                            CallbackDoSomething.callbackUnike(bool[0], context);
                            break;
                    }
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    switch (operation)
                    {
                        case ("deleteTask"):
                            CallbackDoSomething.callbackDeleteTask(bool[0], context);
                            break;
                        case ("subscribe"):
                            CallbackDoSomething.callbackSubscribe(bool[0], context);
                            break;
                        case ("unsubscribe"):
                            CallbackDoSomething.callbackUnsubscribe(bool[0], context);
                            break;
                        case ("like"):
                            CallbackDoSomething.callbackLike(bool[0], context);
                            break;
                        case ("unlike"):
                            CallbackDoSomething.callbackUnike(bool[0], context);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                switch (operation)
                {
                    case ("deleteTask"):
                        CallbackDoSomething.callbackDeleteTask(bool[0], context);
                        break;
                    case ("subscribe"):
                        CallbackDoSomething.callbackSubscribe(bool[0], context);
                        break;
                    case ("unsubscribe"):
                        CallbackDoSomething.callbackUnsubscribe(bool[0], context);
                        break;
                    case ("like"):
                        CallbackDoSomething.callbackLike(bool[0], context);
                        break;
                    case ("unlike"):
                        CallbackDoSomething.callbackUnike(bool[0], context);
                        break;
                }
            }
        });
    }

    public static void callbackListUser(Call<List<User>> call, final Context context, final List<User> list)
    {
        final List<User>[] object = new List[]{new ArrayList<>()};
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful())
                {
                    object[0] = response.body();
                    Log.d(TAG, "onResponse: " + object[0].toString());
                    Log.d(TAG, "onResponse: " + response.toString());
                    CallbackDoSomething.callbackGetAllUsers(object[0], context, list);
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    object[0] = null;
                    CallbackDoSomething.callbackGetAllUsers(object[0], context, list);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                object[0] = null;
                CallbackDoSomething.callbackGetAllUsers(object[0], context, list);
            }
        });
    }

    public static void callbackUser(Call<User> call, final Context context, final String operation, final User mUser)
    {
        final User[] user = {new User()};
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful())
                {
                    user[0] = response.body();
                    Log.d(TAG, "onResponse: " + user[0].toString());
                    Log.d(TAG, "onResponse: " + response.toString());
                    switch (operation)
                    {
                        case ("getUser"):
                            CallbackDoSomething.callbackGetUser(user[0], context, mUser);
                            break;
                        case ("getMyself"):
                            CallbackDoSomething.callbackGetMyself(user[0], context, mUser);
                            break;
                    }
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    user[0] = null;
                    switch (operation)
                    {
                        case ("getUser"):
                            CallbackDoSomething.callbackGetUser(user[0], context, mUser);
                            break;
                        case ("getMyself"):
                            CallbackDoSomething.callbackGetMyself(user[0], context, mUser);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                user[0] = null;
                switch (operation)
                {
                    case ("getUser"):
                        CallbackDoSomething.callbackGetUser(user[0], context, mUser);
                        break;
                    case ("getMyself"):
                        CallbackDoSomething.callbackGetMyself(user[0], context, mUser);
                        break;
                }
            }
        });
    }

    public static void callbackToken(Call<TalkingToServerService.Token> call, final Context context)
    {
        final TalkingToServerService.Token[] object = {new TalkingToServerService.Token()};
        call.enqueue(new Callback<TalkingToServerService.Token>() {
            @Override
            public void onResponse(Call<TalkingToServerService.Token> call, Response<TalkingToServerService.Token> response) {
                if (response.isSuccessful())
                {
                    object[0] = response.body();
                    Log.d(TAG, "onResponse: " + object[0].toString());
                    Log.d(TAG, "onResponse: " + response.toString());
                    CallbackDoSomething.callbackGetToken(object[0], context);
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    object[0] = null;
                    CallbackDoSomething.callbackGetToken(object[0], context);
                }
            }

            @Override
            public void onFailure(Call<TalkingToServerService.Token> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                object[0] = null;
                CallbackDoSomething.callbackGetToken(object[0], context);
            }
        });
    }

    public static void callbackRegister(Call<TalkingToServerService.RegisterUser> call, final Context context, final User user)
    {
        final TalkingToServerService.RegisterUser[] object = {new TalkingToServerService.RegisterUser()};
        call.enqueue(new Callback<TalkingToServerService.RegisterUser>() {
            @Override
            public void onResponse(Call<TalkingToServerService.RegisterUser> call, Response<TalkingToServerService.RegisterUser> response) {
                if (response.isSuccessful())
                {
                    object[0] = response.body();
                    Log.d(TAG, "onResponse: " + object[0].toString());
                    Log.d(TAG, "onResponse: " + response.toString());
                    CallbackDoSomething.callbackRegistration(object[0], context, user);
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    object[0] = null;
                    CallbackDoSomething.callbackRegistration(object[0], context, user);
                }
            }

            @Override
            public void onFailure(Call<TalkingToServerService.RegisterUser> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                object[0] = null;
                CallbackDoSomething.callbackRegistration(object[0], context, user);
            }
        });
    }


}
