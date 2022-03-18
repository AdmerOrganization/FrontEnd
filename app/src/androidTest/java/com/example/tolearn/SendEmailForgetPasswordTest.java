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

public class SendEmailForgetPasswordTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<SendEmailForgetPassword> rule2 = new ActivityScenarioRule<SendEmailForgetPassword>(SendEmailForgetPassword.class);


    @Test
    public void sendWrongEmail ()
    {
        onView(withId(R.id.emailInput)).perform(typeText("saman.raf.com"));
        onView(withId(R.id.SendEmailBtn)).perform(click());
        //expect to see a message , that this email is not valid...
    }


    @Test
    public void sendRealEmail ()
    {
        onView(withId(R.id.emailInput)).perform(typeText("saman.raouf@gmail.com"));
        onView(withId(R.id.SendEmailBtn)).perform(click());
    }
}