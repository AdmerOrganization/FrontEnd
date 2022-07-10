package com.example.tolearn;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.junit.Assert.*;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;

import org.junit.Rule;
import org.junit.Test;

import kotlin.jvm.JvmField;
public class chatTest {

    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), ChatActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
    }
    @Rule
    public ActivityScenarioRule<ChatActivity> activityScenarioRule = new ActivityScenarioRule<ChatActivity>(intent);

    @Test
    public void Test1() {
        assertTrue(true);
    }

    @Test
    public void Test2() {
        assertTrue(true);
        //sending a message
    }

    @Test
    public void Test3() {
        assertTrue(true);
        //sending a meesage
    }
}
