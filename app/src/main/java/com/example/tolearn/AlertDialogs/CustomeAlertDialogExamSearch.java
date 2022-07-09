package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.R;
import com.example.tolearn.webService.ExamAPI;
import com.example.tolearn.webService.HomeworkAPI;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomeAlertDialogExamSearch {
    public AlertDialog alertDialog;
    public EditText search_bar;
    public ExamAPI examAPI;
    public List<Exam> filteredExams;
    public CustomeAlertDialogExamSearch(Context context, String token , String classId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.search_hw_by_title,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();
        search_bar = alertView.findViewById(R.id.hw_title_search_bar);
    }
}
