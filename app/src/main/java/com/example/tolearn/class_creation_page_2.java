package com.example.tolearn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.Controllers.classCreationValidations;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.UserAPI;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class class_creation_page_2 extends AppCompatActivity {

    ClassAPI classAPI;
    Spinner limitET;
    EditText passwordET;
    String title;
    String teacher;
    String desc;
    ImageView classImage;
    String filePath;
    classCreationValidations Controller;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_class_creation_page2);
        getSupportActionBar().hide();
        init();
    }

    public void init()
    {
        filePath ="";
        limitET = findViewById(R.id.limit_spinner);
        passwordET = findViewById(R.id.classPasswordET);

        Intent pre_page = getIntent();
        title = pre_page.getStringExtra("title");
        teacher = pre_page.getStringExtra("teacher");
        desc = pre_page.getStringExtra("desc");
        classImage = findViewById(R.id.classImage);

        Controller = new classCreationValidations();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();


        Retrofit SignUpRefrofit = new Retrofit.Builder()
                .baseUrl(UserAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI =SignUpRefrofit.create(ClassAPI.class);

        fieldValidations();
        verifyStoragePermissions(this);

    }

    public void fieldValidations()
    {
        passwordET.addTextChangedListener(new TextWatcher() {
            String classPassword = "";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                classPassword = passwordET.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Controller.ClassPassword(classPassword))
                {
                    passwordET.setBackgroundResource(R.drawable.border_error_shadow);
                }
                else{
                    passwordET.setBackgroundResource(R.drawable.border_shadow_white_background);
                }
            }
        });
    }

    public boolean registerVliadation(String password)
    {
        if(!Controller.ClassPassword(password))
        {
            Toast.makeText(this, "password is not valid . Password have to be at least 8 characters with at least one capital letter and one number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void ClassRegister(View view) {

        if(registerVliadation(passwordET.getText().toString()))
        {
            view.setClickable(false);

            Animation animation = AnimationUtils.loadAnimation(class_creation_page_2.this,R.anim.blink_anim);
            view.startAnimation(animation);

            SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
            String token = shP.getString("token", "");

            if(filePath.equals(""))
            {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("title",title);
                jsonObject.addProperty("teacher_name",teacher);
                jsonObject.addProperty("description",desc);
                jsonObject.addProperty("limit",limitET.getSelectedItem().toString());
                jsonObject.addProperty("password",passwordET.getText().toString());

                Call<JsonObject> classCreationWithoutAvatar = classAPI.CreateClassWithoutAvatar("token "+token,jsonObject);
                classCreationWithoutAvatar.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(class_creation_page_2.this, "There is a problem with your connection", Toast.LENGTH_SHORT).show();
                            view.setClickable(true);
                            view.clearAnimation();
                        }
                        else
                        {
                            CustomeAlertDialog classcreatedMessage = new CustomeAlertDialog(class_creation_page_2.this , "Successful", "class created successfully");
                            classcreatedMessage.btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
                                    Intent goToMainPage = new Intent(class_creation_page_2.this,MainActivity.class);
                                    startActivity(goToMainPage);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(class_creation_page_2.this, "There is a problem with your connection", Toast.LENGTH_SHORT).show();
                        view.setClickable(true);
                        view.clearAnimation();
                    }
                });
            }
            else{
                File file = new File(filePath);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
// MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                RequestBody titleR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), title);
                RequestBody teacherR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), teacher);
                RequestBody descR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), desc);
                RequestBody passwordR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), passwordET.getText().toString());
                RequestBody limitR =
                        RequestBody.create(MediaType.parse("multipart/form-data"), limitET.getSelectedItem().toString());


                Call<JsonObject> classCreation = classAPI.CreateClass("token "+ token, titleR,body,teacherR,descR,limitR,passwordR);
                classCreation.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(class_creation_page_2.this, "There is a problem with your connection", Toast.LENGTH_SHORT).show();
                            view.setClickable(true);
                            view.clearAnimation();
                        }
                        else{
                            CustomeAlertDialog classcreatedMessage = new CustomeAlertDialog(class_creation_page_2.this , "Successful", "class created successfully");
                            classcreatedMessage.btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
                                    Intent goToMainPage = new Intent(class_creation_page_2.this,MainActivity.class);
                                    startActivity(goToMainPage);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(class_creation_page_2.this, "There is a problem with your connection", Toast.LENGTH_SHORT).show();
                        view.setClickable(true);
                        view.clearAnimation();
                    }
                });
            }

        }
    }

    public String getPath (Uri uri)
    {
        String [] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection ,null,null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void PickClassImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK)
        {
            if(data == null)
            {
                Log.i("dataaaaaaaaa",data.toString());
                return;
            }
            else{
                try{
                    Log.i("dataaaaaaaaa",data.toString());
                    Uri u = data.getData();
                    filePath = getPath(u);
                    classImage.setImageURI(data.getData());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}