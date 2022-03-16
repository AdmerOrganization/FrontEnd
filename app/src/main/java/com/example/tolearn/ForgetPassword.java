package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tolearn.webService.UserAPI;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPassword extends AppCompatActivity {

    EditText passwordET;
    EditText confirmPasswordET;
    EditText verificationCode;
    LoginSignUpInputController inputController;
    UserAPI userAPI;
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

        inputController = new LoginSignUpInputController();
        passwordET = findViewById(R.id.passwordInput);
        confirmPasswordET = findViewById(R.id.confirmPasswordInput);
        verificationCode = findViewById(R.id.codeInput);
        validators();
    }


    public void validators()
    {
        passwordValidationChecker();
        ConfirmPasswordValidationChecker();
    }

    private void passwordValidationChecker() {
        passwordET.addTextChangedListener(new TextWatcher() {

            String password = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = passwordET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean passwordValid = inputController.checkStringPassword(password);
                if(passwordValid == false)
                {
                    passwordET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    passwordET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }

    private void ConfirmPasswordValidationChecker() {
        confirmPasswordET.addTextChangedListener(new TextWatcher() {

            String confirmPassword = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmPassword = confirmPasswordET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!confirmPassword.equals(passwordET.getText().toString()))
                {
                    confirmPasswordET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    if(inputController.checkStringPassword(passwordET.getText().toString()))
                    {
                        confirmPasswordET.setBackgroundResource(R.drawable.border_shadow_white_background);
                    }
                }
            }
        });
    }



    public void verify(View view) {
        if(verificationCode.getText().toString().equals(""))
        {
            Toast.makeText(this, "verification code can not be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!inputController.checkStringPassword(passwordET.getText().toString()))
        {
            Toast.makeText(this, "your password is not valid", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Password must be at least 8 characters containing at least one capital letter and one numbers ", Toast.LENGTH_LONG).show();
        }
        else if(!passwordET.getText().toString().equals(confirmPasswordET.getText().toString()))
        {
            Toast.makeText(this, "password and confirm password must be equal", Toast.LENGTH_SHORT).show();
        }
        else{
            view.setClickable(false);
            Animation animation = AnimationUtils.loadAnimation(ForgetPassword.this,R.anim.blink_anim);
            view.startAnimation(animation);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("password",passwordET.getText().toString());
            jsonObject.addProperty("token",verificationCode.getText().toString());
            Call<JsonObject> verify = userAPI.ConfirmationPassword_reset("application/json",jsonObject);
            verify.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(ForgetPassword.this, "Please try again", Toast.LENGTH_SHORT).show();
                        view.setClickable(true);
                        view.clearAnimation();
                    }
                    else{
                        Intent goToLogin = new Intent(ForgetPassword.this,Login.class);
                        startActivity(goToLogin);
                        finish();
                    }
                    view.setClickable(true);
                    view.clearAnimation();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(ForgetPassword.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
            });
        }
    }
}