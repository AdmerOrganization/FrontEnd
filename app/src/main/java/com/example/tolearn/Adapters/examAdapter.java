package com.example.tolearn.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.tolearn.AlertDialogs.HomeworkEditDialog;
import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.ExamNew;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.ExamUpdate;
import com.example.tolearn.Homework_results;
import com.example.tolearn.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        SharedPreferences shp2 = context.getSharedPreferences("classId",context.MODE_PRIVATE);
        String access = shp2.getString("user_access","");
        if(access.equals("student"))
        {
            editBtn.setClickable(false);
            editBtn.setVisibility(View.INVISIBLE);
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
        return view;
    }

}
