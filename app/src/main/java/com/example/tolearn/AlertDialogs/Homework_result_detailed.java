package com.example.tolearn.AlertDialogs;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.R;
import com.google.android.material.snackbar.Snackbar;

public class Homework_result_detailed {
    public AlertDialog alertDialog;
    TextView fullNameTv;
    TextView dateTv;
    public Button downloadBtn;

    public Homework_result_detailed(Context context , String fullname , String date , String url)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.homework_result_detailed,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        fullNameTv = alertView.findViewById(R.id.memberNameTv);
        dateTv = alertView.findViewById(R.id.dateTv);
        downloadBtn = alertView.findViewById(R.id.downloadBtn);

        fullNameTv.setText(fullname);

        String [] dateArr = date.split("T1");
        String [] timeArr = dateArr[1].split(":");
        String timeStr = timeArr[0] + ":" +  timeArr[1];
        String dateTimeStr = dateArr[0] + " " + timeStr;

        dateTv.setText("submitted at: "+ dateTimeStr);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDownload(url , context , fullname , date);
                alertDialog.dismiss();
            }
        });
    }

    private void initDownload(String pdfLink , Context context ,String full_name , String date) {
        String uri = pdfLink;
        download(context, full_name+"_"+date, ".pdf", "Downloads", uri.trim());
    }
    private void download(Context context, String Filename, String FileExtension, String DesignationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, DesignationDirectory, Filename + FileExtension);
        assert downloadManager != null;
        downloadManager.enqueue(request);
        Toast.makeText(context, "downloading .....", Toast.LENGTH_SHORT).show();
    }
}
