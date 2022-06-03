package com.example.tolearn;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tolearn.webService.chatAPI;
import com.google.gson.JsonObject;

import android.util.Log;

import java.net.URI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import tech.gusavila92.websocketclient.WebSocketClient;

public class chat extends AppCompatActivity  {
    chatAPI chat_api;
    JsonObject class_token;
    String class_token_str;
    WebSocketListener webSocketListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        getSupportActionBar().hide();

        String class_token = "8-zeL_h-xlwPhuONTrsqZQ";

        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        String token = shP.getString("token","");


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://185.235.42.101:8000/ws/chat/"+class_token+"/").build();
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.i("WebSocketListener","closed");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //todo
                    }
                });
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.i("WebSocketListener","closing");
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                Log.i("WebSocketListener",response.message());
                Log.i("WebSocketListener",t.getMessage());
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);

                Log.i("WebSocketListener",text);
            }


            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Log.i("WebSocketListener","closed");
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
                Log.i("WebSocketListener",response.message());
            }
        };
        WebSocket ws = client.newWebSocket(request,webSocketListener);
        ws.send("{\n" +
                "    \"message\":\"helloo123ooo\",\n" +
                "    \"user_token\":\"8811e281721d792fbf30757b0ab0e261e5eef7865e37d501ccc9048ea54d9fbb\",\n" +
                "    \"token\":\"8-zeL_h-xlwPhuONTrsqZQ\"\n" +
                "}");
    }
}




//        Call<JsonObject> info = chat_api.chatClassToken("token "+token,classId);
//        info.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if(!response.isSuccessful())
//                {
//                    Toast.makeText(chat.this, response.message(), Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    class_token = response.body();
//                    //String chat_info = class_token.getAsString();
//                    //todo
//                    class_token_str = "8-zeL_h-xlwPhuONTrsqZQ";
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(chat.this, "There is a problem with your connection", Toast.LENGTH_SHORT).show();
//            }
//        });
