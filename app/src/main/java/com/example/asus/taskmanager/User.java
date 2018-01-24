package com.example.asus.taskmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.asus.taskmanager.activities.MainActivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

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
    @SerializedName("subscribers")
    @Expose
    private  List<Long> subscribers;
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

    public List<Long> getSubscribers() {
        return this.subscribers;
    }
    public void setSubscribers(List<Long> subscribers) {
        this.subscribers = subscribers;
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
        id = sharedPreferences.getLong("id", 0);
    }

    public void succesfullLogin(Context context, String token) // Adds token to Share Preferences, sets this.token
    {
        this.token = token;
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("token", token).commit();
    }

    public void succesfullLoginId(Context context, Long id) // Adds token to Share Preferences, sets this.token
    {
        this.id = id;
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong("id", id).commit();
    }


    public void logout(Context context)
    {
        this.token = null;
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(MainActivity.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("token").remove("id").commit();
    }

    public static void getToken(final User user, final Context context, final TaskList.PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        if (user.getToken() == null) {
            Call<TalkingToServerService.Token> call = MainActivity.getTalkingToServerService().serverGetToken(user.getLogin(), user.getPassword());
            call.enqueue(new Callback<TalkingToServerService.Token>() {
                @Override
                public void onResponse(Call<TalkingToServerService.Token> call, Response<TalkingToServerService.Token> response) {
                    if (!response.isSuccessful())
                    {
                        onErrorCallback.perform();
                        return;
                    }
                    performObject.perform(response.body());

                    getMyself(new TaskList.PerformObject() {
                        @Override
                        public void perform(Object object) {
                            User user1 = (User) object;
                            user1.setToken(user.getToken());
                            user1.setPassword(user.getPassword());
                            MainActivity.setUser(user1);
                            MainActivity.getUser().succesfullLoginId(context, user1.getId());
                        }
                    }, new Utils.OnErrorCallback() {
                        @Override
                        public void perform() {
                            Log.e(TAG, "perform: Error Happened");
                        }
                    });
                }

                @Override
                public void onFailure(Call<TalkingToServerService.Token> call, Throwable t) {
                    onErrorCallback.perform();
                }
            });
        }
        else
            getMyself(new TaskList.PerformObject() {
                @Override
                public void perform(Object object) {
                    User user1 = (User) object;
                    user1.setToken(user.getToken());
                    user1.setPassword(user.getPassword());
                    MainActivity.setUser(user1);
                    MainActivity.getUser().succesfullLoginId(context, user1.getId());
                }
            }, new Utils.OnErrorCallback() {
                @Override
                public void perform() {
                    Log.e(TAG, "perform: Error Happened");
                }
            });
    }

    public static void getMyself(final TaskList.PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<User> call = MainActivity.getTalkingToServerService().serverGetMyself(MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public static void registration(User user, final Context context, final TaskList.PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<TalkingToServerService.RegisterUser> call = MainActivity.getTalkingToServerService().serverRegisterUser(user.getLogin(), user.getPassword(), user.getPassword(), user.getLastName(), user.getFirstName());
        call.enqueue(new Callback<TalkingToServerService.RegisterUser>() {
            @Override
            public void onResponse(Call<TalkingToServerService.RegisterUser> call, Response<TalkingToServerService.RegisterUser> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<TalkingToServerService.RegisterUser> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public static void getAllUsers(final TaskList.PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<List<User>> call = MainActivity.getTalkingToServerService().serverGetAllUsers(MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }

    public static void subscribe(long id, final TaskList.PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<Void> call = MainActivity.getTalkingToServerService().serverSubscribe(id, MainActivity.getAddToToken() + MainActivity.getToken());
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

    public static void unsubscribe(long id, final TaskList.PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<Void> call = MainActivity.getTalkingToServerService().serverUnsubscribe(id, MainActivity.getAddToToken() + MainActivity.getToken());
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

    public static void getUser(long id, final TaskList.PerformObject performObject, final Utils.OnErrorCallback onErrorCallback)
    {
        Call<User> call = MainActivity.getTalkingToServerService().serverGetUser(id, MainActivity.getAddToToken() + MainActivity.getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful())
                {
                    onErrorCallback.perform();
                    return;
                }
                performObject.perform(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onErrorCallback.perform();
            }
        });
    }
}
