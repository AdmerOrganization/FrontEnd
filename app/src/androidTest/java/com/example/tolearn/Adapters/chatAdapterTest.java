package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.View;

import com.example.tolearn.Entity.message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class chatAdapterTest {
    @Mock
    List<message> messageList;
    @Mock
    Context context;
//    @InjectMocks
//    com.example.tolearn.Adapters.chatAdapter chatAdapter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCount() throws Exception {
        message m = new message("id",  "fname",  "lname", "message", "timestamp");
        List<message> messageList = new ArrayList<>();
        messageList.add(m);
        chatAdapter chatAdapter = new chatAdapter(messageList,context,false);
        int result = chatAdapter.getCount();
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetItem() throws Exception {
        message m = new message("id",  "fname",  "lname", "message", "timestamp");
        List<message> messageList = new ArrayList<>();
        messageList.add(m);
        chatAdapter chatAdapter = new chatAdapter(messageList,context,false);
        Object result = chatAdapter.getItem(0);
        Assert.assertEquals(m, result);
    }

    @Test
    public void testGetItemId() throws Exception {
        message m = new message("id",  "fname",  "lname", "message", "timestamp");
        List<message> messageList = new ArrayList<>();
        messageList.add(m);
        chatAdapter chatAdapter = new chatAdapter(messageList,context,false);
        long result = chatAdapter.getItemId(0);
        Assert.assertEquals(0, result);
    }

}