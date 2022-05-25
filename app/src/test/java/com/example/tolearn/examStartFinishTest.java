package com.example.tolearn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import android.content.Context;
import android.widget.Toast;

import org.junit.Test;

import com.example.tolearn.AlertDialogs.CustomDatePicker;
import com.example.tolearn.Controllers.classCreationValidations;


public class examStartFinishTest {

    public boolean FinishTimeAfterStartTimeChecker(int hNum , int mNum  , int minLimitHour , int minLimitMinute)
    {
        int hourNum = hNum;
        int minNum = mNum;

        String hour = Integer.toString(hourNum);
        String min = Integer.toString(minNum);
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
            return false;
        }
        else{
            return true;
        }
    }
    public boolean checkTime(int hour , int min)
    {
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        if(hour > hour24hrs || (hour == hour24hrs && min >= minutes))
        {
            return true;
        }
        else {
            return false;
        }
    }

    @Test
    public void TimePicker_test1()
    {
        int hour = 23;
        int min = 59;
        boolean isTimeOK =  checkTime(hour , min);
        assertTrue(isTimeOK);
    }

    @Test
    public void TimePicker_test2()
    {
        int hour = 00;
        int min = 45;
        boolean isTimeOK =  checkTime(hour , min);
        assertFalse(isTimeOK);
    }

    @Test
    public void TimePicker_test3()
    {
        int hour = 00;
        int min = 45;
        int hour_min = 01;
        int min_min =01;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertFalse(isTimeOK);
    }

    @Test
    public void TimePicker_test4()
    {
        int hour = 1;
        int min = 00;
        int hour_min = 01;
        int min_min =01;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertFalse(isTimeOK);
    }

    @Test
    public void TimePicker_test5()
    {
        int hour = 12;
        int min = 00;
        int hour_min = 01;
        int min_min =01;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertTrue(isTimeOK);
    }

    @Test
    public void TimePicker_test6()
    {
        int hour = 12;
        int min = 02;
        int hour_min = 01;
        int min_min =01;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertTrue(isTimeOK);
    }

    @Test
    public void TimePicker_test7()
    {
        int hour = 00;
        int min = 02;
        int hour_min = 01;
        int min_min =01;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertFalse(isTimeOK);
    }

    @Test
    public void TimePicker_test8()
    {
        int hour = 12;
        int min = 00;
        int hour_min = 15;
        int min_min =30;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertFalse(isTimeOK);
    }

    @Test
    public void TimePicker_test9()
    {
        int hour = 15;
        int min = 40;
        int hour_min = 15;
        int min_min =30;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertTrue(isTimeOK);
    }

    @Test
    public void TimePicker_test10()
    {
        int hour = 15;
        int min = 40;
        int hour_min = 14;
        int min_min =50;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertTrue(isTimeOK);
    }

    @Test
    public void TimePicker_test11()
    {
        int hour = 15;
        int min = 20;
        int hour_min = 14;
        int min_min =50;
        boolean isTimeOK =  FinishTimeAfterStartTimeChecker(hour , min , hour_min , min_min);
        assertTrue(isTimeOK);
    }
}
