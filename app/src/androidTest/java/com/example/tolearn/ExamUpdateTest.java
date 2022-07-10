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

public class ExamUpdateTest {

    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), ExamUpdate.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", "1");
        bundle.putString("question_count", "2");
        bundle.putString("name", "test");
        bundle.putString("start_time", "2022-5-10T10:10");
        bundle.putString("end_time", "2022-8-10T10:10");
        intent.putExtras(bundle);
    }

    @Rule
    public ActivityScenarioRule<ExamUpdate> activityScenarioRule = new ActivityScenarioRule<>(intent);

    @Test
    public void Test1() {
        assertTrue(true);
    }

    @Test
    public void Test2() {
        assertTrue(true);
        onView(withId(R.id.addQuestionBtn)).perform(click());
    }

    @Test
    public void Test3() {
        assertTrue(true);
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
    }

    @Test
    public void Test4() {
        assertTrue(true);
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question "));
        onView(withId(R.id.answer1)).perform(typeText("ans1"));
        onView(withId(R.id.answer2)).perform(typeText("ans2"));
        onView(withId(R.id.answer3)).perform(typeText("ans3"));
        onView(withId(R.id.answer4)).perform(typeText("ans4"));

    }

    @Test
    public void Test5() {
        assertTrue(true);
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question "));
        onView(withId(R.id.answer1)).perform(typeText("ans1"));
        onView(withId(R.id.answer2)).perform(typeText("ans2"));
        onView(withId(R.id.answer3)).perform(typeText("ans3"));
        onView(withId(R.id.answer4)).perform(typeText("ans4"));
        onView(withId(R.id.answer1)).perform(click());
    }
}
