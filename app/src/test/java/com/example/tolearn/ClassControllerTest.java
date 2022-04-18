package com.example.tolearn;

import static org.junit.Assert.*;

import org.junit.Test;
import com.example.tolearn.Controllers.classCreationValidations;


public class ClassControllerTest {
    @Test
    public void isTitleCorrect() {
        classCreationValidations controller = new classCreationValidations();
        String title = "";
        boolean res = controller.classTitle(title);
        assertFalse(res);
    }

    @Test
    public void isTitleCorrect2() {
        classCreationValidations controller = new classCreationValidations();
        String title = "Tla";
        boolean res = controller.classTitle(title);
        assertTrue(res);
    }

    @Test
    public void isTeacherNameCorrect() {
        classCreationValidations controller = new classCreationValidations();
        String teacher = "";
        boolean res = controller.classTeacher(teacher);
        assertFalse(res);
    }

    @Test
    public void isTeacherNameCorrect2() {
        classCreationValidations controller = new classCreationValidations();
        String teacher = "name";
        boolean res = controller.classTeacher(teacher);
        assertTrue(res);
    }

    @Test
    public void isPasswordCorrect() {
        classCreationValidations controller = new classCreationValidations();
        String password = "";
        boolean res = controller.ClassPassword(password);
        assertFalse(res);
    }

    @Test
    public void isPasswordCorrect2() {
        classCreationValidations controller = new classCreationValidations();
        String teacher = "Tasdaf";
        boolean res = controller.ClassPassword(teacher);
        assertFalse(res);
    }

    @Test
    public void isPasswordCorrect3() {
        classCreationValidations controller = new classCreationValidations();
        String password = "TTTDFDADF";
        boolean res = controller.ClassPassword(password);
        assertFalse(res);
    }

    @Test
    public void isPasswordCorrect4() {
        classCreationValidations controller = new classCreationValidations();
        String teacher = "13425453";
        boolean res = controller.ClassPassword(teacher);
        assertFalse(res);
    }

    @Test
    public void isPasswordCorrect5() {
        classCreationValidations controller = new classCreationValidations();
        String password = "trsfv245534";
        boolean res = controller.ClassPassword(password);
        assertFalse(res);
    }

    @Test
    public void isPasswordCorrect6() {
        classCreationValidations controller = new classCreationValidations();
        String teacher = "TddFDer1345";
        boolean res = controller.ClassPassword(teacher);
        assertTrue(res);
    }
}
