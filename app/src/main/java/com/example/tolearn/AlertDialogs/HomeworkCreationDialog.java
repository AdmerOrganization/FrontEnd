package com.example.tolearn.AlertDialogs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.Entity.User;
import com.example.tolearn.MainActivity;
import com.example.tolearn.R;

import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.tolearn.Controllers.homework_creation_validations;
import com.example.tolearn.webService.HomeworkAPI;
import com.example.tolearn.webService.UserAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

public class HomeworkCreationDialog extends Activity {
    public EditText titleET;
    public EditText descET;
    public DatePicker datePicker;
    public Button fileSelection;
    public Button homeworkCreation;
    HomeworkAPI homeworkAPI;
    Bundle extras;
    int class_id;
    File file;
    public homework_creation_validations Controller;
    private static final int PICK_PDF_FOR_FILE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Files.FileColumns.DISPLAY_NAME};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_FOR_FILE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                Log.i("0", "0");
                Uri u = data.getData();
                String filePath = getPath(u);
                Log.i("DATA", u.toString());
                Log.i("DATA2", filePath);
//                profileImage = findViewById(R.id.profileImageSource);
//                profileImage.setImageURI(data.getData());
                file = new File(filePath);
                Log.i("file name", file.getName());
                Log.i("1", "1");
                Log.i("3", "3");
                Log.i("4", "4");
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
    private void browseDocuments(){

        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), PICK_PDF_FOR_FILE);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Controller = new homework_creation_validations();
        extras = getIntent().getExtras();
        class_id = extras.getInt("id");
        super.onCreate(savedInstanceState);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit createHomeworkRetro = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI =createHomeworkRetro.create(HomeworkAPI.class);
        //  verifyStoragePermissions();
        setContentView(R.layout.homework_creation_dialog);

        //init
        titleET = findViewById(R.id.titleET);
        descET = findViewById(R.id.descET);
        datePicker = findViewById(R.id.datePicker);
        fileSelection = findViewById(R.id.homeworkPdfSelection);
        homeworkCreation = findViewById(R.id.HomeworkCreate);

        fileSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                browseDocuments();

            }
        });

        homeworkCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                month = month + 1;
                int day = datePicker.getDayOfMonth();
                if (!Controller.IsTitleCorrect(titleET.getText().toString())) {
                    Toast.makeText(HomeworkCreationDialog.this, "Please enter a title for homework", Toast.LENGTH_SHORT).show();
                } else if (!Controller.IsDescCorrect(descET.getText().toString())) {
                    Toast.makeText(HomeworkCreationDialog.this, "Please enter a desc for homework", Toast.LENGTH_SHORT).show();
                } else if (!Controller.IsDateValid(year, month, day)) {
                    Toast.makeText(HomeworkCreationDialog.this, "You can not select a date in the past.", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    RequestBody titleR =
                            RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(titleET));
                    RequestBody descR =
                            RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(descET));
                    RequestBody dateR =
                            RequestBody.create(MediaType.parse("multipart/form-data"), year+"-"+month+"-"+day);
                    SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                    String token = shP.getString("token", "");
                    Call<Homework> homeworkCall = homeworkAPI.Create("token " + token, titleR, descR, dateR,class_id, body);
                    Log.i("5", "5");
                    homeworkCall.enqueue(new Callback<Homework>() {
                        @Override
                        public void onResponse(Call<Homework> call, Response<Homework> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(HomeworkCreationDialog.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                                Log.i("MOSHKEL", response.message());
                            } else {
                                String code = Integer.toString(response.code());
                                Homework homework = response.body();
                                Log.i("PHOTO", "SUCCED");
                                //                           Log.i("IMAGE URL",user.getAvatar().toString());
                                Toast.makeText(HomeworkCreationDialog.this, "Homework created!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Homework> call, Throwable t) {
                            Log.i("moshkel","injas");
                            Toast.makeText(HomeworkCreationDialog.this, "error is :" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}