package com.example.tolearn.Controllers;

public class classCreationValidations {

    public boolean classTitle(String classTitle)
    {
        if(!classTitle.equals(""))
        {
            return true;
        }
        return false;
    }

    public boolean classTeacher ( String classTeacher)
    {
        if(!classTeacher.equals(""))
        {
            return true;
        }
        return false;
    }

    public boolean ClassDescriotion(String classDesc)
    {
        if(!classDesc.equals(""))
        {
            return true;
        }
        return false;
    }

    public boolean ClassPassword(String classPassword)
    {
        if(classPassword.length()<8)
        {
            return false;
        }
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < classPassword.length();i++) {
            ch = classPassword.charAt(i);
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
}
