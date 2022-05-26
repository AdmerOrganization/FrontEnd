package com.example.tolearn.webService;

import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.ExamNew;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.Entity.Homework_result;
import com.example.tolearn.Entity.myClass;
import com.google.gson.JsonArray;
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

public interface ExamAPI {
    public static final String BASE_URL = "http://185.235.42.101:8000/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("exams/exams_create/")
    Call<JsonObject> ExamCreate(@Header("Authorization") String user_token,@Body Exam examInfo);

    @PUT("exams/exams_edit/")
    Call<JsonObject> ExamUpdate(@Header("Authorization") String user_token,@Body Exam examInfo);

    @GET("exams/exams_create/")
    Call<List<ExamNew>> GetAllExams(@Header("Authorization") String user_token);

    @POST("exams/exams_retrieve/")
    Call<List<ExamNew>> GetAllExamsForClass(@Header("Authorization") String user_token,@Body JsonObject classroomID);

    @POST("exams/exams_retrieve/")
    Call<JsonArray> GetExamDetails (@Header("Authorization") String user_token, @Body JsonObject examId);
//    @POST("homeworks/list/")
//    Call<List<Homework>> GetAllHomework(@Header("Authorization") String user_token,@Body JsonObject classInfo);
//    @Multipart
//    @POST("homeworks/create/")
//    Call<Homework> Create(@Header("Authorization") String user_token , @Part("title") RequestBody titleR,
//                           @Part("description")RequestBody descR,
//                           @Part("deadline")RequestBody dateR,
//                           @Part("classroom") Integer class_id,
//                           @Part MultipartBody.Part file);
//
//    @POST("homeworks/display/")
//    Call<Homework> Display(@Header("Authorization") String user_token , @Body JsonObject class_token);
//    @Multipart
//    @POST("homeworks/edit/")
//    Call<Homework> Edit(@Header("Authorization") String user_token ,
//                          @Part("homework_token") RequestBody homeworkR,
//                          @Part("title") RequestBody titleR,
//                          @Part("description")RequestBody descR,
//                          @Part("deadline")RequestBody dateR,
//                          @Part MultipartBody.Part file);
//    @Multipart
//    @POST("homeworks/answer/")
//    Call<JsonObject> Submit(@Header("Authorization") String user_token ,
//                            @Part("homework") Integer homework_id,
//                            @Part MultipartBody.Part file);
//
//    @POST("homeworks/list_answer/")
//    Call<List<Homework_result>> homework_answers(@Header("Authorization") String user_token,@Body JsonObject homework_id);
}
