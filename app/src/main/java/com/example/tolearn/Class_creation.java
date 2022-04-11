package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.AlertDialogs.CustomDatePicker;
import com.example.tolearn.AlertDialogs.CustomeAlertDialogDescription;
import java.util.Date;

import com.example.tolearn.Controllers.classCreationValidations;

public class Class_creation extends AppCompatActivity {

    EditText classTeachertET;
    EditText classTitleET;
    TextView classDescET;
    TextView dateTV;
    classCreationValidations controller;

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
        classTeachertET = findViewById(R.id.classTeacherET);
        classDescET = findViewById(R.id.classDescription);
        dateTV = findViewById(R.id.classStartDate);

        controller = new classCreationValidations();
        fieldsValidations();
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

    public void goNextPage(View view) {
        String classTitle = classTitleET.getText().toString();
        String classTeacher = classTeachertET.getText().toString();
        String classDesc = classDescET.getText().toString();
        String classDate = dateTV.getText().toString();

        classDesc = classDesc + "    date : "+ classDate;

        if(registerValidations(classTitle,classTeacher,classDesc))
        {
            Intent goNextPage = new Intent(this, class_creation_page_2.class);
            goNextPage.putExtra("title",classTitle);
            goNextPage.putExtra("teacher",classTeacher);
            goNextPage.putExtra("desc",classDesc);
            goNextPage.putExtra("finisher", new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    Class_creation.this.finish();
                }
            });
            startActivityForResult(goNextPage,1);
        }
    }

    public boolean registerValidations(String title , String teacher , String desc )
    {
        if(!controller.classTitle(title))
        {
            Toast.makeText(this, "title is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (! controller.classTeacher(teacher))
        {
            Toast.makeText(this, "teacher is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if( ! controller.ClassDescriotion(desc))
        {
            Toast.makeText(this, "description is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public void fieldsValidations()
    {

        // class teacher validation ....
        classTeachertET.addTextChangedListener(new TextWatcher() {
            String classTeacher = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                classTeacher = classTeachertET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!controller.classTeacher(classTeacher))
                {
                    classTeachertET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    classTeachertET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });


        // class title validation ....
        classTitleET.addTextChangedListener(new TextWatcher() {
            String classTitle = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                classTitle = classTitleET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!controller.classTeacher(classTitle))
                {
                    classTitleET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    classTitleET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });

        // class desc validation ....
        classDescET.addTextChangedListener(new TextWatcher() {
            String classDesc = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                classDesc = classTitleET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!controller.classTeacher(classDesc))
                {
                    classDescET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    classDescET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }
}