package com.example.asus.taskmanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoneService extends Service {
    static private String serverName = "http://siriustaskmanager.herokuapp.com/api/";
    //static private String serverName = "https://salty-springs-72589.herokuapp.com/api/";
    private static String token = null;
    private static DataBase dataBase = null;

    /*final private static String name = "name";
    final private static String description = "description";
    final private static String date = "end_time";*/
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(serverName).addConverterFactory(GsonConverterFactory.create()).build();
    static final private String TAG = "FoneService";

    public FoneService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = TaskList.getInstance(getApplicationContext()).getDataBase();
        if (token !=null)
        {
            token = getToken("login", "password", getApplicationContext());
            MainActivity.setToken(token);
        }
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

    public static String getToken(String username, String password, final Context context)
    {
        if (token == null) {
            /*ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            new DoWithTask(context, params, "POST", "auth/login/", "login").execute((Task[])null);*/
            token = MainActivity.getToken();
            if (token != null)
                return token;
            Log.d(TAG, "getToken: started " + username + " " + password);
            TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
            Call<TalkingToServerService.Token> call = talkingToServerService.serverGetToken(username, password);
            call.enqueue(new Callback<TalkingToServerService.Token>() {
                @Override
                public void onResponse(Call<TalkingToServerService.Token> call, Response<TalkingToServerService.Token> response) {
                    if (response.isSuccessful()) {
                        token = response.body().getToken();

                        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.commit();

                        MainActivity.setToken(token);
                        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                    else
                    {
                        //TODO this toast doesn't work
                        Toast.makeText(context.getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG);
                        Log.d("LOGIN", "Can't sign in");
                    }
                    Log.d("Token", "getToken: " + token);
                }

                @Override
                public void onFailure(Call<TalkingToServerService.Token> call, Throwable t) {
                    Log.e("Error", t.toString());
                }
            });
        }
            //return "503712e87969da1ab86c6eafa9b0e6d1ac81441b";
        return token;
    }

    public static void registration(final String login, final String password, String firstName, String lastName, final Context context)
    {
        /*ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("email", login));
        params.add(new BasicNameValuePair("password1", password));
        params.add(new BasicNameValuePair("password2", password));
        params.add(new BasicNameValuePair("last_name", lastName));
        params.add(new BasicNameValuePair("first_name", firstName));
        new DoWithTask(context, params, "POST", "auth/register/", "registration").execute((Task[])null);*/
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<TalkingToServerService.RegisterUser> call = talkingToServerService.serverRegisterUser(login, password, password, lastName, firstName);
        call.enqueue(new Callback<TalkingToServerService.RegisterUser>() {
            @Override
            public void onResponse(Call<TalkingToServerService.RegisterUser> call, Response<TalkingToServerService.RegisterUser> response) {
                if (response.isSuccessful())
                {
                    Log.d(TAG, "onResponse: " + response.toString());
                    MainActivity.setUsername(login);
                    MainActivity.setPassword(password);
                    Log.d("JSON", "Registration finished succesfully");
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                    Log.e("JSON", "Registration faild");
                }
            }

            @Override
            public void onFailure(Call<TalkingToServerService.RegisterUser> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Log.e("JSON", "Registration faild");
            }
        });

    }

    public static void addAllTasksFromGlobalDB(Context context)
    {
        /*token = getToken(MainActivity.getUsername(), MainActivity.getPassword(), context);
        ArrayList<NameValuePair> params = new ArrayList<>();
        new DoWithTask(context, params, "GET", "tasks/", "tadklist").execute((Task[]) null);*/
        /*TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<List<TalkingToServerService.AddTask>> call = talkingToServerService.serverAddAllFromServer("Token " + token);
        call.enqueue(new Callback<List<TalkingToServerService.AddTask>>() {
            @Override
            public void onResponse(Call<List<TalkingToServerService.AddTask>> call, Response<List<TalkingToServerService.AddTask>> response) {
                if (response.isSuccessful()){
                    List<TalkingToServerService.AddTask> list = response.body();
                    for (TalkingToServerService.AddTask i: list)
                    {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<TalkingToServerService.AddTask>> call, Throwable t) {

            }
        });*/
        return;
    }

    public static void addTask(final Task task, final Context context)
    {
        token = getToken(MainActivity.getUsername(), MainActivity.getPassword(), context);
        /*ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(name, task.getName()));
        params.add(new BasicNameValuePair(date, task.getTime().getStringForDB()));
        Log.d("JSONFoneService", task.getTime().getStringForDB());
        params.add(new BasicNameValuePair(description, task.getDescription()));
        new DoWithTask(context, params, "POST", "tasks/", "add").execute(task);*/
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<TalkingToServerService.AddTask> call = talkingToServerService.serverAddTask("Token " + token, task.getName(), task.getTime().getStringForDB(), task.getDescription());
        call.enqueue(new Callback<TalkingToServerService.AddTask>() {
            @Override
            public void onResponse(Call<TalkingToServerService.AddTask> call, Response<TalkingToServerService.AddTask> response) {
                if (response.isSuccessful())
                {
                    task.setGlobalDataBaseId(response.body().getId());
                    TaskList.getInstance(context).getDataBase().updateTask(task);
                    Log.d(TAG, "onResponse: " + task.getGlobalDataBaseId());
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<TalkingToServerService.AddTask> call, Throwable t) {
                Log.e(TAG, "onFailure: "  + t.toString());
            }
        });
    }

    public static void updateTask(final Task task, final Context context)
    {
        token = getToken(MainActivity.getUsername(), MainActivity.getPassword(), context);
        /*ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(name, task.getName()));
        params.add(new BasicNameValuePair(date, task.getTime().getStringForDB()));
        params.add(new BasicNameValuePair(description, task.getDescription()));
        new DoWithTask(context, params, "PUT", "tasks/" + task.getGlobalDataBaseId() + "/", "updating").execute(task);*/
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<TalkingToServerService.UpdateTask> call = talkingToServerService.serverUpdateTask(task.getGlobalDataBaseId(),"Token " + token, task.getName(), task.getTime().getStringForDB(), task.getDescription());
        call.enqueue(new Callback<TalkingToServerService.UpdateTask>() {
            @Override
            public void onResponse(Call<TalkingToServerService.UpdateTask> call, Response<TalkingToServerService.UpdateTask> response) {
                if (response.isSuccessful())
                {
                    Log.d(TAG, "onResponse: " + response.toString());
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<TalkingToServerService.UpdateTask> call, Throwable t) {
                Log.e(TAG, "onFailure: "  + t.toString());
            }
        });
    }

    public static void deleteTask(Long globalId, Context context)
    {
        token = getToken(MainActivity.getUsername(), MainActivity.getPassword(), context);
        /*ArrayList<NameValuePair> params = new ArrayList<>();
        new DoWithTask(context, params, "DELETE", "tasks/" + globalId + "/", "deleting").execute((Task[])null);*/
        TalkingToServerService talkingToServerService = retrofit.create(TalkingToServerService.class);
        Call<TalkingToServerService.UpdateTask> call = talkingToServerService.serverDeleteTask(globalId,"Token " + token);
        call.enqueue(new Callback<TalkingToServerService.UpdateTask>() {
            @Override
            public void onResponse(Call<TalkingToServerService.UpdateTask> call, Response<TalkingToServerService.UpdateTask> response) {
                if (response.isSuccessful())
                {
                    Log.d(TAG, "onResponse: " + response.toString());
                }
                else
                {
                    Log.e(TAG, "onResponse: Error");
                    Log.e(TAG, "onResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<TalkingToServerService.UpdateTask> call, Throwable t) {
                Log.e(TAG, "onFailure: "  + t.toString());
            }
        });
    }

    public static void setToken(String tToken) {
        token = tToken;
    }

    /*public static class DoWithTask extends AsyncTask<Task, Void, JSONObject>
    {
        static Context context = null;
        final private static String name = "name";
        final private static String description = "description";
        final private static String date = "end_time";
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
                            //TODO this toast doesn't work
                            Toast.makeText(context.getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG);
                            Log.d("LOGIN", "Can't sign in");
                            return;
                        }
                        token = jObject.getString("token");

                        SharedPreferences sharedPreferences = context.getSharedPreferences("Settings", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.commit();

                        MainActivity.setToken(token);
                        context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                    case ("registration"):
                        boolean isRegistered = jObject.getBoolean("status");
                        if (isRegistered) {
                            Log.d("JSON", "Registration finished succesfully");
                            context.startActivity(new Intent(context, LoginActivity.class));
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
    }*/
}