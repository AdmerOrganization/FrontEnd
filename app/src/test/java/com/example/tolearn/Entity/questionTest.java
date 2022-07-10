package com.example.tolearn.Entity;
import static org.junit.Assert.*;

import org.junit.Test;
import com.example.tolearn.Entity.question;
public class questionTest {
    @Test
    public void test1()
    {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        boolean result = false;
        if(q1.getAnswer1().equals("answer1"))
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test2()
    {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        boolean result = false;
        if(q1.getAnswer2().equals("answer2"))
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test3()
    {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        boolean result = false;
        if(q1.getAnswer3().equals("answer3"))
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test4()
    {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        boolean result = false;
        if(q1.getAnswer4().equals("answer4"))
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test5()
    {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        boolean result = false;
        if(q1.getRigh_ans().equals("1"))
        {
            result = true;
        }
        assertTrue(result);
    }


    @Test
    public void test6()
    {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        boolean result = false;
        if(q1.getQuestion().equals("question1"))
        {
            result = true;
        }
        assertTrue(result);
    }


}
