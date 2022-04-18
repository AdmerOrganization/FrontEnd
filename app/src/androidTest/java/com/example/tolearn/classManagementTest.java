package com.example.tolearn;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import static java.util.EnumSet.allOf;

import android.os.SystemClock;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import kotlin.jvm.JvmField;
public class classManagementTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<manageClass> rule2 = new ActivityScenarioRule<manageClass>(manageClass.class);
    @Test
    public void classLoad()
    {
        SystemClock.sleep(10000);
        //onView(withId(R.id.searchEventEditText)).perform(typeText("riazi3"));
    }

    @Test
    public void classLoad2()
    {
        SystemClock.sleep(10000);
        onView(withId(R.id.searchEventEditText)).perform(typeText("riazi"));
        SystemClock.sleep(4000);
    }

    @Test
    public void classLoad3()
    {
        SystemClock.sleep(10000);
        onView(withId(R.id.searchEventEditText)).perform(typeText("tla"));
        SystemClock.sleep(4000);
    }


}
