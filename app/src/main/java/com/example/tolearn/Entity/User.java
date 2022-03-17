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

    public User (String email)
    {
        this.email = email;
    }
}
