package com.example.tolearn.Entity;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExamNew {
    @SerializedName("questions_count")
    int questions_count;
    @SerializedName("name")
    String name;
    List<question> questionList;
    @SerializedName("data")
    List<JsonObject> data;
    @SerializedName("start_time")
    String StartDate;
    @SerializedName("finish_time")
    String EndDate;
    List<JsonObject> data_2;
    public ExamNew(List<question> list, String sd , String ed , int qc , String name)
    {
        data = new ArrayList<JsonObject>();
        StartDate = sd;
        EndDate = ed;
        questions_count = qc;
        this.name = name;
    }


    public int getQuestions_count() {
        return questions_count;
    }

    public String getName() {
        return name;
    }

    public List<question> getQuestionList() {
        return questionList;
    }

    public List<JsonObject> getData() {
        return data;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }
}
