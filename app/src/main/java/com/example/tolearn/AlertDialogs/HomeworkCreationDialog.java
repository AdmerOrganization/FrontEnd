package com.example.tolearn.AlertDialogs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.tolearn.Controllers.homework_creation_validations;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class HomeworkCreationDialog extends Activity {
    public AlertDialog alertDialog;
    public EditText titleET;
    public EditText descET;
    public DatePicker datePicker;
    public Button fileSelection;
    public Button homeworkCreation;
    public homework_creation_validations Controller;

    public HomeworkCreationDialog(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.homework_creation_dialog,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        //init
        titleET = alertView.findViewById(R.id.titleET);
        descET = alertView.findViewById(R.id.descET);
        datePicker = alertView.findViewById(R.id.datePicker);
        fileSelection = alertView.findViewById(R.id.homeworkPdfSelection);
        homeworkCreation = alertView.findViewById(R.id.HomeworkCreate);

        fileSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set homework pdf .....
            }
        });

        homeworkCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                month = month + 1;
                int day = datePicker.getDayOfMonth();
                if(!Controller.IsTitleCorrect(titleET.getText().toString()))
                {
                    Toast.makeText(context, "Please enter a title for homework", Toast.LENGTH_SHORT).show();
                }
                else if(!Controller.IsDescCorrect(descET.getText().toString()))
                {
                    Toast.makeText(context, "Please enter a desc for homework", Toast.LENGTH_SHORT).show();
                }
                else if(!Controller.IsDateValid(year,month,day))
                {
                    Toast.makeText(context, "You can not select a date in the past.", Toast.LENGTH_SHORT).show();
                }
                else{
                    //connection to back for sending the info
                }
            }
        });
    }
}