package com.example.tolearn.Entity;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ExamNewTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

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
        ExamNew exam = new ExamNew(questionList, startDate , endDate , 3 ,"exam_test_400",1);
        boolean result = false;
        if(exam.getQuestions_count() == 3){
            result = true;
        }
        assertTrue(result);
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
        ExamNew exam = new ExamNew(questionList, startDate , endDate , 3 ,"exam_test_400",1);
        boolean result = false;
        if(exam.getName() == "exam_test_400"){
            result = true;
        }
        assertTrue(result);
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
        ExamNew exam = new ExamNew(questionList, startDate , endDate , 3 ,"exam_test_400",1);
        boolean result = false;
        if(exam.getStartDate() == startDate){
            result = true;
        }
        assertTrue(result);
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
        ExamNew exam = new ExamNew(questionList, startDate , endDate , 3 ,"exam_test_400",1);
        boolean result = false;
        if(exam.getEndDate() == endDate){
            result = true;
        }
        assertTrue(result);
    }

    public void testGetId() {
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
        boolean result = false;
        if(exam.getId() == 1){
            result = true;
        }
        assertTrue(result);
    }

    public void testSetId() {
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
        exam.setId(1);
        boolean result = false;
        if(exam.getId() == 1){
            result = true;
        }
        assertTrue(result);
    }

    public void testGetScore() {
    }

    public void testSetScore() {
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
        exam.setScore("50");
        boolean result = false;
        if(exam.getScore() == "50"){
            result = true;
        }
        assertTrue(result);
    }
}