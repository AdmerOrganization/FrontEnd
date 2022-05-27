package com.example.tolearn;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.junit.Assert.*;

import android.widget.DatePicker;
import android.widget.Toast;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;

import org.junit.Rule;
import org.junit.Test;

import kotlin.jvm.JvmField;

public class examTest {

    @Rule
    @JvmField
    public ActivityScenarioRule<ExamProfile> rule2 = new ActivityScenarioRule<ExamProfile>(ExamProfile.class);

    @Test
    public void EditProfile1()
    {
        onView(withId(R.id.titleEt)).perform(typeText("abcdefgh"));
        onView(withId(R.id.examDate)).perform(click());
    }

    @Test
    public void EditProfile2()
    {
        onView(withId(R.id.titleEt)).perform(typeText("exam1"));
        onView(withId(R.id.examDate)).perform(click());
    }


    @Test
    public void EditProfile3()
    {
        onView(withId(R.id.titleEt)).perform(typeText("abcdefgh"));
        onView(withId(R.id.startTimeET)).perform(click());
    }


    @Test
    public void EditProfile4()
    {
        onView(withId(R.id.titleEt)).perform(typeText("df"));
        onView(withId(R.id.startTimeET)).perform(click());
    }

    @Test
    public void EditProfile5()
    {
        onView(withId(R.id.titleEt)).perform(typeText("abcdefgh"));
        onView(withId(R.id.startTimeET)).perform(click());
        onView(withId(R.id.examDate)).perform(click());
    }

    @Test
    public void EditProfile6()
    {
        onView(withId(R.id.titleEt)).perform(typeText("abcdefgh"));
        onView(withId(R.id.EndTimeEt)).perform(click());
        onView(withId(R.id.startTimeET)).perform(click());
        onView(withId(R.id.examDate)).perform(click());
    }

    @Test
    public void EditProfile7()
    {
        onView(withId(R.id.titleEt)).perform(typeText("test"));
        onView(withId(R.id.EndTimeEt)).perform(click());
        onView(withId(R.id.startTimeET)).perform(click());
        onView(withId(R.id.examDate)).perform(click());
    }


    @Test
    public void EditProfile8()
    {
        onView(withId(R.id.titleEt)).perform(typeText("abcdefgh"));
        onView(withId(R.id.examDate)).perform(click());
    }

    @Test
    public void EditProfile9()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
    }

    @Test
    public void EditProfile10()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("exam"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
    }

    @Test
    public void EditProfile11()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("exam"));
        onView(withId(R.id.answer1)).perform(typeText(""));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }


    @Test
    public void EditProfile12()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("exam"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }


    @Test
    public void EditProfile13()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("exam"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText(""));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }


    @Test
    public void EditProfile14()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("exam"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }

    @Test
    public void EditProfile15()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText(""));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }

    @Test
    public void EditProfile16()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("exam11"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }

    @Test
    public void EditProfile17()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("exam11"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }

    @Test
    public void EditProfile18()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question1"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());

        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question2"));
        onView(withId(R.id.answer1)).perform(typeText("a"));
        onView(withId(R.id.answer2)).perform(typeText("b"));
        onView(withId(R.id.answer3)).perform(typeText("c"));
        onView(withId(R.id.answer4)).perform(typeText("d"));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }

    @Test
    public void EditProfile19()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question1"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());

        onView(withId(R.id.editQuestion)).perform(click());
    }

    @Test
    public void EditProfile20()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question1"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());

        onView(withId(R.id.editQuestion)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question1_edited_Version"));
        onView(withId(R.id.answer1)).perform(typeText("1a"));
        onView(withId(R.id.answer2)).perform(typeText("2b"));
        onView(withId(R.id.answer3)).perform(typeText("3c"));
        onView(withId(R.id.answer4)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }

    @Test
    public void EditProfile21()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question1"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());

        onView(withId(R.id.editQuestion)).perform(click());
        onView(withId(R.id.question)).perform(typeText("_edited_Version"));
        onView(withId(R.id.answer1)).perform(typeText("a"));
        onView(withId(R.id.answer2)).perform(typeText("b"));
        onView(withId(R.id.answer3)).perform(typeText("c"));
        onView(withId(R.id.answer4)).perform(typeText(""));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }

    @Test
    public void EditProfile22()
    {
        onView(withId(R.id.addQuestionBtn)).perform(click());
        onView(withId(R.id.question)).perform(typeText("question1"));
        onView(withId(R.id.answer1)).perform(typeText("1"));
        onView(withId(R.id.answer2)).perform(typeText("2"));
        onView(withId(R.id.answer3)).perform(typeText("3"));
        onView(withId(R.id.answer4)).perform(typeText("4"));
        onView(withId(R.id.answer1)).perform(click());
        onView(withId(R.id.answer1)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());

        onView(withId(R.id.editQuestion)).perform(click());
        onView(withId(R.id.question)).perform(typeText("_edited_Version"));
        onView(withId(R.id.answer1)).perform(typeText("a"));
        onView(withId(R.id.answer2)).perform(typeText("b"));
        onView(withId(R.id.answer3)).perform(typeText("c"));
        onView(withId(R.id.answer4)).perform(typeText(""));
        onView(withId(R.id.answer2)).perform(click());
        onView(withId(R.id.answer2)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.doneBtn)).perform(click());
    }


}