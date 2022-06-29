package com.example.tolearn.AlertDialogs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomEditClassAlertDialog extends Activity {

    ClassAPI classAPI;
    public AlertDialog alertDialog;
    JsonObject EditedClass;
    EditText titleET,teacherET,descET,passwordET;
    ImageView classImage;
    Spinner limitSpinner;
    Spinner categorySpinner;
    public Button btnEdit;
    classCreationValidations Controller;

    public CustomEditClassAlertDialog(Context context ,String userToken,String class_token, String title , String teacher , String desc , String password , String limit ,String category, String avatar)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.custome_alert_dialog_edit_class,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        categorySpinner = alertView.findViewById(R.id.categorySpinner);
        titleET = alertView.findViewById(R.id.detailTitleET);
        teacherET = alertView.findViewById(R.id.teacherET);
        descET = alertView.findViewById(R.id.detailDescET);
        limitSpinner = alertView.findViewById(R.id.limitSpinner);
        classImage = alertView.findViewById(R.id.classImage);
        btnEdit = alertView.findViewById(R.id.editBtn);
        btnEdit.setClickable(true);
        Controller = new classCreationValidations();

        //load form the data.....
        titleET.setText(title);
        teacherET.setText(teacher);
        descET.setText(desc);


        switch (limit){
            case "limit":
                limitSpinner.setSelection(0);
                break;
            case "5":
                limitSpinner.setSelection(1);
                break;
            case "10":
                limitSpinner.setSelection(2);
                break;
            case "20":
                limitSpinner.setSelection(3);
                break;
            case "30":
                limitSpinner.setSelection(4);
                break;
            case "40":
                limitSpinner.setSelection(5);
                break;
            case "50":
                limitSpinner.setSelection(6);
                break;
            case "60":
                limitSpinner.setSelection(7);
                break;
            case "80":
                limitSpinner.setSelection(8);
                break;
            case "100":
                limitSpinner.setSelection(9);
                break;
            case "150":
                limitSpinner.setSelection(10);
                break;
            case "200":
                limitSpinner.setSelection(11);
                break;
        }

        if(avatar.equals(""))
        {
            switch (category)
            {
                case "Math":
                    categorySpinner.setSelection(1);
                    classImage.setImageResource(R.drawable.math);
                    break;
                case "Chemistry":
                    categorySpinner.setSelection(2);
                    classImage.setImageResource(R.drawable.chemistry);
                    break;
                case "Physics":
                    categorySpinner.setSelection(3);
                    classImage.setImageResource(R.drawable.atom);
                    break;
                case "Engineering":
                    categorySpinner.setSelection(4);
                    classImage.setImageResource(R.drawable.engineering);
                    break;
                case "Paint":
                    categorySpinner.setSelection(5);
                    classImage.setImageResource(R.drawable.paint);
                    break;
                case "Music":
                    categorySpinner.setSelection(6);
                    classImage.setImageResource(R.drawable.musical);
                    break;
                case "Cinema":
                    categorySpinner.setSelection(7);
                    classImage.setImageResource(R.drawable.clapperboard);
                    break;
                case "athletic":
                    categorySpinner.setSelection(8);
                    classImage.setImageResource(R.drawable.athletics);
                    break;
                case "computer science":
                    categorySpinner.setSelection(9);
                    classImage.setImageResource(R.drawable.responsive);
                    break;
                case "language":
                    categorySpinner.setSelection(10);
                    classImage.setImageResource(R.drawable.languages);
                    break;
            }
        }
        else
        {
            Picasso.get().load(avatar).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(classImage);
        }

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(categorySpinner.getSelectedItem().toString().equals("Math"))
                {
                    classImage.setImageResource(R.drawable.math);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("Chemistry"))
                {
                    classImage.setImageResource(R.drawable.chemistry);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("Physics"))
                {
                    classImage.setImageResource(R.drawable.atom);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("Engineering"))
                {
                    classImage.setImageResource(R.drawable.engineering);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("Paint"))
                {
                    classImage.setImageResource(R.drawable.paint);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("Music"))
                {
                    classImage.setImageResource(R.drawable.musical);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("Cinema"))
                {
                    classImage.setImageResource(R.drawable.clapperboard);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("computer science"))
                {
                    classImage.setImageResource(R.drawable.responsive);
                }
                else if(categorySpinner.getSelectedItem().toString().equals("language"))
                {
                    classImage.setImageResource(R.drawable.languages);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //edit clicked ...
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setClickable(false);

                Animation animation = AnimationUtils.loadAnimation(context,R.anim.blink_anim);
                view.startAnimation(animation);

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();


                Retrofit SignUpRefrofit = new Retrofit.Builder()
                        .baseUrl(UserAPI.BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                classAPI =SignUpRefrofit.create(ClassAPI.class);

                if(!Controller.classTeacher(teacherET.getText().toString()))
                {
                    Toast.makeText(context, "Teacher can not be empty", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
                else if(!Controller.classTitle(titleET.getText().toString()))
                {
                    Toast.makeText(context, "Title can not be empty", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
                else if(!Controller.ClassDescriotion(descET.getText().toString()))
                {
                    Toast.makeText(context, "Description can not be empty", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
                else if(limitSpinner.getSelectedItem().toString().equals("limit"))
                {
                    Toast.makeText(context, "Limit can not be unselected", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
                else if(categorySpinner.getSelectedItem().toString().equals("category"))
                {
                    Toast.makeText(context, "Category can not be unselected", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
                else {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("classroom_token",class_token);
                    jsonObject.addProperty("title",titleET.getText().toString());
                    jsonObject.addProperty("teacher_name",teacherET.getText().toString());
                    jsonObject.addProperty("description",descET.getText().toString());
                    jsonObject.addProperty("limit",limitSpinner.getSelectedItem().toString());
                    jsonObject.addProperty("category",categorySpinner.getSelectedItem().toString());
                    Call<JsonObject> callBack = classAPI.EditClass("token "+ userToken,jsonObject);
                    callBack.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(!response.isSuccessful())
                            {
                                view.setClickable(true);
                                view.clearAnimation();
                               // CustomeAlertDialog myEvents = new CustomeAlertDialog(CustomEditClassAlertDialog.this,"Response Error","There is a problem with your internet connection");
                            }
                            else{
                                int responseCode = response.code();
                                JsonObject myCreatedClasses = response.body();
                                view.clearAnimation();
                                Toast.makeText(context, "Class edited successfully.", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            view.setClickable(true);
                            view.clearAnimation();
                          //  CustomeAlertDialog myEvents = new CustomeAlertDialog(CustomEditClassAlertDialog.this,"Error","There is a problem with your internet connection");
                        }
                    });
                }
            }
        });
    }

}
