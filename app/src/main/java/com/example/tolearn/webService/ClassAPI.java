package com.example.tolearn.webService;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ClassAPI {
    public static final String BASE_URL = "https://amoozande.herokuapp.com/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })

    @Multipart
    @POST("classrooms/create/")
    Call<JsonObject> CreateClass(@Header("Authorization") String user_token, @Part("Title") RequestBody title
                                 , @Part MultipartBody.Part image
                                 , @Part("Teacher_name") RequestBody TeacherName
                                 , @Part("Description") RequestBody Description
                                 , @Part("Limit") RequestBody Limit
                                 , @Part("Password") RequestBody password
    );

    @POST("classrooms/create/")
    Call<JsonObject> CreateClassWithoutAvatar(@Header("Authorization") String user_token,@Body JsonObject classInfo);
}
