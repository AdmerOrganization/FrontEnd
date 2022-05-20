package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.R;

import java.util.Calendar;

public class CustomeTimePicker {
    AlertDialog alertDialog;
    Button btnOk;
    TimePicker timePicker;
    public String time;
    String hour;
    String min;
    int hourNum;
    int minNum;

    public CustomeTimePicker(Context context, TextView timeTV , boolean today)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.time_picker_dialog,null);
        builder.setView(alertView);

        time = "";

        alertDialog = builder.create();
        alertDialog.show();

        timePicker = alertView.findViewById(R.id.timePicker);
        btnOk = alertView.findViewById(R.id.okBtn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourNum = timePicker.getCurrentHour();
                minNum = timePicker.getCurrentMinute();

                hour = Integer.toString(hourNum);
                min = Integer.toString(minNum);
                if(hour.length() == 1)
                {
                    hour = "0" + hour;
                }

                if(min.length()==1)
                {
                    min = "0" + min;
                }
                String time = hour+":"+min;


                if(today == true)
                {
                    if(checkTime(hourNum , minNum))
                    {
                        timeTV.setText(time);
                        alertDialog.dismiss();
                    }
                    else{
                        Toast.makeText(context, "you can not select a time in the past", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    timeTV.setText(time);
                    alertDialog.dismiss();
                }
            }
        });
    }

    public CustomeTimePicker(Context context, TextView timeTV , boolean today , int minLimitHour , int minLimitMinute)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.time_picker_dialog,null);
        builder.setView(alertView);

        time = "";

        alertDialog = builder.create();
        alertDialog.show();

        timePicker = alertView.findViewById(R.id.timePicker);
        btnOk = alertView.findViewById(R.id.okBtn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourNum = timePicker.getCurrentHour();
                minNum = timePicker.getCurrentMinute();

                hour = Integer.toString(hourNum);
                min = Integer.toString(minNum);
                if(hour.length() == 1)
                {
                    hour = "0" + hour;
                }

                if(min.length()==1)
                {
                    min = "0" + min;
                }
                String time = hour+":"+min;

                if(hourNum < minLimitHour || (hourNum == minLimitHour && minNum<minLimitMinute))
                {
                    Toast.makeText(context, "exam can not end before start time", Toast.LENGTH_LONG).show();
                }
                else{
                    if(today == true)
                    {
                        if(checkTime(hourNum , minNum))
                        {
                            timeTV.setText(time);
                            alertDialog.dismiss();
                        }
                        else{
                            Toast.makeText(context, "you can not select a time in the past", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        timeTV.setText(time);
                        alertDialog.dismiss();
                    }
                }
            }
        });
    }
    public boolean checkTime(int hour , int min)
    {
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        if(hour >= hour24hrs && min >= minutes)
        {
            return true;
        }
        else {
            return false;
        }
    }
}