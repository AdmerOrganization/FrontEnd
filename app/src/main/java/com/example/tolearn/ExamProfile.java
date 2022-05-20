package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.Adapters.questionsAdapter;
import com.example.tolearn.AlertDialogs.CustomDatePicker;
import com.example.tolearn.AlertDialogs.CustomeTimePicker;
import com.example.tolearn.AlertDialogs.question_item_dialog;
import com.example.tolearn.Entity.question;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExamProfile extends AppCompatActivity {

    TextView startTimeTv;
    TextView endTimeTv;
    TextView examDate;
    ListView questionsListView;
    questionsAdapter questionsAdapter;
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
        startTimeTv = findViewById(R.id.startTimeET);
        endTimeTv = findViewById(R.id.EndTimeEt);
        examDate = findViewById(R.id.examDate);
        questionsListView = findViewById(R.id.questions);
        questionList = new ArrayList<>();
        questionsAdapter = new questionsAdapter(ExamProfile.this , questionList);
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
            String today = String.valueOf(currentYear) + "_" +String.valueOf(currentMonth) +"_"+String.valueOf(currentDay);
            Toast.makeText(this, today, Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, questionList.toString(), Toast.LENGTH_LONG).show();


    }
}