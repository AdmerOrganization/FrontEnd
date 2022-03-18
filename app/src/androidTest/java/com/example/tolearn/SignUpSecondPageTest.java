package com.example.tolearn;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import kotlin.jvm.JvmField;

public class SignUpSecondPageTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<SignUpSecondPage> rule2 = new ActivityScenarioRule<SignUpSecondPage>(SignUpSecondPage.class);

    @Test
    public void first_name_last_name ()
    {
        onView(withId(R.id.firstnameInput)).perform(typeText("abcdef"));
        onView(withId(R.id.lastnameInput)).perform(typeText("abcdefghijklmn"));
        onView(withId(R.id.SignUpBtn)).perform(click());
    }
}