package com.example.tolearn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tolearn.webService.chatAPI;
import com.google.gson.JsonObject;
import com.example.tolearn.webService.chatAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chat extends AppCompatActivity  {
    chatAPI chat_api;
    JsonObject class_token;
    String class_token_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        getSupportActionBar().hide();
        init();
    }

    public void init()
    {
        SharedPreferences shP = getSharedPreferences("classId", MODE_PRIVATE);
        String id = shP.getString("Id", "");
        int classId = Integer.parseInt(id);
        SharedPreferences shP2 = getSharedPreferences("userInformation", MODE_PRIVATE);
        String token = shP2.getString("token", "");
        Call<JsonObject> info = chat_api.chatClassToken("token "+token,classId);
        info.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(chat.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                else{
                    class_token = response.body();
                    //String chat_info = class_token.getAsString();
                    //todo
                    class_token_str = "8-zeL_h-xlwPhuONTrsqZQ";
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(chat.this, "There is a problem with your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
