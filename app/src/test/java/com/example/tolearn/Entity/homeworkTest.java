package com.example.tolearn.Entity;
import junit.framework.TestCase;
import static org.junit.Assert.*;

import org.junit.Test;
import com.example.tolearn.Entity.Homework;

import java.util.ArrayList;
import java.util.List;
public class homeworkTest {

    @Test
    public void test1()
    {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        boolean result = false;
        if(h.getClassroom() == 1)
        {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void test2()
    {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        boolean result = false;
        if(h.getTitle() == "title")
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test3()
    {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        boolean result = false;
        if(h.getFile() == "file")
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test4()
    {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        boolean result = false;
        if(h.getDescription() == "description")
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test5()
    {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        boolean result = false;
        if(h.getDeadline() == "deadline")
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test6()
    {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        boolean result = false;
        if(h.getId() == 1)
        {
            result = true;
        }
        assertTrue(result);
    }

}
