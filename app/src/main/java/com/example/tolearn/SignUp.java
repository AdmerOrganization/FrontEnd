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
import android.widget.EditText;
import android.widget.Toast;

import com.example.tolearn.webService.UserAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    EditText usernameET,passwordET,confirmPasswordET,emailET;
    LoginSignUpInputController inputController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        init();
    }

    public void init()
    {
        usernameET = findViewById(R.id.usernameInput);
        passwordET = findViewById(R.id.passwordInput);
        confirmPasswordET = findViewById(R.id.repeatPasswordInput);
        emailET = findViewById(R.id.emailInput);
        inputController = new LoginSignUpInputController();

        ValidationsChecker();
    }


    private void ValidationsChecker() {
        UsernameValidationChecker();
        PasswordValidationChecker();
        EmailValidationChecker();
        ConfirmPasswordValidationChecker();
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

    private void EmailValidationChecker() {
        emailET.addTextChangedListener(new TextWatcher() {

            String email = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email = emailET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean emailValid = inputController.isEmailValid(email);
                if(emailValid == false)
                {
                    emailET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    emailET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }

    private void PasswordValidationChecker() {
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

    private void UsernameValidationChecker() {
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

    public void goToLogin(View view) {
        Intent goToLogin = new Intent(this,Login.class);
        startActivity(goToLogin);
        finish();
    }

    public void signUpBtn(View view) {
        if(SignUpValidationChecker())
        {
            Intent myIntent = new Intent(this, SignUpSecondPage.class);
            myIntent.putExtra("username",usernameET.getText().toString());
            myIntent.putExtra("password",passwordET.getText().toString());
            myIntent.putExtra("confirmPassword",confirmPasswordET.getText().toString());
            myIntent.putExtra("email",emailET.getText().toString());
            startActivity(myIntent);
        }
    }

    private boolean SignUpValidationChecker() {
        boolean isSignUpOk = true;
        if(usernameET.getText().toString().equals(""))
        {
            Toast.makeText(this, "username can not be empty", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        if(inputController.IsUsernameCorrect(usernameET.getText().toString()) == false)
        {
            Toast.makeText(this, "username must be 8 letters at least.", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        if(passwordET.getText().toString().equals(""))
        {
            Toast.makeText(this, "password can not be empty", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        if(inputController.checkStringPassword(passwordET.getText().toString()) == false)
        {
            Toast.makeText(this, "password must be 8 letters at least with capital and number", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        if(confirmPasswordET.getText().toString().equals(""))
        {
            Toast.makeText(this, "confirm password can not be empty", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        if(!passwordET.getText().toString().equals(confirmPasswordET.getText().toString()))
        {
            Toast.makeText(this, "confirm password have to be the same as password", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        if(emailET.getText().toString().equals(""))
        {
            Toast.makeText(this, "Email can not be empty", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        if(inputController.isEmailValid(emailET.getText().toString()) == false)
        {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            isSignUpOk = false;
        }
        return isSignUpOk;

    }
}