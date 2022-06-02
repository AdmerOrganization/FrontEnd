package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tolearn.Adapters.StudentExamQuestionsAdapter;
import com.example.tolearn.Entity.question;
import com.example.tolearn.webService.ExamAPI;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamStart extends AppCompatActivity {

    String user_token;
    ListView questionsListView;
    StudentExamQuestionsAdapter adapter;
    List<JsonObject> questionsList;
    List<String> answersList;
    int examId;
    ExamAPI examAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_start);
        init();
        fillList();
    }

    public void init()
    {
        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        user_token = shP.getString("token", "");

        questionsListView = findViewById(R.id.questionsListView);
        examId = -1;
        try {
            Intent getInfo = getIntent();
            examId = Integer.parseInt(getInfo.getStringExtra("examId"));
        }
        catch (Exception exception)
        {
            Toast.makeText(this, "There is a problem getting this exam ", Toast.LENGTH_SHORT).show();
            finish();
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit createHomeworkRetro = new Retrofit.Builder()
                .baseUrl(ExamAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        examAPI =createHomeworkRetro.create(ExamAPI.class);

        answersList = new ArrayList<>();
        questionsList = new ArrayList<>();
    }


    public void fillList()
    {
        JsonObject examIdJson = new JsonObject();
        examIdJson.addProperty("exam_info",Integer.valueOf(examId));

        Call<List<JsonObject>> GetAllQuestions = examAPI.GetExamQuestions("token "+user_token,examIdJson);
        GetAllQuestions.enqueue(new Callback<List<JsonObject>>() {
            @Override
            public void onResponse(Call<List<JsonObject>> call, Response<List<JsonObject>> response) {
                if(!response.isSuccessful())
                {
                    try {
                        //todo : the message format may not be as we expect for showing to the users
                        Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception exception)
                    {
                        Toast.makeText(ExamStart.this, "there is a problem with your connection", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    questionsList = response.body();
                    adapter = new StudentExamQuestionsAdapter(ExamStart.this,questionsList,answersList);
                    questionsListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<JsonObject>> call, Throwable t) {
                Toast.makeText(ExamStart.this, "there is a problem with your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void exam_end(View view) {
        
    }
}