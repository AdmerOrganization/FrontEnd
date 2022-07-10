package com.example.tolearn.Entity;
import junit.framework.TestCase;
import static org.junit.Assert.*;

import org.junit.Test;
import com.example.tolearn.Entity.Homework_result;

import java.util.ArrayList;
import java.util.List;
public class Homework_result_test {
    @Test
    public void test1()
    {
        User user = new User("username", "password", "password2","email", "first_name" , "last_name");
        Homework_result hw_result = new Homework_result("id", "homework", "file", user,  "date;");

    }
}
