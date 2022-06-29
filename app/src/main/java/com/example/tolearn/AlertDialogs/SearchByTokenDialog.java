package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.example.tolearn.webService.ClassAPI;
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

import java.util.List;

public class SearchByTokenDialog {
    public AlertDialog alertDialog;
    public EditText tokenET;
    public Button searchBtn;
    public ClassAPI classAPI;
    public List<myClass> filteredClasses;
    public SearchByTokenDialog(Context context, List<myClass> classes)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.search_by_token_dialog,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        filteredClasses = classes;
        tokenET = alertView.findViewById(R.id.tokenET);
        searchBtn = alertView.findViewById(R.id.searchByTokenBtn);

    }
}
