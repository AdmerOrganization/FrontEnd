package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import com.example.tolearn.Controllers.classCreationValidations;
import com.example.tolearn.R;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.UserAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomEditClassAlertDialog {

    ClassAPI classAPI;
    public AlertDialog alertDialog;
    EditText titleET,teacherET,descET,passwordET;
    ImageView classImage;
    Spinner limitSpinner;
    public Button btnEdit;
    classCreationValidations Controller;

    public CustomEditClassAlertDialog(Context context , String title , String teacher , String desc , String password , String limit , String avatar)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.custome_alert_dialog_edit_class,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        titleET = alertView.findViewById(R.id.titleET);
        teacherET = alertView.findViewById(R.id.teacherET);
        descET = alertView.findViewById(R.id.descET);
        passwordET = alertView.findViewById(R.id.passwordET);
        limitSpinner = alertView.findViewById(R.id.limitSpinner);
        classImage = alertView.findViewById(R.id.classImage);
        btnEdit = alertView.findViewById(R.id.editBtn);
        Controller = new classCreationValidations();

        //load form the data.....
        titleET.setText(title);
        teacherET.setText(teacher);
        descET.setText(desc);
        passwordET.setText(password);

        switch (limit){
            case "limit":
                limitSpinner.setSelection(0);
            case "5":
                limitSpinner.setSelection(1);
            case "10":
                limitSpinner.setSelection(2);
            case "20":
                limitSpinner.setSelection(3);
            case "30":
                limitSpinner.setSelection(4);
            case "40":
                limitSpinner.setSelection(5);
            case "50":
                limitSpinner.setSelection(6);
            case "60":
                limitSpinner.setSelection(7);
            case "80":
                limitSpinner.setSelection(8);
            case "100":
                limitSpinner.setSelection(9);
            case "150":
                limitSpinner.setSelection(10);
            case "200":
                limitSpinner.setSelection(11);
        }
        //load image in classImage ImageView ....



        //edit clicked ...
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();


                Retrofit SignUpRefrofit = new Retrofit.Builder()
                        .baseUrl(UserAPI.BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                classAPI =SignUpRefrofit.create(ClassAPI.class);

                if(!Controller.ClassPassword(passwordET.getText().toString()))
                {
                    Toast.makeText(context, "Password has to be at least 8 characters with at least one uppercase and one number", Toast.LENGTH_SHORT).show();
                }
                else if(!Controller.classTeacher(teacherET.getText().toString()))
                {
                    Toast.makeText(context, "Teacher can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!Controller.classTitle(titleET.getText().toString()))
                {
                    Toast.makeText(context, "Title can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!Controller.ClassDescriotion(descET.getText().toString()))
                {
                    Toast.makeText(context, "Description can not be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    //retrofit connection to backend ....
                }
            }
        });
    }

}
