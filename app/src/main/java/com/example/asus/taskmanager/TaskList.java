package com.example.asus.taskmanager;

import android.content.Context;
import android.util.Log;

import com.example.asus.taskmanager.activities.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class TaskList {
    private static TaskList taskList;
    private static DataBase dataBase;
    private static Context thisContext;

    public static TaskList getInstance(Context context) {
        if (taskList == null) {
            taskList = new TaskList();
            makeDB(context);
            thisContext = context;
        }
        return taskList;
    }

    public void addTask(final Task task, final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        this.getDataBase().addTask(task);
        Call<ServerTask> call = MainActivity.getTalkingToServerService().serverAddTask(MainActivity.getAddToToken() + MainActivity.getToken(), task.getName(), task.getTime().getStringForDB(), task.getDescription());
        call.enqueue(new Callback<ServerTask>() {
            @Override
            public void onResponse(Call<ServerTask> call, Response<ServerTask> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<ServerTask> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public Task getTask(long dataBaseId)
    {
        return this.getDataBase().getTaskById(dataBaseId);
    }

    public void deleteTask(long dataBaseId, final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Long globalId = this.getDataBase().getTaskById(dataBaseId).getGlobalDataBaseId();
        this.getDataBase().deleteTask(dataBaseId);
        if (globalId != -1)
        {
            Call<Void> call = MainActivity.getTalkingToServerService().serverDeleteTask(globalId, MainActivity.getAddToToken() + MainActivity.getToken());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d(TAG, "onResponse: " + response.toString());
                    if (!response.isSuccessful())
                    {
                        onErrorCallback.perform();
                        return;
                    }
                    performObject.perform(response.body());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    onErrorCallback.perform();
                }
            });
        }
    }

    public void deleteTask(Task task) {
        this.getDataBase().deleteTask(task.getDataBaseId());
    }

    public void changeTask(Task newTask, final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        this.getDataBase().updateTask(newTask);
        Call<ServerTask> call = MainActivity.getTalkingToServerService().serverUpdateTask(newTask.getGlobalDataBaseId(),MainActivity.getAddToToken() + MainActivity.getToken(), newTask.getName(), newTask.getTime().getStringForDB(), newTask.getDescription());
        call.enqueue(new Callback<ServerTask>() {
            @Override
            public void onResponse(Call<ServerTask> call, Response<ServerTask> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<ServerTask> call, Throwable t) {
                Log.e(TAG, "onFailure: UpdateFailure");
                onErrorCallback.perform();
            }
        });
    }

    private static void makeDB(Context context)
    {
        dataBase = new DataBase(context);
    }

    public DataBase getDataBase()
    {
        return this.dataBase;
    }

    public void clearDataBase()
    {
        dataBase.onUpgrade(null, 0, 0);
    }


    public void getAllTasksFromGlobalDataBase(final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<List<ServerTask>> call = MainActivity.getTalkingToServerService().serverGetAll(MainActivity.getUser().getId(), MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<List<ServerTask>>() {
            @Override
            public void onResponse(Call<List<ServerTask>> call, Response<List<ServerTask>> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<List<ServerTask>> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public void getWall(final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<List<ServerTask>> call = MainActivity.getTalkingToServerService().serverGetWall(MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<List<ServerTask>>() {
            @Override
            public void onResponse(Call<List<ServerTask>> call, Response<List<ServerTask>> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<List<ServerTask>> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public void getTask(long taskid, final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<ServerTask> call = MainActivity.getTalkingToServerService().serverGetTask(taskid, MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<ServerTask>() {
            @Override
            public void onResponse(Call<ServerTask> call, Response<ServerTask> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<ServerTask> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public void like(long taskid, final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<Void> call = MainActivity.getTalkingToServerService().serverLike(taskid, MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public void unlike(long taskid, final PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<Void> call = MainActivity.getTalkingToServerService().serverUnlike(taskid, MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }


    public interface PerformObject
    {
        void perform(Object object);
    }
}
