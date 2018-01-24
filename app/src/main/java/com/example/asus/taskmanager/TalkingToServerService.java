package com.example.asus.taskmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Maxim on 19.01.2018.
 */

public interface TalkingToServerService {
    @FormUrlEncoded
    @POST("tasks/")
    Call<ServerTask> serverAddTask(@Header("Authorization") String token, @Field("name") String name, @Field("end_time") String time, @Field("description") String description);

    @FormUrlEncoded
    @PUT("tasks/{taskid}/")
    Call<ServerTask> serverUpdateTask(@Path("taskid") Long taskid, @Header("Authorization") String token, @Field("name") String name, @Field("end_time") String time, @Field("description") String description);

    @GET("tasks/{taskid}/")
    Call<ServerTask> serverGetTask(@Path("taskid") long taskid, @Header("Authorization") String token);


    @FormUrlEncoded
    @POST("auth/login/")
    Call<Token> serverGetToken(@Field("username") String username, @Field("password") String password);
    class Token {

        @SerializedName("token")
        @Expose
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return token;
        }
    }

    @FormUrlEncoded
    @POST("auth/register/")
    Call<RegisterUser> serverRegisterUser(@Field("email") String email, @Field("password1") String password1, @Field("password2") String password2, @Field("last_name") String lastname, @Field("first_name") String firstname);
    class RegisterUser{
        @SerializedName("messages")
        @Expose
        private Messages messages;
        @SerializedName("status")
        @Expose
        private Boolean status;

        public Messages getMessages() {
            return messages;
        }

        public void setMessages(Messages messages) {
            this.messages = messages;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        class Messages {

            @SerializedName("non_field")
            @Expose
            private String nonField;

            public String getNonField() {
                return nonField;
            }

            public void setNonField(String nonField) {
                this.nonField = nonField;
            }

        }
    }

    @GET("user/")
    Call<List<User>> serverGetAllUsers(@Header("Authorization") String token);


    @GET("user/{userid}/")
    Call<User> serverGetUser(@Path("userid") long userid, @Header("Authorization") String token);

    @GET("user/self/")
    Call<User> serverGetMyself(@Header("Authorization") String token);


    @GET("tasks/")
    Call<List<ServerTask>> serverGetAll(@Query("owner_id") long oener_id, @Header("Authorization") String token);

    @GET("tasks/wall/")
    Call<List<ServerTask>> serverGetWall(@Header("Authorization") String token);


    @DELETE("tasks/{taskid}/")
    Call<Void> serverDeleteTask(@Path("taskid") Long taskid, @Header("Authorization") String token);

    @POST("user/{userid}/subscribe/")
    Call<Void> serverSubscribe(@Path("userid") long userid, @Header("Authorization") String token);

    @POST("user/{userid}/unsubscribe/")
    Call<Void> serverUnsubscribe(@Path("userid") long userid, @Header("Authorization") String token);

    @POST("tasks/{taskid}/like/")
    Call<Void> serverLike(@Path("taskid") long taskid, @Header("Authorization") String token);

    @POST("tasks/{taskid}/unlike/")
    Call<Void> serverUnlike(@Path("taskid") long taskid, @Header("Authorization") String token);

}
