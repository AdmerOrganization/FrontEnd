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
import android.widget.Toast;

public class Login extends AppCompatActivity {

    LoginSignUpInputController inputController;
    EditText usernameET;
    EditText passwordET;

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
            //todo
            //go connect to the back end for login....
        }
        else{
            //todo
            //make a special message activity for messages and show it...
            Toast.makeText(this, "Username or password is not valid", Toast.LENGTH_LONG).show();
        }
    }

    public boolean loginValidationsChecker()
    {
        boolean isUsernameOk = inputController.IsUsernameCorrect(usernameET.getText().toString());
        boolean isPasswordOk = inputController.checkStringPassword(passwordET.getText().toString());

        if(isPasswordOk && isPasswordOk)
        {
            return true;
        }
        else {
            return false;
        }
    }
}