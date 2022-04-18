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
public class ClassCreationTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<Class_creation> rule2 = new ActivityScenarioRule<Class_creation>(Class_creation.class);

    @Test
    public void ClassCreation()
    {
        onView(withId(R.id.classTitleET)).perform(typeText(""));
        onView(withId(R.id.classTeacherET)).perform(typeText("abcdefgh"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

}
