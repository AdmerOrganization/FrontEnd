package com.example.tolearn.Controllers;

import android.content.Context;

import java.util.Calendar;

public class homework_creation_validations {
    public boolean IsTitleCorrect(String title)
    {
        if(!title.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean IsDescCorrect(String desc)
    {
        if(!desc.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean IsDateValid(int year, int month , int day)
    {
        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        currentMonth = currentMonth + 1;
        int currentYear = c.get(Calendar.YEAR);


        String currentDateTime = Integer.toString(currentYear)+Integer.toString(currentMonth)+Integer.toString(currentDay);
        // Toast.makeText(this, currentDateTime, Toast.LENGTH_SHORT).show();
        if(currentYear<year)
        {
            return true;
        }
        else if(currentYear==year && currentMonth<month)
        {
            return true;
        }
        else if(currentYear == year && currentMonth == month && currentDay <= day)
        {
            return true;
        }

        //Toast.makeText(this, "you can set a task for past", Toast.LENGTH_SHORT).show();
        return false;
    }
}
