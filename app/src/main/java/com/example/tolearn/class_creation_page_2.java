package com.example.tolearn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tolearn.Controllers.classCreationValidations;

import java.io.File;
import java.io.FileNotFoundException;

public class class_creation_page_2 extends AppCompatActivity {

    Spinner limitET;
    EditText passwordET;
    String title;
    String teacher;
    String desc;
    ImageView classImage;
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
        limitET = findViewById(R.id.limit_spinner);
        passwordET = findViewById(R.id.classPasswordET);

        Intent pre_page = getIntent();
        title = pre_page.getStringExtra("title");
        teacher = pre_page.getStringExtra("teacher");
        desc = pre_page.getStringExtra("desc");
        classImage = findViewById(R.id.classImage);

        Controller = new classCreationValidations();

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
            //todo
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
                    String filePath = getPath(u);
                    classImage.setImageURI(data.getData());
                    File file = new File(filePath);

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