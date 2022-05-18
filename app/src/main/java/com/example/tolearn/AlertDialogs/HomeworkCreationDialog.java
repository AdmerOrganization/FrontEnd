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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.tolearn.Controllers.homework_creation_validations;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.R;
import com.example.tolearn.webService.HomeworkAPI;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/Download";
    Bundle extras;
    int class_id;
    File file;
    String path;
    public homework_creation_validations Controller;
    private static final int PICK_PDF_FOR_FILE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        //int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

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
    public static String getRealPathFromUri(Context ctx, Uri uri) {
        String[] filePathColumn = { MediaStore.Files.FileColumns.DISPLAY_NAME };

        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        String picturePath="";
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("", "picturePath : " + picturePath);
            cursor.close();
        }
        return picturePath;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_PDF_FOR_FILE && resultCode == Activity.RESULT_OK) {
//            if (data == null) {
//                //Display an error
//                return;
//            }
//            try {
////                FileUtils fileUtils = new FileUtils(this);
//
//                Log.i("0", "0");
//                Uri u = data.getData();
////                String filePath = FileUtils.getPath(u);
//                String filePath = getPath(u);
////                String filePath = getRealPathFromUri(this,u);
//                Log.i("DATA", u.toString());
//                Log.i("DATA2", filePath);
////                profileImage = findViewById(R.id.profileImageSource);
////                profileImage.setImageURI(data.getData());
//                file = new File(filePath);
//                Log.i("file name", file.getName());
//                Log.i("1", "1");
//                Log.i("3", "3");
//                Log.i("4", "4");
//                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
//        }
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();

            File myFile = new File(uriString);
            path = getFilePathFromURI(this,uri);
            Log.d("iooooooooooooooooooooo",path);
            //uploadPDF(path);
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
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
        requestMultiplePermissions();
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), PICK_PDF_FOR_FILE);

    }
    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivity(intent);
        verifyStoragePermissions(this);
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
        setContentView(R.layout.homework_creation_dialog);

        //init
        titleET = findViewById(R.id.detailTitleET);
        descET = findViewById(R.id.detailDescET);
        datePicker = findViewById(R.id.datePicker);
        fileSelection = findViewById(R.id.detailHomeworkPdfSelection);
        homeworkCreation = findViewById(R.id.detailSubmitHomework);

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
                    File file = new File(path);
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("*/*"), file);

// MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    RequestBody titleR =
                            RequestBody.create(MediaType.parse("multipart/form-data"), titleET.getText().toString());
                    RequestBody descR =
                            RequestBody.create(MediaType.parse("multipart/form-data"), descET.getText().toString());
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
                            Log.i("Moshkel",t.getMessage());
                            Toast.makeText(HomeworkCreationDialog.this, "error is :" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}