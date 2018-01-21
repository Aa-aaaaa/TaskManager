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

/**
 * Created by Maxim on 19.01.2018.
 */

public interface TalkingToServerService {
    @FormUrlEncoded
    @POST("tasks/")
    Call<AddTask> serverAddTask(@Header("Authorization") String token, @Field("name") String name, @Field("end_time") String time, @Field("description") String description);
    class AddTask {
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("owner")
        @Expose
        private Owner owner;
        @SerializedName("access_list")
        @Expose
        private List<Object> accessList = null;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public List<Object> getAccessList() {
            return accessList;
        }

        public void setAccessList(List<Object> accessList) {
            this.accessList = accessList;
        }

        @Override
        public String toString() {
            return id + " " + name + " " + endTime + " " + description;
        }
        class Owner
        {

            @SerializedName("email")
            @Expose
            private String email;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            @Override
            public String toString() {
                return email;
            }
        }

    }

    @FormUrlEncoded
    @PUT("tasks/{taskid}/")
    Call<UpdateTask> serverUpdateTask(@Path("taskid") Long taskid, @Header("Authorization") String token, @Field("name") String name, @Field("end_time") String time, @Field("description") String description);
    class UpdateTask{
        @SerializedName("id")
        @Expose
        private Long id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("owner")
        @Expose
        private Owner owner;
        @SerializedName("access_list")
        @Expose
        private List<Object> accessList = null;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public List<Object> getAccessList() {
            return accessList;
        }

        public void setAccessList(List<Object> accessList) {
            this.accessList = accessList;
        }

        public class Owner {

            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("last_name")
            @Expose
            private String lastName;
            @SerializedName("first_name")
            @Expose
            private String firstName;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
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

        }
    }

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

    @DELETE("tasks/{taskid}/")
    Call<UpdateTask> serverDeleteTask(@Path("taskid") Long taskid, @Header("Authorization") String token);

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

    @GET("tasks/")
    Call<List<AddTask>> serverAddAllFromServer(@Header("Authorization") String token);
}
