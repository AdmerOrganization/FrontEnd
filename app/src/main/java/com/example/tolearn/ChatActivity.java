package com.example.tolearn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tolearn.Adapters.chatAdapter;
import com.example.tolearn.Entity.message;
import com.example.tolearn.webService.chatAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatActivity  extends AppCompatActivity {

    chatAPI chat_api;
    JsonObject class_token;
    String class_token_str;
    WebSocketListener webSocketListener;
    List<message> messagesList;
    com.example.tolearn.Adapters.chatAdapter chatAdapter;
    ListView  messagesListView ;
    EditText my_text;
    WebSocket ws;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        getSupportActionBar().hide();

        my_text = findViewById(R.id.my_message_text);

        messagesList = new ArrayList<>();
        messagesListView = findViewById(R.id.messages_view);

        String class_token = "8-zeL_h-xlwPhuONTrsqZQ";
        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        String token = shP.getString("token","");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://185.235.42.101:8000/ws/chat/"+class_token+"/")
                .addHeader("Authorization","token "+token)
                .build();

        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.i("WebSocketListener","closed");
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.i("WebSocketListener","closing");
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                Log.i("WebSocketListener",t.getMessage());
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);

                Thread newThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject js = new JsonObject();
                        JsonParser parser = new JsonParser();
                        js = (JsonObject) parser.parse(text);

                        JsonArray jsonArray = (JsonArray) js.get("messages");
                        for (int i= 0 ; i<jsonArray.size();i++)
                        {
                            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                            String id = jsonObject.get("id").toString();
                            String fname = jsonObject.get("fname").toString();
                            fname = fname.replace("\"","");
                            String lname = jsonObject.get("lname").toString();
                            lname = lname.replace("\"","");
                            String message = jsonObject.get("message").toString();
                            message = message.replace("\"","");
                            String timeStamp = jsonObject.get("timestamp").toString();
                            message newMessage = new message(id , fname , lname , message  , timeStamp);
                            messagesList.add(newMessage);
                        }

                        chatAdapter = new chatAdapter(messagesList,ChatActivity.this,false);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messagesListView.setAdapter(chatAdapter);
                                chatAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                newThread.start();

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
                Log.i("WebSocketListener",Integer.toString(response.code()));
                Log.i("WebSocketListener",response.body().toString());
                Log.i("WebSocketListener",response.protocol().toString());
                Log.i("WebSocketListener",response.request().toString());
            }

        };
        ws = client.newWebSocket(request,webSocketListener);
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
        chatAdapter = new chatAdapter(messagesList,ChatActivity.this,false);
        messagesListView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ws.close(4000,"bye");
    }
}
