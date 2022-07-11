package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.View;

import com.example.tolearn.Entity.Homework_result;
import com.example.tolearn.Entity.User;
import com.example.tolearn.webService.HomeworkAPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class homework_result_item_adapterTest {
    @Mock
    Context context;
    @Mock
    List<Homework_result> results;
    @Mock
    HomeworkAPI homeworkAPI;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        User user = new User( "username",  "password",  "password2",  "email",  "first_name" ,  "last_name");
        Homework_result hr = new Homework_result("1","hw","",user,"");
        List<Homework_result> hw_results = new ArrayList<>();
        hw_results.add(hr);
        homework_result_item_adapter hw = new homework_result_item_adapter(context,  hw_results,"token");
        int result = hw.getCount();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetItem() throws Exception {
        User user = new User( "username",  "password",  "password2",  "email",  "first_name" ,  "last_name");
        Homework_result hr = new Homework_result("1","hw","",user,"");
        List<Homework_result> hw_results = new ArrayList<>();
        hw_results.add(hr);
        homework_result_item_adapter hw = new homework_result_item_adapter(context,  hw_results,"token");
        Object result = hw.getItem(0);
        Assert.assertEquals(hr, result);
    }

    @Test
    public void testGetItemId() throws Exception {
        User user = new User( "username",  "password",  "password2",  "email",  "first_name" ,  "last_name");
        Homework_result hr = new Homework_result("1","hw","",user,"");
        List<Homework_result> hw_results = new ArrayList<>();
        hw_results.add(hr);
        homework_result_item_adapter hw = new homework_result_item_adapter(context,  hw_results,"token");
        long result = hw.getItemId(0);
        Assert.assertEquals(0, result);
    }
}