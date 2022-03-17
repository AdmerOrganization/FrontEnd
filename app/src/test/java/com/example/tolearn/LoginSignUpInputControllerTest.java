package com.example.tolearn;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginSignUpInputControllerTest {

    @Test
    public void isUsernameCorrectTest() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String username = "saman";
        boolean res = controller.IsUsernameCorrect(username);
        assertFalse(res);
    }

    @Test
    public void isUsernameCorrectTest2() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String username = "s";
        boolean res = controller.IsUsernameCorrect(username);
        assertFalse(res);
    }

    @Test
    public void isUsernameCorrectTest3() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String username = "";
        boolean res = controller.IsUsernameCorrect(username);
        assertFalse(res);
    }

    @Test
    public void isUsernameCorrectTest4() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String username = "saman123";
        boolean res = controller.IsUsernameCorrect(username);
        assertTrue(res);
    }

    @Test
    public void isUsernameCorrectTest5() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String username = "reza2000";
        boolean res = controller.IsUsernameCorrect(username);
        assertTrue(res);
    }

    @Test
    public void isUsernameCorrectTest6() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String username = "23123123m";
        boolean res = controller.IsUsernameCorrect(username);
        assertTrue(res);
    }

    @Test
    public void checkStringPasswordTest() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String password = "23123123123";
        boolean res = controller.checkStringPassword(password);
        assertFalse(res);
    }

    @Test
    public void checkStringPasswordTest2() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String password = "abcdefghij";
        boolean res = controller.checkStringPassword(password);
        assertFalse(res);
    }

    @Test
    public void checkStringPasswordTest3() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String password = "ABCDEFGHI";
        boolean res = controller.checkStringPassword(password);
        assertFalse(res);
    }

    @Test
    public void checkStringPasswordTest4() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String password = "ABCabcdef";
        boolean res = controller.checkStringPassword(password);
        assertFalse(res);
    }

    @Test
    public void checkStringPasswordTest5() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String password = "ABCDE1234";
        boolean res = controller.checkStringPassword(password);
        assertFalse(res);
    }

    @Test
    public void checkStringPasswordTest6() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String password = "abcdefgh1233";
        boolean res = controller.checkStringPassword(password);
        assertFalse(res);
    }

    @Test
    public void checkStringPasswordTest7() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String password = "ABCDabcd123";
        boolean res = controller.checkStringPassword(password);
        assertTrue(res);
    }

    @Test
    public void isEmailValidTest() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String email = "saman.raouf@gmail.com";
        boolean res = controller.isEmailValid(email);
        assertTrue(res);
    }

    @Test
    public void isEmailValidTest2() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String email = "samanraouf@gmail.com";
        boolean res = controller.isEmailValid(email);
        assertTrue(res);
    }


    @Test
    public void isEmailValidTest3() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String email = "saman.raoufgmail.com";
        boolean res = controller.isEmailValid(email);
        assertFalse(res);
    }


    @Test
    public void isEmailValidTest4() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String email = "saman.raouf@gmail";
        boolean res = controller.isEmailValid(email);
        assertFalse(res);
    }


    @Test
    public void isEmailValidTest5() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String email = "saman.raouf@yahoo.com";
        boolean res = controller.isEmailValid(email);
        assertTrue(res);
    }


    @Test
    public void isEmailValidTest6() {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String email = "abcdefghcom";
        boolean res = controller.isEmailValid(email);
        assertFalse(res);
    }


    @Test
    public void isPhoneNumberTest()
    {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String phone = "abcdefghcom";
        boolean res = controller.isPhoneNumber(phone);
        assertFalse(res);
    }

    @Test
    public void isPhoneNumberTest2()
    {
        LoginSignUpInputController controller = new LoginSignUpInputController();
        String phone = "09102211825";
        boolean res = controller.isPhoneNumber(phone);
        assertTrue(res);
    }




}