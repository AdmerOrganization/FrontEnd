package com.example.tolearn;
import static org.junit.Assert.*;

import android.content.Context;
import android.widget.Toast;

import org.junit.Test;

import com.example.tolearn.AlertDialogs.CustomDatePicker;
import com.example.tolearn.Controllers.classCreationValidations;

public class classRegisterTest {
    public boolean registerValidations(String title , String teacher , String desc )
    {
        classCreationValidations Controller;
        Controller = new classCreationValidations();
        if(!Controller.classTitle(title))
        {
            return false;
        }
        else if (! Controller.classTeacher(teacher))
        {
            return false;
        }
        else if( ! Controller.ClassDescriotion(desc))
        {
            return false;
        }
        else {
            return true;
        }
    }

    @Test
    public void classRegister() {
        classCreationValidations controller = new classCreationValidations();
        String title = "";
        String teacher ="";
        String desc = "";
        boolean res = registerValidations(title,teacher,desc);
        assertFalse(res);
    }

    @Test
    public void classRegister1() {
        classCreationValidations controller = new classCreationValidations();
        String title = "dfdfdf";
        String teacher ="";
        String desc = "";
        boolean res = registerValidations(title,teacher,desc);
        assertFalse(res);
    }

    @Test
    public void classRegister2() {
        classCreationValidations controller = new classCreationValidations();
        String title = "";
        String teacher ="dfdfd";
        String desc = "";
        boolean res = registerValidations(title,teacher,desc);
        assertFalse(res);
    }

    @Test
    public void classRegister3() {
        classCreationValidations controller = new classCreationValidations();
        String title = "";
        String teacher ="";
        String desc = "dfdfd";
        boolean res = registerValidations(title,teacher,desc);
        assertFalse(res);
    }

    @Test
    public void classRegister4() {
        classCreationValidations controller = new classCreationValidations();
        String title = "dssdf";
        String teacher ="sdfsdf";
        String desc = "sdfsdf";
        boolean res = registerValidations(title,teacher,desc);
        assertTrue(res);
    }

    @Test
    public void classRegister5() {
        classCreationValidations controller = new classCreationValidations();
        String title = "dssdf";
        String teacher ="";
        String desc = "sdfsdf";
        boolean res = registerValidations(title,teacher,desc);
        assertFalse(res);
    }

    @Test
    public void classRegister6() {
        classCreationValidations controller = new classCreationValidations();
        String title = "dssdf";
        String teacher ="sdfsdf";
        String desc = "";
        boolean res = registerValidations(title,teacher,desc);
        assertFalse(res);
    }
}
