package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tolearn.AlertDialogs.CustomDatePicker;
import com.example.tolearn.AlertDialogs.CustomeAlertDialogDescription;

import java.util.Date;

public class Class_creation extends AppCompatActivity {

    EditText classSubjectET;
    EditText classTitleET;
    TextView classDescET;
    TextView dateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_creation);
        getSupportActionBar().hide();
        init();
    }

    public void init()
    {
        classTitleET = findViewById(R.id.classTitleET);
        classSubjectET = findViewById(R.id.classSubjectET);
        classDescET = findViewById(R.id.classDescription);
        dateTV = findViewById(R.id.classStartDate);
    }

    public void ShowDescDialog(View view) {
        CustomeAlertDialogDescription descDialog = new CustomeAlertDialogDescription(this,classDescET);
        descDialog.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = descDialog.desc.getText().toString();
                classDescET.setText(desc);
                descDialog.alertDialog.dismiss();
            }
        });
    }

    public void ShowDateDialog(View view) {
        CustomDatePicker datePicker = new CustomDatePicker(this,dateTV);
    }
}