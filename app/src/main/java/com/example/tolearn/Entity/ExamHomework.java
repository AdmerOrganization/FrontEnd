package com.example.tolearn.Entity;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExamHomework {
    @SerializedName("homework")
    Homework homework;
    @SerializedName("exam")
    ExamNew exam ;

    public ExamHomework(Homework homework , ExamNew exam) {
        this.homework = homework;
        this.exam = exam;
    }

    public Homework getHomework() {
        return homework;
    }

    public ExamNew getExam() {
        return exam;
    }
}
