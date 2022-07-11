package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.View;

import com.example.tolearn.Entity.Homework;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class homeworkAdapterTest {
    @Mock
    Context context;
    @Mock
    List<Homework> list;
    @Mock
    List<Homework> temp;
    @InjectMocks
    com.example.tolearn.Adapters.homeworkAdapter homeworkAdapter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        List<Homework> hws = new ArrayList<>();
        hws.add(h);
        homeworkAdapter ha = new homeworkAdapter(context , hws , "token ");
        int result = ha.getCount();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetItem() throws Exception {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        List<Homework> hws = new ArrayList<>();
        hws.add(h);
        homeworkAdapter ha = new homeworkAdapter(context , hws , "token ");
        Object result = ha.getItem(0);
        Assert.assertEquals(h, result);
    }

    @Test
    public void testGetItemId() throws Exception {
        Homework h = new Homework(1,"token", "title", "file", "description", "deadline", 1);
        List<Homework> hws = new ArrayList<>();
        hws.add(h);
        homeworkAdapter ha = new homeworkAdapter(context , hws , "token ");
        long result = ha.getItemId(0);
        Assert.assertEquals(0, result);
    }
}