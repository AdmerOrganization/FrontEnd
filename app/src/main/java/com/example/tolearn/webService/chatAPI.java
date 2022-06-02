package com.example.tolearn.webService;

import android.text.Html;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface chatAPI {
    public static final String BASE_URL = "http://185.235.42.101:8000/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })


    @GET("chat/{id}/")
    Call<JsonObject> chatClassToken(@Header("Authorization") String user_token, @Path("id") int id);

}
