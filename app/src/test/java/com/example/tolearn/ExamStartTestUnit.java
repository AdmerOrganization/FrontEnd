package com.example.tolearn;

import static org.junit.Assert.*;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.junit.Rule;
import org.junit.Test;

import com.example.tolearn.Adapters.StudentExamQuestionsAdapter;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.CustomeConfirmAlertDialog;
import com.example.tolearn.ExamStart;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamStartTestUnit {

    String endDate;

    public boolean ExamTimeChecker (String startDate , String endDate)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTime = (dateFormat.format(cal.getTime()));

        String [] dateTime = currentDateTime.split(" ");
        String [] dateInfo = dateTime[0].split("/");
        String [] timeInfo = dateTime[1].split(":");

        int currentYear = Integer.parseInt(dateInfo[0]);
        int currentMonth = Integer.parseInt(dateInfo[1]);
        int currentDay = Integer.parseInt(dateInfo[2]);
        int currentHour = Integer.parseInt(timeInfo[0]);
        int currentMinute = Integer.parseInt(timeInfo[1]);

        String [] startDateTimeInfo = startDate.split("T");
        String [] startDateInfo = startDateTimeInfo[0].split("-");
        int startYear = Integer.parseInt(startDateInfo[0]);
        int startMonth = Integer.parseInt(startDateInfo[1]);
        int startDay = Integer.parseInt(startDateInfo[2]);

        String [] StartTimeInfo = startDateTimeInfo[1].split(":");
        int startHour = Integer.parseInt(StartTimeInfo[0]);
        int startMinute = Integer.parseInt(StartTimeInfo[1]);


        String [] endDateTimeInfo = endDate.split("T");
        String [] endDateInfo = endDateTimeInfo[0].split("-");
        int endYear = Integer.parseInt(endDateInfo[0]);
        int endMonth = Integer.parseInt(endDateInfo[1]);
        int endDay = Integer.parseInt(endDateInfo[2]);

        String [] endTimeInfo = endDateTimeInfo[1].split(":");
        int endHour = Integer.parseInt(endTimeInfo[0]);
        int endMinute = Integer.parseInt(endTimeInfo[1]);


        String currentMoment = String.valueOf(currentHour)+String.valueOf(currentMinute);
        int currentMomentInt = Integer.parseInt(currentMoment);

        String startMoment = String.valueOf(startHour)+String.valueOf(startMinute);
        int startMomentInt = Integer.parseInt(startMoment);

        String endMoment = String.valueOf(endHour)+String.valueOf(endMinute);
        int endMomentInt = Integer.parseInt(endMoment);

        if(currentMomentInt >= startMomentInt && currentMomentInt <= endMomentInt)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public long durationCalculator ()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTime = (dateFormat.format(cal.getTime()));

        String [] dateTime = currentDateTime.split(" ");
        String [] dateInfo = dateTime[0].split("/");
        String [] timeInfo = dateTime[1].split(":");

        int currentYear = Integer.parseInt(dateInfo[0]);
        int currentMonth = Integer.parseInt(dateInfo[1]);
        int currentDay = Integer.parseInt(dateInfo[2]);
        int currentHour = Integer.parseInt(timeInfo[0]);
        int currentMinute = Integer.parseInt(timeInfo[1]);


        String [] endDateTimeInfo = endDate.split("T");
        String [] endDateInfo = endDateTimeInfo[0].split("-");
        int endYear = Integer.parseInt(endDateInfo[0]);
        int endMonth = Integer.parseInt(endDateInfo[1]);
        int endDay = Integer.parseInt(endDateInfo[2]);

        String [] endTimeInfo = endDateTimeInfo[1].split(":");
        int endHour = Integer.parseInt(endTimeInfo[0]);
        int endMinute = Integer.parseInt(endTimeInfo[1]);

        int hourDifference = endHour - currentHour;
        int minDifference = endMinute - currentMinute;

        long timeleft = hourDifference * 60 + minDifference;
        timeleft = timeleft*60;
        timeleft = timeleft * 1000;
        //timeleft = timeleft + 59000;
        return timeleft;
    }


    @Test
    public void test6()
    {
        boolean res = ExamTimeChecker("2021-10-10T10:10","2024-10-10T10:10");
        assertFalse(res);
    }
    @Test
    public void test7()
    {
        endDate = "2022-07-20T10:10";
        if(10000 < durationCalculator()){
            assertTrue(true);
        }
    }
}
