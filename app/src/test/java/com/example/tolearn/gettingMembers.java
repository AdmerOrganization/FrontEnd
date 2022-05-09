package com.example.tolearn;

import static org.junit.Assert.*;

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

public class gettingMembers {
    ClassAPI classAPI;
    boolean IsoK;

    public boolean gettingMembers(String token , String ClassId)
    {
        Retrofit classMembers = new Retrofit.Builder()
                .baseUrl(ClassAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI =classMembers.create(ClassAPI.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", ClassId);

        IsoK = false;
        Call<List<member>> callBack = classAPI.classMembers("token "+ token,jsonObject );
        callBack.enqueue(new Callback<List<member>>() {
            @Override
            public void onResponse(Call<List<member>> call, Response<List<member>> response) {
                if(!response.isSuccessful()){
                }
                else{
                    IsoK = true;
                }
            }

            @Override
            public void onFailure(Call<List<member>> call, Throwable t) {
            }
        });

        return IsoK;
    }

    @Test
    public void GettingMembersTest1()
    {
        String token = "1231dffjdffjdf";
        String Id = "1";
        boolean gettingMembers = gettingMembers(token , Id);
        assertFalse(gettingMembers);
    }

    @Test
    public void GettingMembersTest2()
    {
        String token = "1231dffjdffjdf";
        String Id = "2";
        boolean gettingMembers = gettingMembers(token , Id);
        assertFalse(gettingMembers);
    }


    @Test
    public void GettingMembersTest3()
    {
        String token = "1231dffjdffjdf";
        String Id = "3";
        boolean gettingMembers = gettingMembers(token , Id);
        assertFalse(gettingMembers);
    }


    @Test
    public void GettingMembersTest4()
    {
        String token = "1231dffjdffjdf";
        String Id = "15";
        boolean gettingMembers = gettingMembers(token , Id);
        assertFalse(gettingMembers);
    }


    @Test
    public void GettingMembersTest5() {
        String token = "1231dffjdffjdf";
        String Id = "60";
        boolean gettingMembers = gettingMembers(token, Id);
        assertFalse(gettingMembers);
    }
}
