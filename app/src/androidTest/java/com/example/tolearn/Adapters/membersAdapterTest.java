package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.View;

import com.example.tolearn.Entity.member;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class membersAdapterTest {
    @Mock
    Context context;
    @Mock
    List<member> members;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        member m = new member("name",  "lastname");
        List<member> members = new ArrayList<>();
        members.add(m);
        membersAdapter ma = new membersAdapter(context,members);
        int result = ma.getCount();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetItem() throws Exception {
        member m = new member("name",  "lastname");
        List<member> members = new ArrayList<>();
        members.add(m);
        membersAdapter ma = new membersAdapter(context,members);
        Object result = ma.getItem(0);
        Assert.assertEquals(m, result);
    }

    @Test
    public void testGetItemId() throws Exception {
        member m = new member("name",  "lastname");
        List<member> members = new ArrayList<>();
        members.add(m);
        membersAdapter ma = new membersAdapter(context,members);
        long result = ma.getItemId(0);
        Assert.assertEquals(0, result);
    }

}