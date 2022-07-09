package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.Adapters.StudentExamQuestionsAdapter;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.CustomeConfirmAlertDialog;
import com.example.tolearn.Entity.question;
import com.example.tolearn.webService.ExamAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ExamStart extends AppCompatActivity {

    TextView timerTv;
    String startDate;
    String endDate;
    String user_token;
    ListView questionsListView;
    StudentExamQuestionsAdapter adapter;
    List<JsonObject> questionsList;
    int examId;
    int [] answers;
    ExamAPI examAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_start);
        getSupportActionBar().hide();
        init();
        fillList();

    }

    public void init()
    {
        timerTv = findViewById(R.id.timerTextView);
        CustomeAlertDialog alertDialog = new CustomeAlertDialog(ExamStart.this,"Be careful!","Do Not close this page and Pay attention to your time");
        alertDialog.imageView.setImageResource(R.drawable.attention);
        alertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.alertDialog.dismiss();
            }
        });

        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        user_token = shP.getString("token", "");

        questionsListView = findViewById(R.id.questionsListView);
        examId = -1;
        try {
            Intent getInfo = getIntent();
            examId = Integer.parseInt(getInfo.getStringExtra("examId"));
            startDate = getInfo.getStringExtra("startDate");
            endDate = getInfo.getStringExtra("endDate");
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


        questionsList = new ArrayList<>();

        examStart();
    }

    public void examStart()
    {
        JsonObject jsonObject =  new JsonObject();
        jsonObject.addProperty("exam_info",examId);
        Call<String> callBack = examAPI.exam_start("token "+user_token, jsonObject);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                    //todo : if the exam start time has been expired...
                }
                else{
                    if(response.body().equals("Exam already started"))
                    {
                        CustomeAlertDialog alertDialog = new CustomeAlertDialog(ExamStart.this,"Error","You have already started the exam");;
                        alertDialog.imageView.setImageResource(R.drawable.error);
                        alertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.alertDialog.dismiss();
                                finish();
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ExamStart.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //start timer
        long timeleft = durationCalculator();
        new CountDownTimer(timeleft, 1000) {

            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                long minutesLeft = secondsLeft /60;
                secondsLeft = secondsLeft - (minutesLeft * 60);
                Log.i("TIMELEFT",Long.toString(minutesLeft) + " : "+ Long.toString(secondsLeft));
                timerTv.setText(Long.toString(minutesLeft) + ":"+ Long.toString(secondsLeft));
            }

            public void onFinish() {
                ExamEnded();
            }
        }.start();
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
                    answers = new int[questionsList.size()];
                    adapter = new StudentExamQuestionsAdapter(ExamStart.this,questionsList,answers);
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
        finishExam();
    }

    public void finishExam(){
        if(ExamTimeChecker(startDate,endDate))
        {
            CustomeConfirmAlertDialog confirmAlertDialog = new CustomeConfirmAlertDialog(ExamStart.this,"Exam","do you want to end ?");
            confirmAlertDialog.image.setImageResource(R.drawable.question);
            confirmAlertDialog.No.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmAlertDialog.alertDialog.dismiss();
                }
            });
            confirmAlertDialog.Yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String js = "{\"exam_info\":"+examId+",\"answers\":"+Arrays.toString(answers)+"}";
                    JsonParser parser = new JsonParser();

                    JsonObject json = (JsonObject) parser.parse(js);
                    Call<JsonArray> callBack = examAPI.exam_answer("token "+user_token,json);
                    callBack.enqueue(new Callback<JsonArray>() {
                        @Override
                        public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                            if(!response.isSuccessful())
                            {
                                Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //answers have been sent to the server
                                JsonObject js = new JsonObject();
                                js.addProperty("exam_info",examId);
                                Call<String> callFinish = examAPI.exam_finish("token "+user_token , js );
                                callFinish.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if(!response.isSuccessful())
                                        {
                                            Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(ExamStart.this, "exam finished", Toast.LENGTH_SHORT).show();


                                            Call<String> callBack = examAPI.exam_result("token "+user_token,js);
                                            callBack.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    if(!response.isSuccessful())
                                                    {
                                                        Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }
                                                    else{
                                                        String result = response.body().toString();

                                                        float grade = Float.parseFloat(result);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                    Toast.makeText(ExamStart.this, "There is a problem with your internet connection", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(ExamStart.this, "there is a problem with your connection", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonArray> call, Throwable t) {
                            Toast.makeText(ExamStart.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("exam answered",t.getMessage());
                        }
                    });

                    confirmAlertDialog.alertDialog.dismiss();
                }
            });
        }
        else{
            CustomeAlertDialog customeAlertDialog = new CustomeAlertDialog(ExamStart.this,"Error","Sorry ! time is up!");
            customeAlertDialog.imageView.setImageResource(R.drawable.error);
            customeAlertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customeAlertDialog.alertDialog.dismiss();
                    finish();
                }
            });
        }
    }

    public void ExamEnded(){

        String js = "{\"exam_info\":"+examId+",\"answers\":"+Arrays.toString(answers)+"}";
        JsonParser parser = new JsonParser();

        JsonObject json = (JsonObject) parser.parse(js);
        Call<JsonArray> callBack = examAPI.exam_answer("token "+user_token,json);
        callBack.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                else{
                    //answers have been sent to the server
                    JsonObject js = new JsonObject();
                    js.addProperty("exam_info",examId);
                    Call<String> callFinish = examAPI.exam_finish("token "+user_token , js );
                    callFinish.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(!response.isSuccessful())
                            {
                                Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ExamStart.this, "exam finished", Toast.LENGTH_SHORT).show();
                                CustomeConfirmAlertDialog confirmAlertDialog1 = new CustomeConfirmAlertDialog(ExamStart.this , "Time is up","Exam has been finished .Do you want to see your result right now ?");
                                confirmAlertDialog1.image.setImageResource(R.drawable.question);
                                confirmAlertDialog1.No.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //confirmAlertDialog1.alertDialog.dismiss();
                                        finish();
                                    }
                                });

                                confirmAlertDialog1.Yes.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Call<String> callBack = examAPI.exam_result("token "+user_token,js);
                                        callBack.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if(!response.isSuccessful())
                                                {
                                                    Toast.makeText(ExamStart.this, response.message(), Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                                else{
                                                    String result = response.body().toString();

                                                    float grade = Float.parseFloat(result);
                                                    if(grade == 100.0)
                                                    {
                                                        CustomeAlertDialog alertDialog = new CustomeAlertDialog(ExamStart.this,"Result","great work !! "+result.toString());
                                                        alertDialog.imageView.setImageResource(R.drawable.victory_start);
                                                        alertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                    else {
                                                        CustomeAlertDialog alertDialog = new CustomeAlertDialog(ExamStart.this,"Result",result.toString());
                                                        alertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                finish();
                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                Toast.makeText(ExamStart.this, "There is a problem with your internet connection", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(ExamStart.this, "there is a problem with your connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(ExamStart.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("exam answered",t.getMessage());
            }
        });
    }

    public boolean ExamTimeChecker (String startDate , String endDate)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTime = (dateFormat.format(cal.getTime()));

        String [] dateTime = currentDateTime.split(" ");
        String [] dateInfo = dateTime[0].split("/");
        String [] timeInfo = dateTime[1].split(":");

        int currentYear = Integer.parseInt(dateInfo[0]);
        int currentMonth = Integer.parseInt(dateInfo[1]);
        int currentDay = Integer.parseInt(dateInfo[2]);
        int currentHour = Integer.parseInt(timeInfo[0]);
        int currentMinute = Integer.parseInt(timeInfo[1]);

        String [] startDateTimeInfo = startDate.split("T");
        String [] startDateInfo = startDateTimeInfo[0].split("-");
        int startYear = Integer.parseInt(startDateInfo[0]);
        int startMonth = Integer.parseInt(startDateInfo[1]);
        int startDay = Integer.parseInt(startDateInfo[2]);

        String [] StartTimeInfo = startDateTimeInfo[1].split(":");
        int startHour = Integer.parseInt(StartTimeInfo[0]);
        int startMinute = Integer.parseInt(StartTimeInfo[1]);


        String [] endDateTimeInfo = endDate.split("T");
        String [] endDateInfo = endDateTimeInfo[0].split("-");
        int endYear = Integer.parseInt(endDateInfo[0]);
        int endMonth = Integer.parseInt(endDateInfo[1]);
        int endDay = Integer.parseInt(endDateInfo[2]);

        String [] endTimeInfo = endDateTimeInfo[1].split(":");
        int endHour = Integer.parseInt(endTimeInfo[0]);
        int endMinute = Integer.parseInt(endTimeInfo[1]);

        Log.i("DateTimeExam", String.valueOf(currentYear));
        Log.i("DateTimeExam", String.valueOf(currentMonth));
        Log.i("DateTimeExam", String.valueOf(currentDay));
        Log.i("DateTimeExam", String.valueOf(currentHour));
        Log.i("DateTimeExam", String.valueOf(currentMinute));

        Log.i("DateTimeExam", String.valueOf(startYear));
        Log.i("DateTimeExam", String.valueOf(startMonth));
        Log.i("DateTimeExam", String.valueOf(startDay));
        Log.i("DateTimeExam", String.valueOf(startHour));
        Log.i("DateTimeExam", String.valueOf(startMinute));

        Log.i("DateTimeExam", String.valueOf(endYear));
        Log.i("DateTimeExam", String.valueOf(endMonth));
        Log.i("DateTimeExam", String.valueOf(endDay));
        Log.i("DateTimeExam", String.valueOf(endHour));
        Log.i("DateTimeExam", String.valueOf(endMinute));


        String currentMoment = String.valueOf(currentHour)+String.valueOf(currentMinute);
        int currentMomentInt = Integer.parseInt(currentMoment);

        String startMoment = String.valueOf(startHour)+String.valueOf(startMinute);
        int startMomentInt = Integer.parseInt(startMoment);

        String endMoment = String.valueOf(endHour)+String.valueOf(endMinute);
        int endMomentInt = Integer.parseInt(endMoment);

        if(currentMomentInt >= startMomentInt && currentMomentInt <= endMomentInt)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public long durationCalculator ()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTime = (dateFormat.format(cal.getTime()));

        String [] dateTime = currentDateTime.split(" ");
        String [] dateInfo = dateTime[0].split("/");
        String [] timeInfo = dateTime[1].split(":");

        int currentYear = Integer.parseInt(dateInfo[0]);
        int currentMonth = Integer.parseInt(dateInfo[1]);
        int currentDay = Integer.parseInt(dateInfo[2]);
        int currentHour = Integer.parseInt(timeInfo[0]);
        int currentMinute = Integer.parseInt(timeInfo[1]);


        String [] endDateTimeInfo = endDate.split("T");
        String [] endDateInfo = endDateTimeInfo[0].split("-");
        int endYear = Integer.parseInt(endDateInfo[0]);
        int endMonth = Integer.parseInt(endDateInfo[1]);
        int endDay = Integer.parseInt(endDateInfo[2]);

        String [] endTimeInfo = endDateTimeInfo[1].split(":");
        int endHour = Integer.parseInt(endTimeInfo[0]);
        int endMinute = Integer.parseInt(endTimeInfo[1]);

        int hourDifference = endHour - currentHour;
        int minDifference = endMinute - currentMinute;

        long timeleft = hourDifference * 60 + minDifference;
        timeleft = timeleft*60;
        timeleft = timeleft * 1000;
        Log.i("timeleft",Long.toString(timeleft));
        //timeleft = timeleft + 59000;
        return timeleft;
    }
}