package com.example.tolearn.Entity;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExamHomework {
    @SerializedName("homework")
    Homework homework;
    @SerializedName("exam")
    Exam exam ;

    public ExamHomework(Homework homework , Exam exam) {
        this.homework = homework;
        this.exam = exam;
    }

    public Homework getHomework() {
        return homework;
    }

    public Exam getExam() {
        return exam;
    }
}
