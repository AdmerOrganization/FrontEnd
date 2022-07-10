package com.example.tolearn.Adapters;

import static org.junit.Assert.*;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.widget.TextView;

import org.junit.Test;
import com.example.tolearn.AlertDialogs.CustomDatePicker;

public class CustomeDatePickerTest {

    private static Context context;
    TextView TV;
    private static Activity sample;
    public String date;
    String YearS;
    String MonthS;
    String DayS;
    int yearNum;
    int monthNum;
    int dayNum;

    public String getDate()
    {
        return date;
    }

    public String getYearS() {
        return YearS;
    }

    public String getMonthS() {
        return MonthS;
    }

    public String getDayS() {
        return DayS;
    }

    public int getYearNum() {
        return yearNum;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public int getDayNum() {
        return dayNum;
    }
    @Test
    public void getDateTest() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDate();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getDateTest2() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDate();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }


    @Test
    public void getDateTest3() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDate();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getDateTest4() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDate();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getYearSTest() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getYearS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getYearSTest2() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getYearS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }


    @Test
    public void getYearSTest3() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getYearS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getYearSTest4() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getYearS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getMonthSTest() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getMonthS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getMonthSTest2() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getMonthS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }


    @Test
    public void getMonthSTest3() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getMonthS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getMonthSTest4() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getMonthS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }


    @Test
    public void getDaySTest() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDayS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getDaySTest2() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDayS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }


    @Test
    public void getDaySTest3() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDayS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }

    @Test
    public void getDaySTest4() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        String res = getDayS();
        boolean result;
        if(res == null)
        {
            result = false;
            assertFalse(result);
        }
        else if (!res.equals(""))
        {
            result = true;
            assertTrue(result);
        }
        else{
            result = false;
            assertFalse(result);
        }
    }


    @Test
    public void getYearNumTest() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        int res = getYearNum();
        boolean result;
        if(res<2000)
        {
            result = false;
            assertFalse(result);
        }
        else{
            result = true;
            assertTrue(result);
        }
    }

    @Test
    public void getDayNumTest() {
        //context  = sample;
        //CustomDatePicker controller = new CustomDatePicker(context,TV);
        int res = getDayNum();
        boolean result;
        if(res>31)
        {
            result = false;
            assertFalse(result);
        }
        else{
            result = true;
            assertTrue(result);
        }
    }
}
