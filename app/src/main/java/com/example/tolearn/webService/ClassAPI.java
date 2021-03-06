package com.example.tolearn.webService;

import com.example.tolearn.Entity.member;
import com.example.tolearn.Entity.myClass;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ClassAPI {
    public static final String BASE_URL = "http://185.235.42.101:8000/";
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
    @POST("classrooms/join/")
    Call<JsonObject> JoinClass(@Header("Authorization") String user_token,@Body JsonObject joinInfo);
    @GET("classrooms/get-created/")
    Call<List<myClass>> GetCreatedClasses(@Header("Authorization") String user_token);

    @GET("classrooms/get-all/")
    Call<List<myClass>> GetAllClasses(@Header("Authorization") String user_token);

    @POST("classrooms/search/")
    Call<List<myClass>> Filter(@Header("Authorization") String user_token,@Body JsonObject seachInfo);

    @POST("classrooms/search/")
    Call<myClass> Search(@Header("Authorization") String user_token,@Body JsonObject seachInfo);

    @PUT("classrooms/edit/")
    Call<JsonObject> EditClass(@Header("Authorization") String user_token,@Body JsonObject classInfo);

    @POST("classrooms/get-joined/")
    Call<List<myClass>> joinedClasses(@Header("Authorization") String user_token);
    @HTTP(method = "DELETE", path = "classrooms/delete/", hasBody = true)
    Call<JsonObject> deleteClass(@Header("Authorization") String user_token, @Body JsonObject jsonObject);

    @POST("classrooms/students/")
    Call<List<member>> classMembers(@Header("Authorization") String user_token , @Body JsonObject jsonObject);

    @POST("classrooms/retrieve/")
    Call<myClass> classInfo(@Header("Authorization") String user_toke , @Body JsonObject jsonObject);
}
