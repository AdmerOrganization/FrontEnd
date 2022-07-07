package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.Adapters.ResultsAdapter;
import com.example.tolearn.webService.ExamAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentsExamResults extends AppCompatActivity {

    com.google.android.material.floatingactionbutton.FloatingActionButton backBtn;
    ListView resultsView;
    ExamAPI examAPI;
    ResultsAdapter resultsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_exam_results);
        getSupportActionBar().hide();
        init();

        Intent getInfo = getIntent();
        String examID = getInfo.getStringExtra("exam_id");

        connectToRetrofit();
        loadResults(examID);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void init()
    {
        resultsView = findViewById(R.id.results);
        backBtn = findViewById(R.id.BackBtn);
    }


    public void connectToRetrofit()
    {
        Retrofit Exams = new Retrofit.Builder()
                .baseUrl(ExamAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        examAPI = Exams.create(ExamAPI.class);
    }

    public void loadResults (String examID)
    {
        //getting the user token ...
        SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
        String user_token = sharedPreferences.getString("token","");

        //getting the class and exam tokens ....
        SharedPreferences sharedPreferences2 = getSharedPreferences("classId",MODE_PRIVATE);
        String classId = sharedPreferences2.getString("Id","");

        JsonObject js = new JsonObject();
        js.addProperty("exam_info",examID);
        js.addProperty("classroom_id",classId);

        Call<JsonArray> getResults = examAPI.GetStudentsReuslts("token "+user_token, js);
        getResults.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(StudentsExamResults.this, response.message(), Toast.LENGTH_LONG).show();
                }
                else{
                    resultsAdapter = new ResultsAdapter(StudentsExamResults.this, response.body());
                    resultsView.setAdapter(resultsAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(StudentsExamResults.this, "There is a problem with you internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}