package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {

    EditText verificationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().hide();
        init();
    }

    public void init()
    {
        verificationCode = findViewById(R.id.codeInput);
    }

    public void verify(View view) {
        if(verificationCode.getText().toString().equals(""))
        {
            Toast.makeText(this, "verification code can not be empty", Toast.LENGTH_SHORT).show();
        }
        else{
            //connection to back end
        }
    }
}