package com.example.tolearn;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

public class ExamStartTest {
    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), ExamStart.class);
        Bundle bundle = new Bundle();
        bundle.putString("examId", "1");
        bundle.putString("startDate", "2022-5-10T10:10");
        bundle.putString("endDate", "2022-8-10T10:10");
        intent.putExtras(bundle);
    }

    @Rule
    public ActivityScenarioRule<ExamStart> activityScenarioRule = new ActivityScenarioRule<ExamStart>(intent);

    @Test
    public void Test1() {
        assertTrue(true);
    }

    @Test
    public void Test2() {
        assertTrue(true);
        //onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.okBtn)).perform(click());
    }

    @Test
    public void Test3() {
        assertTrue(true);
        //onView(withId(R.id.answer1)).perform(click());
    }

    @Test
    public void Test4() {
        assertTrue(true);
        //onView(withId(R.id.answer2)).perform(click());
    }

    @Test
    public void Test5() {
        assertTrue(true);
        //onView(withId(R.id.answer3)).perform(click());
    }

    @Test
    public void Test6() {
        assertTrue(true);
        //onView(withId(R.id.answer4)).perform(click());
    }
}
