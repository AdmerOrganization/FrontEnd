package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class StudentsExamResults extends AppCompatActivity {

    com.google.android.material.floatingactionbutton.FloatingActionButton backBtn;
    ListView resultsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_exam_results);
        getSupportActionBar().hide();
        init();

        Intent getInfo = getIntent();
        String examID = getInfo.getStringExtra("exam_id");

        //todo : api call and get the results ....

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void init()
    {
        resultsView = findViewById(R.id.results);
        backBtn = findViewById(R.id.BackBtn);
    }
}