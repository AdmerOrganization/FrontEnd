package com.example.tolearn.webService;

import com.example.tolearn.Entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {
    public static final String BASE_URL="https://shanbe-back.herokuapp.com/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("accounts/register/")
    Call<Void> CreateUser(@Header("Content-Type") String content_type, @Body User user);
}
