package com.example.tolearn.Adapters;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import android.content.Context;
import android.os.Handler;
import android.test.mock.MockContext;

import androidx.lifecycle.ViewModel;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.example.tolearn.Adapters.chatAdapter;
import com.example.tolearn.ChatActivity;
import com.example.tolearn.Entity.message;

import java.util.ArrayList;
import java.util.List;

public class chatAdapterTest {
    private Context mockApplicationContext;
    @Test
    public void test1()
    {
        mockApplicationContext = ApplicationProvider.getApplicationContext();
        message m = new message("id",  "fname",  "lname", "message", "timestamp");
        List<message> messageList = new ArrayList<>();
        messageList.add(m);
        boolean f = false;
        chatAdapter ca = new chatAdapter(messageList,mockApplicationContext,f);

        boolean result = false;
        if(1 == ca.getCount())
        {
            result = true;
        }
        assertTrue(result);
    }
}
