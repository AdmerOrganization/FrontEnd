package com.example.tolearn;

import android.util.Log;
import static org.junit.Assert.*;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.Entity.Homework;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.tolearn.webService.HomeworkAPI;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.tolearn.Adapters.membersAdapter;
import com.example.tolearn.Controllers.homework_creation_validations;
import com.example.tolearn.Entity.member;
import com.example.tolearn.webService.ClassAPI;
import com.google.gson.JsonObject;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import org.junit.Test;

public class HomeworksGet {
    HomeworkAPI homeworkAPI;
    boolean IsOk;
    public boolean HomeworkGetAPI(String class_id, String user_token){
        Retrofit Homeworks = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI = Homeworks.create(HomeworkAPI.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("classroom",class_id);
        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+user_token,jsonObject);

        callBack.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                IsOk = true;
            }

            @Override
            public void onFailure(Call<List<Homework>> call, Throwable t) {
                IsOk = false;
            }
        });
        return IsOk;
    }

    @Test
    public void HomeworkGetAPI1()
    {
        boolean homeworksApi = HomeworkGetAPI("100","1234");
        assertFalse(homeworksApi);
    }

    @Test
    public void HomeworkGetAPI2()
    {
        boolean homeworksApi = HomeworkGetAPI("60","1234");
        assertFalse(homeworksApi);
    }


    @Test
    public void HomeworkGetAPI3()
    {
        boolean homeworksApi = HomeworkGetAPI("1","1234");
        assertFalse(homeworksApi);
    }


    @Test
    public void HomeworkGetAPI4()
    {
        boolean homeworksApi = HomeworkGetAPI("5","1234");
        assertFalse(homeworksApi);
    }

    @Test
    public void HomeworkGetAPI5()
    {
        boolean homeworksApi = HomeworkGetAPI("100","6171384a644f073aec65675c3709c288325d45035ee8970155b7ddf072155821");
        assertFalse(homeworksApi);
    }

    @Test
    public void HomeworkGetAPI6()
    {
        boolean homeworksApi = HomeworkGetAPI("60","6171384a644f073aec65675c3709c288325d45035ee8970155b7ddf072155821");
        assertFalse(homeworksApi);
    }


    @Test
    public void HomeworkGetAPI7()
    {
        boolean homeworksApi = HomeworkGetAPI("1","6171384a644f073aec65675c3709c288325d45035ee8970155b7ddf072155821");
        assertFalse(homeworksApi);
    }


    @Test
    public void HomeworkGetAPI8()
    {
        boolean homeworksApi = HomeworkGetAPI("3","6171384a644f073aec65675c3709c288325d45035ee8970155b7ddf072155821");
        assertFalse(homeworksApi);
    }
}
