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

import com.example.tolearn.Adapters.chatAdapter;
import com.example.tolearn.Entity.message;
import com.example.tolearn.webService.chatAPI;
import com.google.gson.JsonObject;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class chat extends AppCompatActivity  {
    chatAPI chat_api;
    JsonObject class_token;
    String class_token_str;
    WebSocketListener webSocketListener;
    List<message> messagesList;
    com.example.tolearn.Adapters.chatAdapter chatAdapter;
    ListView  messagesListView ;
    WebSocket ws;
    EditText my_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        getSupportActionBar().hide();

        messagesList = new ArrayList<>();

        messagesListView = findViewById(R.id.messages_view);

        String class_token = "8-zeL_h-xlwPhuONTrsqZQ";
        class_token_str = class_token;

        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        String token = shP.getString("token","");

        my_text = findViewById(R.id.my_message_text);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://185.235.42.101:8000/ws/chat/"+class_token+"/")
                .addHeader("Authorization","token "+token)
                .build();
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
                try {
                    Log.i("WebSocketListener",response.message());
                    Log.i("WebSocketListener",t.getMessage());
                }
                catch (Exception exception)
                {
                    Log.i("WebSocketListener","failure");
                    webSocket.close(4000,"failed");
                }
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);

                int start = text.indexOf("[");
                int end = text.indexOf("]");

                String messages = text.substring(start,end);
                messages = messages.replace(" ","");
                messages = messages.replace("[","");
                messages = messages.replace("]","");
                messages = messages.replace("\"","");
                messages = messages.replace("},{","|");
                messages = messages.replace("{","");
                messages = messages.replace("}","");

                String [] messagesArray = messages.split("|");

                for ( int i =0;i<messagesArray.length;i++)
                {
                    String [] messageInfo = messagesArray[i].split(",");
                    String [] id = messageInfo[0].split(":");
                    String [] fname = messageInfo[1].split(":");
                    String [] lname = messageInfo[2].split(":");
                    String [] message = messageInfo[3].split(":");
                    String [] timeStamps = messageInfo [4].split(":");

                    message newMessage = new message(id[1],fname[1],lname[1],message[1],timeStamps[1]);
                    messagesList.add(newMessage);
                }

                chatAdapter = new chatAdapter(messagesList,chat.this,false);
                messagesListView.setAdapter(chatAdapter);
                chatAdapter.notifyDataSetChanged();

                Log.i("WebSocketListener",messages);
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
                Log.i("WebSocketListener",Integer.toString(response.code()));
                Log.i("WebSocketListener",response.body().toString());
                Log.i("WebSocketListener",response.protocol().toString());
                Log.i("WebSocketListener",response.request().toString());
            }
        };
        ws = client.newWebSocket(request,webSocketListener);

        //ws.close(4000,"bye");
    }

    public void send(View view) {
        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        String token = shP.getString("token","");
        String myMessage = my_text.getText().toString();
        ws.send("{\n" +
                "    \"message\":\" " + myMessage + "\",\n" +
                "    \"user_token\":\""+token+"\",\n" +
                "    \"token\":\""+class_token_str+"\"\n" +
                "}");

        String fname = shP.getString("firstname","");
        String lname = shP.getString("lastname","");
        message newMessage = new message("0",fname,lname,myMessage,"");
        messagesList.add(newMessage);
        chatAdapter = new chatAdapter(messagesList,chat.this,false);
        messagesListView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ws.close(4000,"bye");
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
