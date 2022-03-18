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

public class SignUpTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<SignUp> rule2 = new ActivityScenarioRule<SignUp>(SignUp.class);


    @Test
    public void register ()
    {
        onView(withId(R.id.usernameInput)).perform(typeText("SamanMRaouf2000"));
        onView(withId(R.id.passwordInput)).perform(typeText("Saman12345"));
        onView(withId(R.id.repeatPasswordInput)).perform(typeText("Saman12345"));
        onView(withId(R.id.emailInput)).perform(typeText("saman.raouf@gmail.com"));
    }
}