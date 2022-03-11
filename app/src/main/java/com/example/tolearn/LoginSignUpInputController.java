package com.example.tolearn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSignUpInputController {
    public boolean IsUsernameCorrect(String username)
    {
        if(username.length()<8)
        {
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkStringPassword(String str) {
        if(str.length()<8)
        {
            return false;
        }
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    public boolean isEmailValid(String email)
    {
        String regEX="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{3}+";
        CharSequence input=email;
        Pattern pattern=Pattern.compile(regEX,Pattern.UNICODE_CASE);
        Matcher matcher=pattern.matcher(input);
        if(matcher.matches())
        {
            return  true;
        }
        else
            return false;
    }
}

