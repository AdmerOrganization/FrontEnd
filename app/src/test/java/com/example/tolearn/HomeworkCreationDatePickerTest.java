package com.example.tolearn;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

import com.example.tolearn.Controllers.homework_creation_validations;


public class HomeworkCreationDatePickerTest {
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


    @Test
    public void DateValidation1()
    {
        boolean dateValiation = IsDateValid(2000,11,1);
        assertFalse(dateValiation);
    }

    @Test
    public void DateValidation2()
    {
        boolean dateValidation = IsDateValid(2021, 5, 29);
        assertFalse(dateValidation);
    }


    @Test
    public void DateValidation3()
    {
        boolean dateValidation = IsDateValid(2021, 4, 20);
        assertFalse(dateValidation);
    }


    @Test
    public void DateValidation4()
    {
        boolean dateValidation = IsDateValid(2011, 5, 20);
        assertFalse(dateValidation);
    }


    @Test
    public void DateValidation5()
    {
        boolean dateValidation = IsDateValid(2022, 5, 20);
        assertFalse(dateValidation);
    }
}
