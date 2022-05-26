package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.Adapters.questionsAdapter;
import com.example.tolearn.AlertDialogs.CustomDatePicker;
import com.example.tolearn.AlertDialogs.CustomeTimePicker;
import com.example.tolearn.AlertDialogs.HomeworkCreationDialog;
import com.example.tolearn.AlertDialogs.question_item_dialog;
import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.Entity.question;
import com.example.tolearn.webService.ExamAPI;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamProfile extends AppCompatActivity {

    TextView startTimeTv;
    TextView endTimeTv;
    TextView examDate;
    EditText titleET;
    ExamAPI examAPI;
    ListView questionsListView;
    questionsAdapter questionsAdapter;

    public List<question> getQuestionList() {
        return questionList;
    }

    List<question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_profile);
        getSupportActionBar().hide();
        init();
    }
    public void init()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit createHomeworkRetro = new Retrofit.Builder()
                .baseUrl(ExamAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        examAPI =createHomeworkRetro.create(ExamAPI.class);
        titleET = findViewById(R.id.titleEt);
        startTimeTv = findViewById(R.id.startTimeET);
        endTimeTv = findViewById(R.id.EndTimeEt);
        examDate = findViewById(R.id.examDate);
        questionsListView = findViewById(R.id.questions);
        questionList = new ArrayList<>();
        questionsAdapter = new questionsAdapter(ExamProfile.this , questionList , questionsListView );
        questionsListView.setAdapter(questionsAdapter);
    }

    public void StartTimeSelection(View view) {
        if(examDate.getText().toString().length()>0)
        {
            String date = examDate.getText().toString();
            Calendar c = Calendar.getInstance();
            int currentDay = c.get(Calendar.DAY_OF_MONTH);
            int currentMonth = c.get(Calendar.MONTH);
            currentMonth = currentMonth + 1;
            int currentYear = c.get(Calendar.YEAR);
            String yearStr = String.valueOf(currentYear);
            String monthStr = String.valueOf(currentMonth);
            String dayStr = String.valueOf(currentDay);
            if(monthStr.length()==1)
            {
                monthStr = "0"+monthStr;
            }
            if(dayStr.length()==1)
            {
                dayStr = "0"+dayStr;
            }
            String today = yearStr + "_" +monthStr +"_"+dayStr;

            if(date.equals(today))
            {
                CustomeTimePicker startTimePicker = new CustomeTimePicker(ExamProfile.this,startTimeTv,true);
            }
            else{
                CustomeTimePicker startTimePicker = new CustomeTimePicker(ExamProfile.this,startTimeTv,false);
            }
        }
        else{
            Toast.makeText(this, "first enter the exam date", Toast.LENGTH_SHORT).show();
        }
    }

    public void EndTimeSelection(View view) {
        if(startTimeTv.getText().toString().length()==0)
        {
            Toast.makeText(this, "first enter the start time", Toast.LENGTH_SHORT).show();
        }
        else{
            if(examDate.getText().toString().length()>0)
            {
                String date = examDate.getText().toString();
                Calendar c = Calendar.getInstance();
                int currentDay = c.get(Calendar.DAY_OF_MONTH);
                int currentMonth = c.get(Calendar.MONTH);
                currentMonth = currentMonth + 1;
                int currentYear = c.get(Calendar.YEAR);

                String startTime = startTimeTv.getText().toString();
                String [] arr = startTime.split(":");
                int startHour = Integer.valueOf(arr[0]);
                int startMin = Integer.valueOf(arr[1]);
                String today = String.valueOf(currentYear) + "_" +String.valueOf(currentMonth) +"_"+String.valueOf(currentDay);
                if(date.equals(today))
                {
                    CustomeTimePicker endTimePicker = new CustomeTimePicker(ExamProfile.this,endTimeTv,true , startHour  , startMin);
                }
                else{
                    CustomeTimePicker endTimePicker = new CustomeTimePicker(ExamProfile.this,endTimeTv,false , startHour  , startMin);
                }
            }
            else{
                Toast.makeText(this, "first enter the exam date", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void dateSelection(View view) {
        CustomDatePicker examDatePicker = new CustomDatePicker(ExamProfile.this,examDate);
    }

    public void addQuestion(View view) {
        int counter = questionList.size() + 1;
        question_item_dialog newQuestion = new question_item_dialog(ExamProfile.this,String.valueOf(counter),questionList,questionsAdapter,questionsListView);

    }

    public void createExam(View view) {
        //todo connection to back ....
        // all of the questions are in questionsList
        //question and exam entities are created
        if(!titleET.getText().toString().equals("")) {
            String examD;
            examD = examDate.getText().toString().replaceAll("_","-");
            SharedPreferences shP = getSharedPreferences("classId", MODE_PRIVATE);
            String id = shP.getString("Id", "");
            int classroom_id = Integer.parseInt(id);
            Exam myExam = new Exam(questionList,examD+" "+startTimeTv.getText().toString()+":00",examD+" "+endTimeTv.getText().toString()+":00",questionList.size(),titleET.getText().toString(),classroom_id);
            SharedPreferences shP2 = getSharedPreferences("userInformation", MODE_PRIVATE);
            String token = shP2.getString("token", "");
            Call<JsonObject> examCreateCall = examAPI.ExamCreate("token " + token,myExam);
            examCreateCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(ExamProfile.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                        Log.i("MOSHKEL", response.message());
                    } else {
                        String code = Integer.toString(response.code());
                        JsonObject Response = response.body();
                        Log.i("PHOTO", "SUCCED");
                        //                           Log.i("IMAGE URL",user.getAvatar().toString());
                        Toast.makeText(ExamProfile.this, "Exam created!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.i("moshkel","injas");
                    Log.i("Moshkel",t.getMessage());
                    Toast.makeText(ExamProfile.this, "error is :" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
        else{
            Toast.makeText(this,"Title is Empty",Toast.LENGTH_LONG).show();
        }
    }
}