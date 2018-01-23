package com.example.asus.taskmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Maxim on 21.01.2018.
 */

public class ServerTask {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("likes_cnt")
    @Expose
    private Long likesCnt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("finish_date")
    @Expose
    private Object finishDate;
    @SerializedName("finish_time")
    @Expose
    private Object finishTime;
    @SerializedName("repeat_settings")
    @Expose
    private Object repeatSettings;
    @SerializedName("owner")
    @Expose
    private Long owner;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getLikesCnt() {
        return likesCnt;
    }
    public void setLikesCnt(Long likesCnt) {
        this.likesCnt = likesCnt;
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

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Object getFinishDate() {
        return finishDate;
    }
    public void setFinishDate(Object finishDate) {
        this.finishDate = finishDate;
    }

    public Object getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(Object finishTime) {
        this.finishTime = finishTime;
    }

    public Object getRepeatSettings() {
        return repeatSettings;
    }
    public void setRepeatSettings(Object repeatSettings) {
        this.repeatSettings = repeatSettings;
    }

    public Long getOwner() {
        return owner;
    }
    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Task getTask() {
        MyDate myDate = new MyDate();
        myDate.setTimeFromDB(this.endTime);
        Task task = new Task(this.name, myDate, this.description);
        task.setGlobalDataBaseId(this.id);
        return task;
    }
    public void setTask(Task task) {
        this.setName(task.getName());
        this.setEndTime(task.getTime().getString());
        this.setDescription(task.getDescription());
        this.setId(task.getGlobalDataBaseId());
    }

    public ServerTask getServerTask()
    {
        return this;
    }

    @Override
    public String toString() {
        return "ServerTask{" +
                "id=" + id +
                ", likesCnt=" + likesCnt +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", endTime='" + endTime + '\'' +
                ", finishDate=" + finishDate +
                ", finishTime=" + finishTime +
                ", repeatSettings=" + repeatSettings +
                ", owner=" + owner +
                '}';
    }
}
