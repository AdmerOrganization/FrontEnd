package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmailForgetPassword extends AppCompatActivity {

    EditText emailET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_send_email_forget_password);
        getSupportActionBar().hide();
        init();

    }
    public void init()
    {
        emailET = findViewById(R.id.emailInput);
    }

    public void goBackToLogin(View view) {
        Intent goBackToLogin = new Intent(this,Login.class);
        startActivity(goBackToLogin);
        finish();
    }

    public void SendEmail(View view) {
        if(InputChecker())
        {
            //send email (connection to back end)
        }
        else{
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean InputChecker()
    {
        String email = emailET.getText().toString();
        boolean InputCorrect = true;
        LoginSignUpInputController inputController = new LoginSignUpInputController();
        if(email.equals(""))
        {
            Toast.makeText(this, "Email can not be empty", Toast.LENGTH_SHORT).show();
            InputCorrect = false;
        }
        if(!inputController.isEmailValid(email))
        {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            InputCorrect = false;
        }
        return InputCorrect;
    }
}