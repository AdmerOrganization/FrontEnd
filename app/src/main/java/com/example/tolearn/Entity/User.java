package com.example.tolearn.Entity;

public class User {
    private int ID;
    private String username;
    private String password;
    private String password2;
    private String email;
    private String first_name;
    private String last_name;
    private String token;
    private String phone_number;
    private String avatar;


    public User(String username, String password, String password2, String email) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.email = email;
    }

    public User (String email)
    {
        this.email = email;
    }
}
