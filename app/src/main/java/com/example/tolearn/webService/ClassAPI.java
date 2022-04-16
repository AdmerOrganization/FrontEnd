package com.example.tolearn.webService;

import com.example.tolearn.Entity.myClass;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ClassAPI {
    public static final String BASE_URL = "https://amoozande.herokuapp.com/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })

    @Multipart
    @POST("classrooms/create/")
    Call<JsonObject> CreateClass(@Header("Authorization") String user_token, @Part("title") RequestBody title
                                 , @Part MultipartBody.Part image
                                 , @Part("teacher_name") RequestBody TeacherName
                                 , @Part("description") RequestBody Description
                                 , @Part("limit") RequestBody Limit
                                 , @Part("password") RequestBody password
    );

    @POST("classrooms/create/")
    Call<JsonObject> CreateClassWithoutAvatar(@Header("Authorization") String user_token,@Body JsonObject classInfo);

    @GET("classrooms/get-created/")
    Call<List<myClass>> GetCreatedClasses(@Header("Authorization") String user_token);

    @GET("classrooms/get-all/")
    Call<List<myClass>> GetAllClasses(@Header("Authorization") String user_token);

}
