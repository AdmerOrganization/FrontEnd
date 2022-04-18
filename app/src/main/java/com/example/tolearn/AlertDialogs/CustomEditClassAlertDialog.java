package com.example.tolearn.AlertDialogs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.Adapters.classAdapterManage;
import com.example.tolearn.Controllers.classCreationValidations;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.example.tolearn.manageClass;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.UserAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    EditText titleET,teacherET,descET,passwordET;
    ImageView classImage;
    Spinner limitSpinner;
    Spinner categorySpinner;
    public Button btnEdit;
    classCreationValidations Controller;

    public CustomEditClassAlertDialog(Context context , String title , String teacher , String desc , String password , String limit ,String category, String avatar)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.custome_alert_dialog_edit_class,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        categorySpinner = alertView.findViewById(R.id.category_spinner);
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
                else if(limitSpinner.getSelectedItem().toString().equals("limit"))
                {
                    Toast.makeText(context, "Limit can not be unselected", Toast.LENGTH_SHORT).show();
                }
                else {
                    Call<List<myClass>> callBack = classAPI.GetCreatedClasses("token "+userToken);
                    callBack.enqueue(new Callback<List<myClass>>() {
                        @Override
                        public void onResponse(Call<List<myClass>> call, Response<List<myClass>> response) {
                            if(!response.isSuccessful())
                            {
                                CustomeAlertDialog myEvents = new CustomeAlertDialog(manageClass.this,"Response Error","There is a problem with your internet connection");
                            }
                            else{
                                int responseCode = response.code();
                                myCreatedClasses = response.body();
                                // Toast.makeText(my_created_events.this, Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
                                myClassesAdap = new classAdapterManage(manageClass.this,myCreatedClasses,"Manage");

//                    myEventsAdap = new myEventsAdapter(my_created_events.this,myEvents);
                                myEventsList.setAdapter(myClassesAdap);
                                if(myClassesAdap.getCount() == 0)
                                {
                                    LinearLayout.LayoutParams noItemParams = (LinearLayout.LayoutParams) noItemFound.getLayoutParams();
                                    noItemParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                                    noItemFound.setVisibility(View.VISIBLE);
                                }
                                Log.i("oomad","To Else");
                                mFrameLayout.startShimmer();
                                mFrameLayout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<myClass>> call, Throwable t) {
                            CustomeAlertDialog myEvents = new CustomeAlertDialog(manageClass.this,"Error","There is a problem with your internet connection");
                        }
                    });
                }
            }
        });
    }

}
