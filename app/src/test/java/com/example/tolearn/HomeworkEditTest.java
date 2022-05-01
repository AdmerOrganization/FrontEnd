package com.example.tolearn;

import static org.junit.Assert.*;

import com.example.tolearn.Controllers.homework_creation_validations;

import org.junit.Test;
public class HomeworkEditTest {
    @Test
    public void IsTitleCorrectTest()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsTitleCorrect("");
        assertFalse(titleCorrect);
    }

    @Test
    public void IsTitleCorrectTest1()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsTitleCorrect("ABCD");
        assertTrue(titleCorrect);
    }

    @Test
    public void IsTitleCorrectTest2()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsTitleCorrect("abcd");
        assertTrue(titleCorrect);
    }

    @Test
    public void IsTitleCorrectTest3()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsTitleCorrect("1234avx");
        assertTrue(titleCorrect);
    }


    @Test
    public void IsDescCorrect1()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDescCorrect("");
        assertFalse(titleCorrect);
    }
    @Test
    public void IsDescCorrect3()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDescCorrect("this is a test");
        assertTrue(titleCorrect);
    }


    @Test
    public void IsDescCorrect4()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDescCorrect("test 2");
        assertTrue(titleCorrect);
    }


    @Test
    public void IsDateValid()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDateValid(1390 , 10 , 31);
        assertFalse(titleCorrect);
    }
    @Test
    public void IsDateValid2()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDateValid(2000 , 10 , 31);
        assertFalse(titleCorrect);
    }
    @Test
    public void IsDateValid3()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDateValid(2020 , 10 , 31);
        assertFalse(titleCorrect);
    }
    @Test
    public void IsDateValid4()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDateValid(2022 , 10 , 31);
        assertTrue(titleCorrect);
    }
    @Test
    public void IsDateValid6()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDateValid(2024 , 11 , 11);
        assertTrue(titleCorrect);
    }
    @Test
    public void IsDateValid7()
    {
        homework_creation_validations controller = new homework_creation_validations();
        boolean titleCorrect = controller.IsDateValid(2021 , 10 , 31);
        assertFalse(titleCorrect);
    }
}
