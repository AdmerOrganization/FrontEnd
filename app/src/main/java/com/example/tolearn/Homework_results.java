package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tolearn.Adapters.homework_result_item_adapter;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.Entity.Homework_result;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Homework_results extends AppCompatActivity {
    HomeworkAPI homeworkAPI;
    String hw_title;
    String deadline;
    String hw_id;
    String userToken;

    TextView hw_title_tv;
    TextView hw_deadline_tv;
    ListView memberResultsView;

    List<Homework_result> resultsList ;
    homework_result_item_adapter resultsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_homework_results);
        init();

    }

    public void init()
    {
        getFromPrePage();
        retrofitConnection();
        loadPageViews();
    }


    public void getFromPrePage()
    {
        Intent getIntentInfo = getIntent();
        hw_title = getIntentInfo.getStringExtra("title");
        deadline = getIntentInfo.getStringExtra("deadline");
        hw_id = getIntentInfo.getStringExtra("id");

        SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
        userToken = sharedPreferences.getString("token","");
    }

    public void loadPageViews()
    {
        hw_title_tv = findViewById(R.id.hw_title_tv);
        hw_deadline_tv = findViewById(R.id.hw_deadline_tv);
        memberResultsView = findViewById(R.id.hw_result_list);

        hw_title_tv.setText(hw_title);
        hw_deadline_tv.setText(deadline);
        fillResultList();
    }

    public void retrofitConnection()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit LoginRetrofit = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI =LoginRetrofit.create(HomeworkAPI.class);
    }

    public void fillResultList()
    {
        JsonObject hw_id_json = new JsonObject();
        hw_id_json.addProperty("homework",hw_id);
        Call<List<Homework_result>> callBack = homeworkAPI.homework_answers("token "+userToken, hw_id_json );
        callBack.enqueue(new Callback<List<Homework_result>>() {
            @Override
            public void onResponse(Call<List<Homework_result>> call, Response<List<Homework_result>> response) {
                if(!response.isSuccessful())
                {
                    CustomeAlertDialog results = new CustomeAlertDialog(Homework_results.this,"Response Error","There is a problem with your internet connection");
                    results.btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }
                else{
                    resultsList = response.body();
                    resultsAdapter = new homework_result_item_adapter(Homework_results.this,resultsList,userToken);

                    memberResultsView.setAdapter(resultsAdapter);
                    if(resultsList.size() == 0)
                    {
                        memberResultsView.setVisibility(View.INVISIBLE);
                        ConstraintLayout no_item = findViewById(R.id.not_found);
                        no_item.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Homework_result>> call, Throwable t) {
                CustomeAlertDialog results = new CustomeAlertDialog(Homework_results.this,"Response Error","There is a problem with your internet connection");
                results.btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
    }
}