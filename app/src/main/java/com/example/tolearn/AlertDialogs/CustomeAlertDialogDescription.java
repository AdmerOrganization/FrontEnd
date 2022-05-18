package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.R;

public class CustomeAlertDialogDescription {
    public AlertDialog alertDialog;
    public EditText desc;
    public Button btnOK;

    public CustomeAlertDialogDescription(Context context , TextView descriptionField )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomDialog);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.custome_desc_dialog,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        desc = alertView.findViewById(R.id.detailDescET);
        desc.setText(descriptionField.getText().toString());

        btnOK = alertView.findViewById(R.id.okBtn);
    }
}
