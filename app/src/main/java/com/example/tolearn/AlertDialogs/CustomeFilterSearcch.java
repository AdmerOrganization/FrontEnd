package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.example.tolearn.webService.ClassAPI;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomeFilterSearcch {
    public AlertDialog alertDialog;
    public EditText titleET;
    public EditText teacherET;
    public Button searchBtn;
    public ClassAPI classAPI;
    public List<myClass> filteredClasses;
    public CustomeFilterSearcch(Context context, List<myClass> classes)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.custome_class_filter_seach,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        filteredClasses = classes;
        titleET = alertView.findViewById(R.id.titleET);
        teacherET = alertView.findViewById(R.id.teacherET);
        searchBtn = alertView.findViewById(R.id.filterSearchBtn);

    }
}
