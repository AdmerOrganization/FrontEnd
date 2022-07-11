package com.example.tolearn.Entity;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ExamHomeworkTest extends TestCase {

    public void testGetHomework() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        ExamNew exam = new ExamNew(questionList, startDate , endDate , 3 ,"exam_test_400",1);
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        ExamHomework examHomework = new ExamHomework(h,exam);
        boolean result = false;
        if(examHomework.getHomework() == h){
            result = true;
        }
        assertTrue(result);
    }

    public void testGetExam() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        ExamNew exam = new ExamNew(questionList, startDate , endDate , 3 ,"exam_test_400",1);
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        ExamHomework examHomework = new ExamHomework(h,exam);
        boolean result = false;
        if(examHomework.getExam() == exam){
            result = true;
        }
        assertTrue(result);
    }
}