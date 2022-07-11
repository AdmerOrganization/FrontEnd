package com.example.tolearn.Entity;

public class message {
    String id;
    String fname;
    String lname;
    String message;
    String timestamp;
    String avatar;


    public message(String id, String fname, String lname, String message, String timestamp, String avatar) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.message = message;
        this.timestamp = timestamp;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
