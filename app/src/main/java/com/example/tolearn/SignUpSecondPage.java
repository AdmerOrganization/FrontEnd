package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tolearn.Entity.User;
import com.example.tolearn.webService.UserAPI;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpSecondPage extends AppCompatActivity {

    EditText firstNameET;
    EditText lastNameET;
    UserAPI userAPI;
    String username;
    String password;
    String confirmPassword;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_second_page);
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

        firstNameET = findViewById(R.id.firstnameInput);
        lastNameET = findViewById(R.id.lastnameInput);


        Intent signUp = getIntent();
        username = signUp.getStringExtra("username");
        password= signUp.getStringExtra("password");
        confirmPassword = signUp.getStringExtra("confirmPassword");
        email= signUp.getStringExtra("email");
    }

    public void SignUp(View view) {
        if(InputValidation())
        {
            User user = new  User (username,password,confirmPassword,email,firstNameET.getText().toString(),lastNameET.getText().toString());
            Call<JsonObject> userInfo = userAPI.CreateUser("application/json",user);
            userInfo.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(SignUpSecondPage.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SignUpSecondPage.this, "please check your email", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(SignUpSecondPage.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean InputValidation()
    {
        String firstname = firstNameET.getText().toString();
        String lastname = lastNameET.getText().toString();
        if(firstname.equals(""))
        {
            Toast.makeText(this, "first name can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(lastname.equals(""))
        {
            Toast.makeText(this, "last name can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}