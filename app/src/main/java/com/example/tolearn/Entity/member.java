package com.example.tolearn.Entity;

import com.google.gson.annotations.SerializedName;

public class member {
    @SerializedName("first_name")
    String name;

    @SerializedName("last_name")
    String lastname;

    public member(String name, String lastname)
    {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName()
    {
        return name + " " + lastname;
    }
}
