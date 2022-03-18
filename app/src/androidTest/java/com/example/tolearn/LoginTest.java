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

public class LoginTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<Login> rule = new ActivityScenarioRule<Login>(Login.class);

    @Test
    public void LoginInputsForThe_FirstTime_()
    {
        //this user has not registered yet.... so we check just for the inputs...
        onView(withId(R.id.usernameInput)).perform(typeText("samanmraouf"));
        onView(withId(R.id.passwordInput)).perform(typeText("Saman"));
    }

    @Test
    public void LoginInputsForThe_FirstTime_2()
    {
        //this user has not registered yet ... so we check just for the inputs and click on login btn ....
        onView(withId(R.id.usernameInput)).perform(typeText("samanmraf"));
        onView(withId(R.id.passwordInput)).perform(typeText("Saman"));
        onView(withId(R.id.LoginBtn)).perform(click());
    }


}