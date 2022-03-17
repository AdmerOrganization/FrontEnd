package com.example.tolearn.Entity;

import com.google.gson.annotations.SerializedName;

public class User {
    private int ID;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("password2")
    private String password2;

    @SerializedName("email")
    private String email;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    private String token;
    private String phone_number;
    private String avatar;

    //sign up
    public User(String username, String password, String password2, String email, String first_name , String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.email = email;
    }

    //Login
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Edit Profile
    public User(String first_name,String last_name,String email,String phone_number){
        this.phone_number=phone_number;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword2() {
        return password2;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getToken() {
        return token;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAvatar() {
        return avatar;
    }

    public User (String email)
    {
        this.email = email;
    }
}
