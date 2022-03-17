package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {

    LoginSignUpInputController inputController;
    EditText usernameET;
    EditText passwordET;
    UserAPI userAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
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


        usernameET = findViewById(R.id.usernameInput);
        passwordET = findViewById(R.id.passwordInput);
        inputController = new LoginSignUpInputController();
        validationsChecker();
    }

    public void validationsChecker()
    {
        usernameValidationChecker();
        passwordValidationChecker();
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

    public void usernameValidationChecker()
    {
        usernameET.addTextChangedListener(new TextWatcher() {

            String username = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username = usernameET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean usernameValid = inputController.IsUsernameCorrect(username);
                if(usernameValid == false)
                {
                    usernameET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    usernameET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }

    public void goToSignUp(View view) {
        Intent goToSignUp = new Intent(this,SignUp.class);
        startActivity(goToSignUp);
        finish();
    }

    public void goToForgetPasswordSendEmail(View view) {
        Intent goToForgetPasswordSendEmail = new Intent(this,SendEmailForgetPassword.class);
        startActivity(goToForgetPasswordSendEmail);
        finish();
    }

    public void LoginBtn(View view) {
        if(loginValidationsChecker())
        {
            view.setClickable(false);

            Animation animation = AnimationUtils.loadAnimation(Login.this, R.anim.blink_anim);
            view.startAnimation(animation);

            User user = new User(usernameET.getText().toString() , passwordET.getText().toString());
            Call<JsonObject> userToken = userAPI.Login("application/json",user);
            userToken.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(Login.this, "username or password is not correct", Toast.LENGTH_SHORT).show();
                        view.setClickable(true);
                        view.clearAnimation();
                    }
                    else{
                        view.clearAnimation();
                        JsonObject jsonObject = response.body();
                        String token = jsonObject.get("token").toString();
                        token = token.replaceAll("\"", "");
                        //shared preferences for user information...
                        SharedPreferences UI = getSharedPreferences("userInformation",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = UI.edit();
                        myEdit.putString("token", token);
                        myEdit.putString("username",usernameET.getText().toString());
                        myEdit.apply();


                        Intent event = new Intent(Login.this, MainActivity.class);
                        startActivity(event);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(Login.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    view.clearAnimation();
                }
            });
        }
        else{
            view.setClickable(true);
            Toast.makeText(this, "Username or password is not valid", Toast.LENGTH_LONG).show();
        }
    }

    public boolean loginValidationsChecker()
    {
        if(usernameET.getText().toString().equals(""))
        {
            Toast.makeText(this, "username can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(passwordET.getText().toString().equals(""))
        {
            Toast.makeText(this, "password can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean isUsernameOk = inputController.IsUsernameCorrect(usernameET.getText().toString());
        boolean isPasswordOk = inputController.checkStringPassword(passwordET.getText().toString());

        if(isUsernameOk && isPasswordOk)
        {
            return true;
        }
        else {
            return false;
        }
    }
}