package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.R;

public class CustomeAlertDialog {

    public AlertDialog alertDialog;
    public ImageView imageView;
    TextView titleTV;
    TextView messageTV;
    public Button btnOk;
    public CustomeAlertDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        imageView = alertView.findViewById(R.id.logo_alert_s);
        titleTV = alertView.findViewById(R.id.titleTv);
        messageTV = alertView.findViewById(R.id.messageTv);
        btnOk = alertView.findViewById(R.id.okBtn);

        titleTV.setText(title);
        messageTV.setText(message);
    }
}