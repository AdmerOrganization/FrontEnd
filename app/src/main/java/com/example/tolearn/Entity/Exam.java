package com.example.tolearn.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Exam {
    @SerializedName("questions_count")
    int questions_count;
    @SerializedName("name")
    String name;
    List<question> questionList;
    @SerializedName("data")
    List<List<Object>> data;
    @SerializedName("start_time")
    String StartDate;
    @SerializedName("finish_time")
    String EndDate;
    public Exam (List<question> list, String sd , String ed , int qc ,String name)
    {
        data = new ArrayList<>();
        questionList = list;
        for (int i = 0 ; i < questionList.size();i++){
            data.add(new ArrayList<>());
            data.get(i).add(i+1);
            data.get(i).add(questionList.get(i).getQuestion());
            data.get(i).add(questionList.get(i).getAnswer1());
            data.get(i).add(questionList.get(i).getAnswer2());
            data.get(i).add(questionList.get(i).getAnswer3());
            data.get(i).add(questionList.get(i).getAnswer4());
            data.get(i).add(Integer.parseInt(questionList.get(i).getRigh_ans()));
        }
        StartDate = sd;
        EndDate = ed;
        questions_count = qc;
        this.name = name;
    }
}
