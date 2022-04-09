package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tolearn.Controllers.classCreationValidations;

public class class_creation_page_2 extends AppCompatActivity {

    Spinner limitET;
    EditText passwordET;
    String title;
    String teacher;
    String desc;
    classCreationValidations Controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_class_creation_page2);
        getSupportActionBar().hide();
        init();
    }

    public void init()
    {
        limitET = findViewById(R.id.limit_spinner);
        passwordET = findViewById(R.id.classPasswordET);

        Intent pre_page = getIntent();
        title = pre_page.getStringExtra("title");
        teacher = pre_page.getStringExtra("teacher");
        desc = pre_page.getStringExtra("desc");
        Controller = new classCreationValidations();

        fieldValidations();
    }

    public void fieldValidations()
    {
        passwordET.addTextChangedListener(new TextWatcher() {
            String classPassword = "";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                classPassword = passwordET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Controller.ClassPassword(classPassword))
                {
                    passwordET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    passwordET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }

    public boolean registerVliadation(String password)
    {
        if(!Controller.ClassPassword(password))
        {
            Toast.makeText(this, "password is not valid . Password have to be at least 8 characters with at least one capital letter and one number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void ClassRegister(View view) {
        if(registerVliadation(passwordET.getText().toString()))
        {
            //todo
        }
    }
}