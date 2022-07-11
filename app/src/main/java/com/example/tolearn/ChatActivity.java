package com.example.tolearn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tolearn.Adapters.chatAdapter;
import com.example.tolearn.Entity.message;
import com.example.tolearn.webService.UserAPI;
import com.example.tolearn.webService.chatAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity  extends AppCompatActivity {

    chatAPI chat_api;
    JsonObject class_token;
    String class_token_str;
    WebSocketListener webSocketListener;
    List<message> messagesList;
    com.example.tolearn.Adapters.chatAdapter chatAdapter;
    ListView  messagesListView ;
    EditText my_text;
    TextView className;
    ImageView classImage;
    WebSocket ws;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        getSupportActionBar().hide();

        my_text = findViewById(R.id.my_message_text);
        className = findViewById(R.id.className);
        classImage = findViewById(R.id.classImage);

        messagesList = new ArrayList<>();
        messagesListView = findViewById(R.id.messages_view);

        SharedPreferences shP2 = getSharedPreferences("classId", MODE_PRIVATE);
        String classId = shP2.getString("Id","");
        String classCategory = shP2.getString("category","");
        String classTitle = shP2.getString("class_name","");
        class_token_str = shP2.getString("classroom_token","");
        class_token_str = class_token_str.replace("\"","");
        className.setText(classTitle);

        switch (classCategory)
        {
            case "Math":
                classImage.setImageResource(R.drawable.math);
                break;
            case "Chemistry":
                classImage.setImageResource(R.drawable.chemistry);
                break;
            case "Physics":
                classImage.setImageResource(R.drawable.atom);
                break;
            case "Engineering":
                classImage.setImageResource(R.drawable.engineering);
                break;
            case "Paint":
                classImage.setImageResource(R.drawable.paint);
                break;
            case "Music":
                classImage.setImageResource(R.drawable.musical);
                break;
            case "Cinema":
                classImage.setImageResource(R.drawable.clapperboard);
                break;
            case "athletic":
                classImage.setImageResource(R.drawable.athletics);
                break;
            case "computer science":
                classImage.setImageResource(R.drawable.responsive);
                break;
            case "language":
                classImage.setImageResource(R.drawable.languages);
                break;
            default:
                //Picasso.get().load(avatar).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(classImage);
                break;
        }


        OkHttpClient client = new OkHttpClient();

        Retrofit chatretro = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chat_api =chatretro.create(chatAPI.class);

        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        String token = shP.getString("token","");


        Request request = new Request.Builder().url("ws://185.235.42.101:8000/ws/chat/"+class_token_str+"/")
                .addHeader("Authorization","token "+token)
                .build();

        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.i("WebSocketListener","closed");
                Log.i("WebSockerListener",reason);
            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.i("WebSocketListener","closing");
                Log.i("WebSockerListener",reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                try {
                    Log.i("WebSocketListener",t.getMessage());
                    Log.i("WebSocketListener",response.message());
                }
                catch (Exception exception)
                {
                    //nothing
                }
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                final boolean[] firstTime = {true};
                Thread newThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject js = new JsonObject();
                        JsonParser parser = new JsonParser();
                        js = (JsonObject) parser.parse(text);

                        try {
                            JsonArray jsonArray = (JsonArray) js.get("messages");
                            for (int i= 0 ; i<jsonArray.size();i++)
                            {
                                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                                String avatar = jsonObject.get("avatar").toString();
                                String id = jsonObject.get("id").toString();
                                String fname = jsonObject.get("fname").toString();
                                fname = fname.replace("\"","");
                                String lname = jsonObject.get("lname").toString();
                                lname = lname.replace("\"","");
                                String message = jsonObject.get("message").toString();
                                message = message.replace("\"","");
                                String timeStamp = jsonObject.get("timestamp").toString();
                                message newMessage = new message(id , fname , lname , message  , timeStamp,avatar);
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
                        } catch (Exception exception)
                        {
                            String fname = js.get("fname").toString();
                            fname = fname.replace("\"","");

                            String lname = js.get("lname").toString();
                            lname = lname.replace("\"","");

                            String message = js.get("message").toString();
                            message = message.replace("\"","");

                            String timestamp = js.get("timestamp").toString();

                            String avatar = js.get("avatar").toString();

                            message newMessage = new message("",fname,lname,message,timestamp,avatar);
                            messagesList.add(newMessage);

                            chatAdapter = new chatAdapter(messagesList,ChatActivity.this,false);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    messagesListView.setAdapter(chatAdapter);
                                    chatAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                        chatAdapter = new chatAdapter(messagesList,ChatActivity.this,false);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (firstTime[0] == true)
                                {
                                    messagesListView.setAdapter(chatAdapter);
                                    firstTime[0] = false;
                                }
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
        my_text.setText("");
        String sendMessageText="{\n" +
                "    \"message\":\" " + myMessage + "\",\n" +
                "    \"user_token\":\""+token+"\",\n" +
                "    \"token\":\""+class_token_str+"\"\n" +
                "}";
        ws.send(sendMessageText);
        Log.i("CHATTTTTTTTT",sendMessageText);
        String fname = shP.getString("firstname","");
        String lname = shP.getString("lastname","");
        message newMessage = new message("0",fname,lname,myMessage,"","");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ws.close(4000,"bye");
    }
}
