package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.R;

public class CustomeConfirmAlertDialog {
    public AlertDialog alertDialog;
    public ImageView image;
    TextView titleTV;
    TextView messageTV;
    public Button Yes;
    public Button No;
    public CustomeConfirmAlertDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_confirm,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        image = alertView.findViewById(R.id.logo_alert_s);
        titleTV = alertView.findViewById(R.id.titleTv);
        messageTV = alertView.findViewById(R.id.messageTv);
        Yes = alertView.findViewById(R.id.YesBtn);
        No = alertView.findViewById(R.id.NoBtn);

        titleTV.setText(title);
        messageTV.setText(message);
    }
}
