package com.example.tolearn.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.CustomeConfirmAlertDialog;
import com.example.tolearn.AlertDialogs.HomeworkEditDialog;
import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.ExamNew;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.ExamStart;
import com.example.tolearn.ExamUpdate;
import com.example.tolearn.Homework_results;
import com.example.tolearn.R;
import com.example.tolearn.StudentsExamResults;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDateTime;

public class examAdapter extends BaseAdapter{

    private Context context;
    private List<JsonObject> list;
    private List<JsonObject> temp;
    String type;

    public examAdapter(Context context, JsonArray list, String type) {
        this.context = context;
        int i  =0;
        this.list = new ArrayList<>();
        this.temp = new ArrayList<>();
        for( i= 0;i<list.size();i++)
        {
            JsonObject newExam = list.get(i).getAsJsonObject();
            this.list.add(newExam);
            this.temp.add(newExam);
        }
        this.type = type;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.exam_item_view,null);
        }

        ExamNew currentExam = new ExamNew(list.get(i));
        TextView title = view.findViewById(R.id.homeworkTextview);
        TextView deadline = view.findViewById(R.id.deadlineTextview);
        TextView scoreView = view.findViewById(R.id.scoreText);
        ImageView scoreImg = view.findViewById(R.id.score);
        String scoreStr = currentExam.getScore().replace("\"","");

        SharedPreferences sharedPreferences2 = context.getSharedPreferences("classId",context.MODE_PRIVATE);
        String access = sharedPreferences2.getString("user_access","");

        if (access.equals("teacher")) {
            scoreView.setVisibility(View.INVISIBLE);
            scoreImg.setVisibility(View.INVISIBLE);
        }

        if(scoreStr.equals(""))
        {
            scoreStr = "N/A";
        }
        if(scoreStr.length()==2)
        {
            scoreStr = "0"+scoreStr;
        }
        scoreView.setText(scoreStr);
        title.setText("  "+ currentExam.getName());
        String start = currentExam.getStartDate().replace("T"," ");
        String end = currentExam.getEndDate().replace("T"," ");
        start = start.replace(":00Z","\n");
        end = end.replace(":00Z","");
        start = "  "+start;
        deadline.setText(start + "  " + end);
        Button submit = view.findViewById(R.id.SubmitBtn);
        Button editBtn = view.findViewById(R.id.editBtn);
        Button resultsBtn = view.findViewById(R.id.resultBtn);
        if(access.equals("student"))
        {
            editBtn.setClickable(false);
            editBtn.setVisibility(View.INVISIBLE);
            resultsBtn.setClickable(false);
            resultsBtn.setVisibility(View.INVISIBLE);
        }
        else{
            submit.setText("results");
            resultsBtn.setClickable(false);
            resultsBtn.setVisibility(View.INVISIBLE);
        }


        String id = currentExam.getId().toString();
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExamUpdate = new Intent(context, ExamUpdate.class);
                goToExamUpdate.putExtra("id",id);
                goToExamUpdate.putExtra("question_count",String.valueOf(currentExam.getQuestions_count()));
                goToExamUpdate.putExtra("name",currentExam.getName());
                goToExamUpdate.putExtra("start_time",currentExam.getStartDate());
                goToExamUpdate.putExtra("end_time",currentExam.getEndDate());
                context.startActivity(goToExamUpdate);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(access.equals("student")) {
                    String StartDate = currentExam.getStartDate();

                    CustomeConfirmAlertDialog confirmAlertDialog = new CustomeConfirmAlertDialog(context,"Exam","do you want to start this exam ?");
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
                            if(ExamTimeChecker(currentExam.getStartDate(),currentExam.getEndDate()))
                            {
                                Intent goToExam = new Intent(context, ExamStart.class);
                                goToExam.putExtra("examId",id);
                                goToExam.putExtra("startDate",currentExam.getStartDate());
                                goToExam.putExtra("endDate",currentExam.getEndDate());
                                context.startActivity(goToExam);
                                confirmAlertDialog.alertDialog.dismiss();
                            }
                            else{
                                CustomeAlertDialog alertDialog = new CustomeAlertDialog(context,"Error","Exam has not been started yet or has been finished!");
                                alertDialog.imageView.setImageResource(R.drawable.error);
                                alertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        confirmAlertDialog.alertDialog.dismiss();
                                        alertDialog.alertDialog.dismiss();
                                    }
                                });
                            }
                        }
                    });
                }
                else{
                    if(ResultTimeChecker(currentExam.getEndDate()))
                    {
                        Intent goToResultActivity = new Intent(context , StudentsExamResults.class);
                        goToResultActivity.putExtra("exam_id",Integer.toString(currentExam.getId()));
                        context.startActivity(goToResultActivity);
                    }
                    else{
                        Toast.makeText(context, "the exam has not ended yet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    public boolean ResultTimeChecker (String endDate)
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

        String currentMoment = String.valueOf(currentHour)+String.valueOf(currentMinute);
        int currentMomentInt = Integer.parseInt(currentMoment);

        String [] endDateTimeInfo = endDate.split("T");
        String [] endDateInfo = endDateTimeInfo[0].split("-");
        int endYear = Integer.parseInt(endDateInfo[0]);
        int endMonth = Integer.parseInt(endDateInfo[1]);
        int endDay = Integer.parseInt(endDateInfo[2]);

        String [] endTimeInfo = endDateTimeInfo[1].split(":");
        int endHour = Integer.parseInt(endTimeInfo[0]);
        int endMinute = Integer.parseInt(endTimeInfo[1]);

        String endMoment = String.valueOf(endHour)+String.valueOf(endMinute);
        int endMomentInt = Integer.parseInt(endMoment);

        if(currentYear > endYear)
        {
            Log.i("TIME",currentDateTime +  "    " + endDate);
            return true;
        }
        else if(currentYear == endYear)
        {
            if(currentMonth > endMonth)
            {
                Log.i("TIME",currentDateTime +  "    " + endDate);
                return true;
            }
            else if ( currentMonth == endMonth)
            {
                if (currentDay > endDay)
                {
                    Log.i("TIME",currentDateTime +  "    " + endDate);
                    return true;
                }
                else if ( currentDay == endDay)
                {
                    if( currentMomentInt > endMomentInt)
                    {
                        Log.i("TIME",currentDateTime +  "    " + endDate);
                        return true;
                    }
                    else
                    {
                        Log.i("TIME",currentDateTime +  "    " + endDate);
                        return false;
                    }
                }
                else{
                    Log.i("TIME",currentDateTime +  "    " + endDate);
                    return false;
                }
            }
            else{
                Log.i("TIME",currentDateTime +  "    " + endDate);
                return false;
            }
        }
        else
        {
            Log.i("TIME",currentDateTime +  "    " + endDate);
            return false;
        }
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
}
