package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

public class class_creation_page_2 extends AppCompatActivity {

    Spinner limitET;
    EditText passwordET;
    String title;
    String teacher;
    String desc;
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

    }
}