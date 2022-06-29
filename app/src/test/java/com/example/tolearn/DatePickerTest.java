package com.example.tolearn;
import static org.junit.Assert.*;

import android.content.Context;

import org.junit.Test;

import com.example.tolearn.AlertDialogs.CustomDatePicker;
import com.example.tolearn.Controllers.classCreationValidations;

import java.util.Calendar;


public class DatePickerTest {

    public boolean checkDate(int year, int month , int day)
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
    public void isDateCorrect() {
        int year = 1995;
        int month = 11;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertFalse(res);
    }

    @Test
    public void isDateCorrect2() {
        int year = 2195;
        int month = 11;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertTrue(res);
    }

    @Test
    public void isDateCorrect3() {
        int year = 2000;
        int month = 11;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertFalse(res);
    }

    @Test
    public void isDateCorrect4() {
        int year = 2019;
        int month = 11;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertFalse(res);
    }

    @Test
    public void isDateCorrect5() {
        int year = 2022;
        int month = 11;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertTrue(res);
    }

    @Test
    public void isDateCorrect6() {
        int year = 2022;
        int month = 4;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertFalse(res);
    }

    @Test
    public void isDateCorrect7() {
        int year = 2022;
        int month = 4;
        int day = 19;
        boolean res = checkDate(year,month,day);
        assertTrue(res);
    }

    @Test
    public void isDateCorrect8() {
        int year = 2022;
        int month = 11;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertTrue(res);
    }

    @Test
    public void isDateCorrect9() {
        int year = 2022;
        int month = 4;
        int day = 14;
        boolean res = checkDate(year,month,day);
        assertFalse(res);
    }

    @Test
    public void isDateCorrect10() {
        int year = 2022;
        int month = 4;
        int day = 19;
        boolean res = checkDate(year,month,day);
        assertTrue(res);
    }
}
