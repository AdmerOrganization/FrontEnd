package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class examAdapterTest {
    @Mock
    Context context;
    @Mock
    List<JsonObject> list;
    @Mock
    List<JsonObject> temp;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        JsonArray ja = new JsonArray();
        examAdapter examAdapter = new examAdapter(context, ja, "type");
        int result = examAdapter.getCount();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetItem() throws Exception {
        JsonArray ja = new JsonArray();
        examAdapter examAdapter = new examAdapter(context, ja, "type");
        if(examAdapter.getCount()!= 0){
            Object result = examAdapter.getItem(0);
        }
        Assert.assertEquals(0, examAdapter.getCount());
    }

    @Test
    public void testGetItemId() throws Exception {
        JsonArray ja = new JsonArray();
        examAdapter examAdapter = new examAdapter(context, ja, "type");
        long result = examAdapter.getItemId(0);
        Assert.assertEquals(0, result);
    }
}