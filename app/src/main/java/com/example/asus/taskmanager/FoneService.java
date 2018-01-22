package com.example.asus.taskmanager;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.taskmanager.Activities.LoginActivity;
import com.example.asus.taskmanager.Activities.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoneService extends Service {
    private static String serverName = null;
    private static String token = null;
    private static DataBase dataBase = null;

    final private static String name = "name";
    final private static String description = "description";
    final private static String date = "end_time";

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

    public static String getToken(User user, Context context)
    {
        // TODO: get token from server
        serverName = MainActivity.getServerName();
        if (token == null) {
            ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", user.getUsername()));
            params.add(new BasicNameValuePair("password", user.getPassword()));
            new DoWithTask(context, params, "POST", "auth/login/", "login").execute((Task[])null);
        }
            //return "503712e87969da1ab86c6eafa9b0e6d1ac81441b";
        return token;
    }

    public static void registration(User user, Context context)
    {
        serverName = MainActivity.getServerName();
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("email", user.getUsername()));
        params.add(new BasicNameValuePair("password1", user.getPassword()));
        params.add(new BasicNameValuePair("password2", user.getPassword()));
        params.add(new BasicNameValuePair("last_name", user.getPassword()));
        params.add(new BasicNameValuePair("first_name", user.getPassword()));
        new DoWithTask(context, params, "POST", "auth/register/", "registration").execute((Task[])null);
    }

    public static void addTask(Task task, Context context)
    {
        token = getToken(MainActivity.getUser(), context);
        ArrayList<NameValuePair> params = new ArrayList<>();
        serverName = MainActivity.getServerName();
        params.add(new BasicNameValuePair(name, task.getName()));
        params.add(new BasicNameValuePair(date, task.getTime().getStringForDB()));
        Log.d("JSONFoneService", task.getTime().getStringForDB());
        params.add(new BasicNameValuePair(description, task.getDescription()));
        new DoWithTask(context, params, "POST", "tasks/", "add").execute(task);
        //Log.d("JSONFoneService", token);
    }

    public static void updateTask(Task task, Context context)
    {
        token = getToken(MainActivity.getUser(), context);
        ArrayList<NameValuePair> params = new ArrayList<>();
        serverName = MainActivity.getServerName();
        params.add(new BasicNameValuePair(name, task.getName()));
        params.add(new BasicNameValuePair(date, task.getTime().getStringForDB()));
        params.add(new BasicNameValuePair(description, task.getDescription()));
        new DoWithTask(context, params, "PUT", "tasks/" + task.getGlobalDataBaseId() + "/", "updating").execute(task);
    }

    public static void deleteTask(Long globalId, Context context)
    {
        token = getToken(MainActivity.getUser(), context);
        ArrayList<NameValuePair> params = new ArrayList<>();
        serverName = MainActivity.getServerName();
        new DoWithTask(context, params, "DELETE", "tasks/" + globalId + "/", "deleting").execute((Task[])null);
    }

    public static class DoWithTask extends AsyncTask<Task, Void, JSONObject>
    {
        static Context context = null;
        private Long id;
        private List<NameValuePair> params = null;
        private String method = null;
        private String suffix = null;
        private String operation = null;

        public DoWithTask(Context context, ArrayList<NameValuePair> params, String method, String suffix, String operation)
        {
            super();
            this.context = context;
            this.params = params;
            this.method = method;
            this.operation = operation;
            this.suffix = suffix;
        }

        @Override
        protected JSONObject doInBackground(Task... tasks) {
            if (tasks != null)
                return loadJSON(tasks[0]);
            else
                return loadJSON(null);
        }

        public JSONObject loadJSON(Task task)
        {
            if (task != null)
                id = task.getDataBaseId();
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(serverName + this.suffix, this.method, this.params, token);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (jsonObject != null)
            {
                afterWorking(jsonObject);
            }
        }

        private void afterWorking(JSONObject jObject)
        {
            try {
                switch (operation) {
                    case ("add"):
                        Long gId = jObject.getLong("id");
                        Task task = TaskList.getInstance(context).getTask(id);
                        task.setGlobalDataBaseId(gId);
                        TaskList.getInstance(context).getDataBase().updateTask(task);
                        break;
                    case ("login"):
                        if (jObject.isNull("token"))
                        {
                            Toast.makeText(context.getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                            Log.d("LOGIN", "Can't sign in");
                            return;
                        }
                        token = jObject.getString("token");

                        MainActivity.getUser().succesfullLogin(context, token);

                        context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        ((Activity)context).finish();
                        break;
                    case ("registration"):
                        boolean isRegistered = jObject.getBoolean("status");
                        if (isRegistered) {
                            Log.d("JSON", "Registration finished succesfully");
                            context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            ((Activity)context).finish();
                        }
                        else
                            Log.e("JSON", "Registration faild");
                        break;
                    case ("deleting"):
                        Log.d("JSON", "afterWorking: " + jObject.toString());
                        break;
                    case ("updating"):
                        Log.d("JSON", "afterWorking: " + jObject.toString());
                }
                Log.d("JSONFoneService", operation + " has finished succesfully");

            } catch (JSONException e){
                e.printStackTrace();
                Log.e("JSONFoneService", e.toString());
            }
        }
    }
}