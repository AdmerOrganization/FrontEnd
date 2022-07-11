package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.tolearn.Entity.question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class questionsAdapterTest {
    @Mock
    Context context;
    @Mock
    List<question> questionList;
    @Mock
    ListView questionListView;
    @Mock
    com.example.tolearn.Adapters.questionsAdapter adapter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questions = new ArrayList<>();
        questions.add(q1);
        questionsAdapter qa = new questionsAdapter(context,questions , questionListView);
        int result = qa.getCount();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetItem() throws Exception {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questions = new ArrayList<>();
        questions.add(q1);
        questionsAdapter qa = new questionsAdapter(context,questions , questionListView);
        Object result = qa.getItem(0);
        Assert.assertEquals(q1, result);
    }

    @Test
    public void testGetItemId() throws Exception {
        question q1 = new question( "1", "question1", "answer1", "answer2", "answer3", "answer4", "1");
        List<question> questions = new ArrayList<>();
        questions.add(q1);
        questionsAdapter qa = new questionsAdapter(context,questions , questionListView);
        long result = qa.getItemId(0);
        Assert.assertEquals(0, result);
    }

}