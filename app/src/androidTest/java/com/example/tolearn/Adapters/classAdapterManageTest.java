package com.example.tolearn.Adapters;

import android.content.Context;

import com.example.tolearn.Entity.myClass;
import com.example.tolearn.webService.ClassAPI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class classAdapterManageTest {
    @Mock
    Context context;
    @Mock
    List<myClass> list;
    @Mock
    List<myClass> temp;
    @Mock
    ClassAPI classAPI;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        myClass c = new myClass(1,"category","classroom","avatar","class_title","teacher_name","desc",10,"2022-10-10T10:10");
        List<myClass> classes = new ArrayList<>();
        classes.add(c);
        classAdapterManage classAdapterManage = new classAdapterManage(context,classes,"type","token");
        int result = classAdapterManage.getCount();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetItem() throws Exception {
        myClass c = new myClass(1,"category","classroom","avatar","class_title","teacher_name","desc",10,"2022-10-10T10:10");
        List<myClass> classes = new ArrayList<>();
        classes.add(c);
        classAdapterManage classAdapterManage = new classAdapterManage(context,classes,"type","token");
        Object result = classAdapterManage.getItem(0);
        Assert.assertEquals(c, result);
    }

    @Test
    public void testGetItemId() throws Exception {
        myClass c = new myClass(1,"category","classroom","avatar","class_title","teacher_name","desc",10,"2022-10-10T10:10");
        List<myClass> classes = new ArrayList<>();
        classes.add(c);
        classAdapterManage classAdapterManage = new classAdapterManage(context,classes,"type","token");
        long result = classAdapterManage.getItemId(0);
        Assert.assertEquals(0, result);
    }
}