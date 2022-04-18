package com.example.tolearn;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import static java.util.EnumSet.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import kotlin.jvm.JvmField;
public class classCreationTest2 {
    @Rule
    @JvmField
    public ActivityScenarioRule<class_creation_page_2> rule2 = new ActivityScenarioRule<class_creation_page_2>(class_creation_page_2.class);

    @Test
    public void ClassCreation()
    {
        onView(withId(R.id.classPasswordET)).perform(typeText(""));
        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation2()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssss"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation3()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssSss12334567"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation4()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssSss12334567"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation5()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssSss12334567"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation6()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(7).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssSss12334567"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation7()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(9).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssSss12334567"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation8()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(9).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssSadfass14567"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation9()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("sssSss12334567"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation10()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(7).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("asddasda1234567890"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }

    @Test
    public void ClassCreation11()
    {

        onView(withId(R.id.limit_spinner)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.category_spinner)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.classPasswordET)).perform(typeText("hhaHADF143"));
        //onView(withId(R.id.NextPageBtn)).perform(click());
    }
}
