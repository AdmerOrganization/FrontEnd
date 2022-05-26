package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.HomeworkEditDialog;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
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
import java.util.Calendar;
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

public class DetailSubmit extends AppCompatActivity {
    Bundle extras;
    String title , Desc , Deadline , homeworkToken , path , pdfLink;
    TextView titleTextView , DescTextView , DeadlineTextView;
    int homeworkId;
    HomeworkAPI homeworkAPI;
    public Button fileSelection;
    public Button homeworkSubmitBtn;
    public Button getPdf;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/Download";
    private static final int PICK_PDF_FOR_FILE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        path = "";
        Retrofit LoginRetrofit = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI =LoginRetrofit.create(HomeworkAPI.class);
        extras = getIntent().getExtras();
        setContentView(R.layout.activity_detail_submit);
        titleTextView = findViewById(R.id.detailTitleET);
        DescTextView = findViewById(R.id.detailDescET);
        DeadlineTextView = findViewById(R.id.detailDeadlineET);
        if (extras != null) {
            title = extras.getString("homework_title");
            homeworkToken = extras.getString("homework_token");
            Deadline = extras.getString("homework_deadline");
            homeworkId = extras.getInt("homework_id");
            titleTextView.setText(title);
           // DescTextView.setText(Desc);
            DeadlineTextView.setText(Deadline);
        }
        fileSelection = findViewById(R.id.detailHomeworkPdfSelection);
        homeworkSubmitBtn = findViewById(R.id.detailSubmitHomework);
        getPdf = findViewById(R.id.detailHomeworkGetPdf);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("homework_token",homeworkToken);
        SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
        String userToken = sharedPreferences.getString("token","");
        Call<Homework> callback = homeworkAPI.Display("token "+userToken,jsonObject);
        callback.enqueue(new Callback<Homework>() {
            @Override
            public void onResponse(Call<Homework> call, Response<Homework> response) {
                if(!response.isSuccessful())
                {
                    Log.i("RESPONES ERORRRRRR",response.errorBody().toString());
                    CustomeAlertDialog myEvents = new CustomeAlertDialog(DetailSubmit.this,"Response Error","There is a problem with your internet connection");
                }
                else{
                    Homework currHomework = response.body();
                    DescTextView.setText(currHomework.getDescription());
                    homeworkId = currHomework.getId();
                    pdfLink = currHomework.getFile();
                }
            }

            @Override
            public void onFailure(Call<Homework> call, Throwable t) {
                CustomeAlertDialog myEvents = new CustomeAlertDialog(DetailSubmit.this,"Error","There is a problem with your internet connection");
            }
        });
        getPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDownload();
            }
        });
        fileSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseDocuments();
            }
        });
        homeworkSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String deadLine = DeadlineTextView.getText().toString();
                    String [] Date = deadLine.split("-");
                    int year = Integer.parseInt(Date[0]);
                    int month = Integer.parseInt(Date[1]);
                    int day = Integer.parseInt(Date[2]);

                Calendar c = Calendar.getInstance();
                int currentDay = c.get(Calendar.DAY_OF_MONTH);
                int currentMonth = c.get(Calendar.MONTH);
                currentMonth = currentMonth + 1;
                int currentYear = c.get(Calendar.YEAR);

                    if(year>currentYear ||(year == currentYear && currentMonth<month)||(year == currentYear && month == currentMonth && day > currentDay) || (year == currentYear && month == currentMonth && day == currentDay))
                    {
                        if(!path.equals(""))
                        {
                            File file = new File(path);
                            RequestBody requestFile =
                                    RequestBody.create(MediaType.parse("*/*"), file);

// MultipartBody.Part is used to send also the actual file name
                            MultipartBody.Part body =
                                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                            SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                            String token = shP.getString("token", "");
                            Call<JsonObject> homeworkCall = homeworkAPI.Submit("token " + token,homeworkId, body);
                            Log.i("5", "5");
                            homeworkCall.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(DetailSubmit.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                                        Log.i("MOSHKEL", response.message());
                                    } else {
                                        String code = Integer.toString(response.code());
                                        JsonObject homeworkSubmitResponse = response.body();
                                        finish();
                                        Log.i("PHOTO", "SUCCED");
                                        //                           Log.i("IMAGE URL",user.getAvatar().toString());
                                        Toast.makeText(DetailSubmit.this, "Homework updated!", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Log.i("moshkel","injas");
                                    Log.i("Moshkel",t.getMessage());
                                    Toast.makeText(DetailSubmit.this, "error is :" + t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(DetailSubmit.this, "You have not chosen any pdf file ", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(DetailSubmit.this, "Deadline is over", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
    private void initDownload() {
        String uri = pdfLink;
        download(getApplicationContext(), title, ".pdf", "Downloads", uri.trim());
    }
    private void download(Context context, String Filename, String FileExtension, String DesignationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, DesignationDirectory, Filename + FileExtension);
        assert downloadManager != null;
        downloadManager.enqueue(request);
        Snackbar snackbar = (Snackbar) Snackbar
                .make(findViewById(android.R.id.content), "Downloading...", Snackbar.LENGTH_LONG);
        snackbar.show();
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
}