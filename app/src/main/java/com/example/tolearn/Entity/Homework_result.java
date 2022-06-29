package com.example.tolearn.Entity;

import com.google.gson.annotations.SerializedName;

public class Homework_result {
    @SerializedName("id")
    String id;

    @SerializedName("homework")
    String homework;

    @SerializedName("file")
    String file;

    @SerializedName("user")
    User user;

    @SerializedName("date")
    String date;

    public Homework_result(String id, String homework, String file, User user, String date) {
        this.id = id;
        this.homework = homework;
        this.file = file;
        this.user = user;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
