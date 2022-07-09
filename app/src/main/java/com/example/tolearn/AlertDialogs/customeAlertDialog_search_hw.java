package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.Entity.Homework;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;

import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class customeAlertDialog_search_hw {
    public AlertDialog alertDialog;
    public EditText search_bar;
    public HomeworkAPI homeworkAPI;
    public List<Homework> filteredHomeworks;
    public customeAlertDialog_search_hw(Context context, List<Homework> homeworks, String token , String classId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.search_hw_by_title,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        filteredHomeworks = homeworks;
        search_bar = alertView.findViewById(R.id.hw_title_search_bar);

        search_bar.setHint("search hw title ...");

        Retrofit hw_search = new Retrofit.Builder()
                .baseUrl(homeworkAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI = hw_search.create(HomeworkAPI.class);
    }
}
