package com.example.tolearn.Entity;

import junit.framework.TestCase;
import static org.junit.Assert.*;

import org.junit.Test;
import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.question;

import java.util.ArrayList;
import java.util.List;

public class ExamTest extends TestCase {

    public void testGetQuestions_count() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if(exam.getQuestions_count() == 3)
        {
            result = true;
        }
        assertTrue(result);
    }

    public void testGetQuestions_count1() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if(exam.questions_count == 2)
        {
            result = true;
        }
        assertFalse(result);
    }

    public void testTestGetName() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if(exam.getName().equals("exam_test_400"))
        {
            result = true;
        }
        assertTrue(result);
    }

    public void testTestGetName2() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if(!exam.getName().equals("exam_test_400"))
        {
            result = true;
        }
        assertFalse(result);
    }

    public void testGetQuestionList() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if (exam.getQuestionList() == questionList)
        {
            result = true;
        }
        assertTrue(result);
    }

    public void testGetQuestionList2() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if (exam.getQuestionList() != questionList)
        {
            result = true;
        }
        assertFalse(result);
    }


    public void testGetStartDate() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if (exam.getStartDate() == startDate)
        {
            result = true;
        }
        assertTrue(result);
    }

    public void testGetStartDate2() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if (exam.getStartDate() != startDate)
        {
            result = true;
        }
        assertFalse(result);
    }

    public void testGetEndDate() {
        String startDate = "2022-05-13T10:10";
        String endDate = "2022-05-13T10:10";

        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        question q2 = new question( "2", "question2", "answer1", "answer2", "answer3", "answer4", "1");
        question q3 = new question( "3", "question3", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);
        Exam exam = new Exam(questionList, startDate , endDate , 3 ,"exam_test_400",1);

        boolean result = false;
        if (exam.getStartDate() != endDate)
        {
            result = true;
        }
        assertFalse(result);
    }
}