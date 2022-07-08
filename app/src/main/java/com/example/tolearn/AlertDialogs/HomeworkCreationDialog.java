package com.example.tolearn.AlertDialogs;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.tolearn.Controllers.homework_creation_validations;
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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.tolearn.webService.VolleyMultipartRequest;

public class HomeworkCreationDialog extends Activity {
    private static String pdfFileName;
    public EditText titleET;
    public EditText descET;
    public DatePicker datePicker;
    public Button fileSelection;
    public Button homeworkCreation;
    HomeworkAPI homeworkAPI;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/Download";
    private static final String upload_URL = "http://185.235.42.101:8000/homeworks/create/";
    Bundle extras;
    int class_id;
    File file;
    String path;
    Uri fileuri;

    //new method ...
    String displayName = null;
    Uri uri;

    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;

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
    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            // Get the Uri of the selected file
//            fileuri = data.getData();
//            String uriString = fileuri.toString();
//
//            File myFile = new File(uriString);
//            path = getFilePathFromURI(this,fileuri);
//            Log.d("iooooooooooooooooooooo",path);
//            //uploadPDF(path);
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();


            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                        //uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);


    }
    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        pdfFileName = fileName;
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
        path = "";
        verifyStoragePermissions(this);
        Controller = new homework_creation_validations();
        extras = getIntent().getExtras();
//        class_id = extras.getInt("id");
        SharedPreferences shP = getSharedPreferences("classId", MODE_PRIVATE);
        String id = shP.getString("Id", "");
        class_id = Integer.parseInt(id);
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

                //browseDocuments();
                requestMultiplePermissions();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,1);
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
                    if(!uri.equals(""))
                    {
                        uploadPDF(displayName , uri);
                        finish();
                    }
                    else{
                        Toast.makeText(HomeworkCreationDialog.this, "select a pdf file", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,HomeworkCreationDialog.this,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                jsonObject.toString().replace("\\\\","");

                                if (jsonObject.getString("status").equals("true")) {
                                    Log.d("come::: >>>  ","yessssss");
                                    arraylist = new ArrayList<HashMap<String, String>>();
                                    JSONArray dataArray = jsonObject.getJSONArray("data");


                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("title",titleET.getText().toString());
                    params.put("description",descET.getText().toString());
                    params.put("classroom",Integer.toString(class_id));
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    month = month + 1;
                    int day = datePicker.getDayOfMonth();
                    params.put("deadline",year+"-"+month+"-"+day);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("file", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(HomeworkCreationDialog.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    class DataPart {
        private String fileName;
        private byte[] content;
        private String type;

        public DataPart() {
        }

        DataPart(String name, byte[] data) {
            fileName = name;
            content = data;
        }

        String getFileName() {
            return fileName;
        }

        byte[] getContent() {
            return content;
        }

        String getType() {
            return type;
        }

    }
}