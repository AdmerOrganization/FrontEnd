package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.View;

import com.example.tolearn.Entity.question;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class StudentExamQuestionsAdapterTest {
    @Mock
    Context context;
    @Mock
    List<JsonObject> questionList;
    //Field answers of type int[] - was not mocked since Mockito doesn't mock arrays
    @InjectMocks
    StudentExamQuestionsAdapter studentExamQuestionsAdapter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questions = new ArrayList<>();
        questions.add(q1);
        JsonObject js = new JsonObject();
        js.addProperty("id","1");
        js.addProperty("question1","1");
        js.addProperty("answer1","1");
        js.addProperty("answer2","1");
        js.addProperty("answer3","1");
        js.addProperty("answer4","1");
        js.addProperty("righ_ans","1");
        List<JsonObject> objects = new ArrayList<>();
        objects.add(js);
        int [] ans = new int[1];
        ans[0] =1;
        studentExamQuestionsAdapter = new StudentExamQuestionsAdapter(context,objects,ans);
        int result = studentExamQuestionsAdapter.getCount();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetItem() throws Exception {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questions = new ArrayList<>();
        questions.add(q1);
        JsonObject js = new JsonObject();
        js.addProperty("id","1");
        js.addProperty("question1","1");
        js.addProperty("answer1","1");
        js.addProperty("answer2","1");
        js.addProperty("answer3","1");
        js.addProperty("answer4","1");
        js.addProperty("righ_ans","1");
        List<JsonObject> objects = new ArrayList<>();
        objects.add(js);
        int [] ans = new int[1];
        ans[0] =1;
        studentExamQuestionsAdapter = new StudentExamQuestionsAdapter(context,objects,ans);
        Object result = studentExamQuestionsAdapter.getItem(0);
        Assert.assertEquals(js, result);
    }

    @Test
    public void testGetItemId() throws Exception {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questions = new ArrayList<>();
        questions.add(q1);
        JsonObject js = new JsonObject();
        js.addProperty("id","1");
        js.addProperty("question1","1");
        js.addProperty("answer1","1");
        js.addProperty("answer2","1");
        js.addProperty("answer3","1");
        js.addProperty("answer4","1");
        js.addProperty("righ_ans","1");
        List<JsonObject> objects = new ArrayList<>();
        objects.add(js);
        int [] ans = new int[1];
        ans[0] =1;
        studentExamQuestionsAdapter = new StudentExamQuestionsAdapter(context,objects,ans);
        long result = studentExamQuestionsAdapter.getItemId(0);
        Assert.assertEquals(0, result);
    }
}