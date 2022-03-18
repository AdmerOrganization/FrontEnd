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

public class EditProfileTest {
    @Rule
    @JvmField
    public ActivityScenarioRule<EditProfile> rule2 = new ActivityScenarioRule<EditProfile>(EditProfile.class);

    @Test
    public void EditProfile()
    {
        onView(withId(R.id.firstNameIde)).perform(typeText("abcdefgh"));
        onView(withId(R.id.lastNameIde)).perform(typeText("abcdefgh"));
        onView(withId(R.id.eemailid)).perform(typeText("abcdefgh@g"));
        onView(withId(R.id.phoneNumberIde)).perform(typeText("09102211824"));
        onView(withId(R.id.cardViewEditProfile)).perform(click());
        //email is not valid.
    }

    @Test
    public void EditProfile2()
    {
        onView(withId(R.id.firstNameIde)).perform(typeText("abcdefgh"));
        onView(withId(R.id.lastNameIde)).perform(typeText("abcdefgh"));
        onView(withId(R.id.eemailid)).perform(typeText("abcdefgh@gmail.com"));
        onView(withId(R.id.phoneNumberIde)).perform(typeText("0910221182a"));
        onView(withId(R.id.cardViewEditProfile)).perform(click());
        //phone number is not valid..
    }

    @Test
    public void EditProfile3()
    {
        onView(withId(R.id.firstNameIde)).perform(typeText(""));
        onView(withId(R.id.lastNameIde)).perform(typeText("abcdefgh"));
        onView(withId(R.id.eemailid)).perform(typeText("abcdefgh@gmail.com"));
        onView(withId(R.id.phoneNumberIde)).perform(typeText("09102211824"));
        onView(withId(R.id.cardViewEditProfile)).perform(click());
        //first name can not be empty
    }
}