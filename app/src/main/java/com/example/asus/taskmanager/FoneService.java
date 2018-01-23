package com.example.asus.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
}