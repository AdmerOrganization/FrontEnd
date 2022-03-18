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

public class ForgetPasswordTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<ForgetPassword> rule2 = new ActivityScenarioRule<ForgetPassword>(ForgetPassword.class);

    @Test
    public void forget_pass_test_with_random_code_expected_messageWithErrorFromTheApplication()
    {
        onView(withId(R.id.codeInput)).perform(typeText("aaaaaaaaaaa"));
        onView(withId(R.id.passwordInput)).perform(typeText("Abcdef12345"));
        onView(withId(R.id.confirmPasswordInput)).perform(typeText("Abcdefg12345"));
        onView(withId(R.id.verifyCode)).perform(click());
        //code is not valid
    }

    @Test
    public void forget_pass_test_with_random_code_expected_messageWithErrorFromTheApplication2()
    {
        onView(withId(R.id.codeInput)).perform(typeText("aaaaaaaaaaa"));
        onView(withId(R.id.passwordInput)).perform(typeText("Abcdef12345"));
        onView(withId(R.id.confirmPasswordInput)).perform(typeText("Abcdefg1245"));
        onView(withId(R.id.verifyCode)).perform(click());
        //confirm password is not equal to password
    }

    @Test
    public void forget_pass_test_with_random_code_expected_messageWithErrorFromTheApplication3()
    {
        onView(withId(R.id.codeInput)).perform(typeText("aaaaaaaaaaa"));
        onView(withId(R.id.passwordInput)).perform(typeText("abcdef12345"));
        onView(withId(R.id.confirmPasswordInput)).perform(typeText("abcdefg12345"));
        onView(withId(R.id.verifyCode)).perform(click());
        //password is not ok
    }
}