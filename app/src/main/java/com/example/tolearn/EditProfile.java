package com.example.tolearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.tolearn.Entity.User;
import com.example.tolearn.webService.UserAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfile extends AppCompatActivity {
    EditText emailEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText phoneNumberEditText;
    UserAPI userAPI;
    String token;
    CardView editbutton;
    LoginSignUpInputController inputController;

    public void EditButtonClicked(View view){
        if(inputValidations())
        {
            editbutton = (CardView) findViewById(R.id.cardViewEditProfile);
            Animation animation10 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_anim);
            editbutton.startAnimation(animation10);
            view.setClickable(false);

            User user = new User(firstNameEditText.getText().toString(),lastNameEditText.getText().toString(),emailEditText.getText().toString(),phoneNumberEditText.getText().toString());
            Call<User> userSessionCall = userAPI.editProfile("token "+ token, user);

            userSessionCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(!response.isSuccessful())
                    {
                        Toast.makeText(EditProfile.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                        Log.i("MOSHKEL",response.message());
                        view.setClickable(true);
                        editbutton.clearAnimation();
                    }
                    else{
                        String code = Integer.toString(response.code());
                        User user = response.body();
                        Toast.makeText(EditProfile.this, "Profile Edited!", Toast.LENGTH_SHORT).show();
                        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = shP.edit();
                        myEdit.putString("firstname",firstNameEditText.getText().toString());
                        myEdit.putString("lastname",lastNameEditText.getText().toString());
                        myEdit.putString("email",emailEditText.getText().toString());
                        myEdit.putString("phonenumber",phoneNumberEditText.getText().toString());
                        myEdit.apply();
                        Intent intent = new Intent(EditProfile.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(EditProfile.this, "error is :"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    editbutton.clearAnimation();
                }
            });
        }
        else{

            if(!inputController.isPhoneNumber(phoneNumberEditText.getText().toString()))
            {
                Toast.makeText(this, "Phone number is not valid", Toast.LENGTH_SHORT).show();
            }
            if(!inputController.isEmailValid(emailEditText.getText().toString()))
            {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "Some Field is Wrong. Please try again", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_profile);

        emailEditText = findViewById(R.id.eemailid);
        firstNameEditText = findViewById(R.id.firstNameIde);
        lastNameEditText = findViewById(R.id.lastNameIde);
        phoneNumberEditText = findViewById(R.id.phoneNumberIde);

        SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
        token = shP.getString("token", "");
        String firstName = shP.getString("firstname","");
        String lastName = shP.getString("lastname","");
        String email = shP.getString("email","");
        String phoneNumber = shP.getString("phonenumber","");
        if(!firstName.equals("")){
            firstNameEditText.setText(firstName);
        }
        else{
            firstNameEditText.setHint(R.string.empty);
        }
        if(!lastName.equals("")){
            lastNameEditText.setText(lastName);
        }
        else{
            lastNameEditText.setHint(R.string.empty);
        }
        if(!email.equals("")){
            emailEditText.setText(email);
        }
        else{
            emailEditText.setHint(R.string.empty);
        }
        if(!phoneNumber.equals("")){
            phoneNumberEditText.setText(phoneNumber);
        }
        else{
            phoneNumberEditText.setHint(R.string.empty);
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit LoginRetrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI =LoginRetrofit.create(UserAPI.class);

        inputController = new LoginSignUpInputController();
        inputChecker();
    }

    public void inputChecker()
    {
        EmailValidationChecker();
        PhoneValidationChecker();
    }

    private void EmailValidationChecker() {
        emailEditText.addTextChangedListener(new TextWatcher() {

            String email = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email = emailEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean emailValid = inputController.isEmailValid(email);
                if(emailValid == false)
                {
                    emailEditText.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    emailEditText.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }


    private void PhoneValidationChecker() {
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {

            String phone = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone = phoneNumberEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean phoneValid = inputController.isPhoneNumber(phone);
                if(phoneValid == false)
                {
                    phoneNumberEditText.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    phoneNumberEditText.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }

    public boolean inputValidations()
    {
        boolean inputValid = true;
        if(!firstNameLastNameValidation())
        {
            inputValid = false;
        }
        if(!inputController.isPhoneNumber(phoneNumberEditText.getText().toString()))
        {
            inputValid = false;
            Toast.makeText(this, "Phone number is not valid", Toast.LENGTH_SHORT).show();
        }
        if(!inputController.isEmailValid(emailEditText.getText().toString()))
        {
            inputValid = false;
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
        }
        return inputValid;
    }

    public boolean firstNameLastNameValidation()
    {
        String firstname = firstNameEditText.getText().toString();
        String lastname = lastNameEditText.getText().toString();
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

    public void resetPassword(View view) {
        Intent goToforgetPassSendEmail = new Intent(this,SendEmailForgetPassword.class);
        startActivity(goToforgetPassSendEmail);
    }
}