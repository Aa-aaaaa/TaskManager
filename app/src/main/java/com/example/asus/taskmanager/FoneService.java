package com.example.asus.taskmanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.asus.taskmanager.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoneService extends Service {
    //static private String serverName = "http://siriustaskmanager.herokuapp.com/api/";
    static private String serverName = "http://salty-springs-72589.herokuapp.com/api/";
    //static private String serverName =  "10.21.136.185:8000/api/";
    private static String addToToken = "Token ";

    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(serverName).addConverterFactory(GsonConverterFactory.create()).build();
    static final private String TAG = "FoneService";

    public FoneService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public static String getToken(User user, final Context context)
    {
        if (user.getToken() == null) {
            Log.d(TAG, "getToken: started " + user.getLogin() + " " + user.getPassword());
            TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
            Call<TalkingToServerService.Token> call = talkingToServerService.serverGetToken(user.getLogin(), user.getPassword());
            CallbackList.callbackToken(call, context);
        }
        else
            getMyself(context);
            //return "503712e87969da1ab86c6eafa9b0e6d1ac81441b";
        return user.getToken();
    }

    public static void registration(User user, final Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<TalkingToServerService.RegisterUser> call = talkingToServerService.serverRegisterUser(user.getLogin(), user.getPassword(), user.getPassword(), user.getLastName(), user.getFirstName());
        CallbackList.callbackRegister(call, context, user);
    }

    public static List<ServerTask> addAllTasksFromGlobalDB(Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        //TODO get ID from Server (there is no way to get it now (when I wrote this TODO))
        MainActivity.getUser().setId(1);
        Call<List<ServerTask>> call = talkingToServerService.serverGetAll(MainActivity.getUser().getId(), addToToken + MainActivity.getToken());
        List<ServerTask> list= new ArrayList<>();
        CallbackList.calbackListServerTask(call, context, "getAllTask", list);
        return list;
    }

    public static void addTask(Task task, final Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<ServerTask> call = talkingToServerService.serverAddTask(addToToken + MainActivity.getUser().getToken(), task.getName(), task.getTime().getStringForDB(), task.getDescription());
        CallbackList.callbackServerTask(call, context, task, "addTask");
    }

    public static void updateTask(final Task task, final Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<ServerTask> call = talkingToServerService.serverUpdateTask(task.getGlobalDataBaseId(),addToToken + MainActivity.getToken(), task.getName(), task.getTime().getStringForDB(), task.getDescription());
        CallbackList.callbackServerTask(call, context, task, "updateTask");
    }

    public static void deleteTask(Long globalId, final Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<Void> call = talkingToServerService.serverDeleteTask(globalId,"Token " + MainActivity.getToken());
        CallbackList.callbackVoid(call, context, "deleteTask");
    }

    public static void getTask(Long taskid, Context context, Task task)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<ServerTask> call = talkingToServerService.serverGetTask(taskid, addToToken + MainActivity.getToken());
        CallbackList.callbackServerTask(call, context, task, "getTask");
    }

    public static List<User> getAllUsers(Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<List<User>> call = talkingToServerService.serverGetAllUsers(addToToken + MainActivity.getToken());
        List<User> list = new ArrayList<>();
        CallbackList.callbackListUser(call, context, list);
        return list;
    }

    public static void getUser(long userId, Context context, User user)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<User> call = talkingToServerService.serverGetUser(userId, addToToken + MainActivity.getToken());
        CallbackList.callbackUser(call, context, "getUser", user);
    }

    public static List<ServerTask> getWall(Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<List<ServerTask>> call = talkingToServerService.serverGetWall(addToToken + MainActivity.getToken());
        List<ServerTask> list = new ArrayList<>();
        CallbackList.calbackListServerTask(call, context, "getWall", list);
        return list;
    }

    public static void subscribe(long userid, Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<Void> call = talkingToServerService.serverSubscribe(userid, addToToken + MainActivity.getToken());
        CallbackList.callbackVoid(call, context, "subscribe");
    }

    public static void unsubscribe(long userid, Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<Void> call = talkingToServerService.serverUnsubscribe(userid, addToToken + MainActivity.getToken());
        CallbackList.callbackVoid(call, context, "unsubscribe");
    }

    public static void like(long taskid, Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<Void> call = talkingToServerService.serverLike(taskid, addToToken + MainActivity.getToken());
        CallbackList.callbackVoid(call, context, "like");
    }

    public static void unlike(long taskid, Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<Void> call = talkingToServerService.serverUnlike(taskid, addToToken + MainActivity.getToken());
        CallbackList.callbackVoid(call, context, "unlike");
    }

    public static void getMyself(Context context)
    {
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<User> call = talkingToServerService.serverGetMyself(addToToken + MainActivity.getToken());
        CallbackList.callbackUser(call, context, "getMyself", MainActivity.getUser());
    }
}