package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

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
                    confirmPasswordET.setBackgroundResource(R.drawable.border_shadow_white_background);
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
        SignUpValidationChecker();
    }

    private void SignUpValidationChecker() {
        boolean isUsername = inputController.IsUsernameCorrect(usernameET.getText().toString());
        //todo

        // in the list ...
        //username and password and .. can not be empty in login or sign up
        //in the sign up show the appropriate sentence for each of the cases:
        //1- username is not correct 2- password is not correct 3-email is not correct 4- confirm password ...
    }
}