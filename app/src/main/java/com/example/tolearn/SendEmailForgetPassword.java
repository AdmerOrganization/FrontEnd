package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.webService.UserAPI;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendEmailForgetPassword extends AppCompatActivity {

    EditText emailET;
    UserAPI userAPI;
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
        //retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();

        Retrofit SignUpRefrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI =SignUpRefrofit.create(UserAPI.class);

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
            view.setClickable(false);
            Animation animation = AnimationUtils.loadAnimation(SendEmailForgetPassword.this,R.anim.blink_anim);
            view.startAnimation(animation);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email",emailET.getText().toString());
            Call<JsonObject> sendEmail = userAPI.Password_reset("application/json",jsonObject);
            sendEmail.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(SendEmailForgetPassword.this, "Please try again", Toast.LENGTH_SHORT).show();
                        view.setClickable(true);
                        view.clearAnimation();
                    }
                    else{
                        CustomeAlertDialog checkEmail = new CustomeAlertDialog(SendEmailForgetPassword.this,"Successful","we have send an email . Please check your email");
                        checkEmail.btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent goToForgetPass = new Intent(SendEmailForgetPassword.this,ForgetPassword.class);
                                checkEmail.alertDialog.dismiss();
                                startActivity(goToForgetPass);
                                //finish();
                            }
                        });
                    }
                    view.setClickable(true);
                    view.clearAnimation();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(SendEmailForgetPassword.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
            });
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