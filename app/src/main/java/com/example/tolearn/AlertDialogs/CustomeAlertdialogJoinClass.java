package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.R;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.UserAPI;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomeAlertdialogJoinClass {
    ClassAPI classAPI;
    public AlertDialog alertDialog;
    TextView messageTV;
    EditText passwordET;
    public Button Yes;
    public Button No;
    public CustomeAlertdialogJoinClass(Context context, String ClassToken)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View alertView = LayoutInflater.from(context).inflate(R.layout.custome_alert_dialog_join_class,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        SharedPreferences shP = context.getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String token = shP.getString("token", "");

        messageTV = alertView.findViewById(R.id.messageTv);
        passwordET = alertView.findViewById(R.id.joinET);
        Yes = alertView.findViewById(R.id.YesBtn);
        No = alertView.findViewById(R.id.NoBtn);

        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordET.getText().equals(""))
                {
                    Toast.makeText(context, "Class password can not be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    view.setClickable(false);
                    //retrofit
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();

                    Retrofit SignUpRefrofit = new Retrofit.Builder()
                            .baseUrl(UserAPI.BASE_URL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    classAPI =SignUpRefrofit.create(ClassAPI.class);

                    JsonObject joinClassJson = new JsonObject();
                    joinClassJson.addProperty("password",passwordET.getText().toString());
                    joinClassJson.addProperty("classroom_token",ClassToken);

                    Call<JsonObject> joinClass = classAPI.JoinClass("token "+token,joinClassJson);
                    joinClass.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(!response.isSuccessful())
                            {
                                Toast.makeText(context, "Password is not correct", Toast.LENGTH_SHORT).show();
                                view.setClickable(true);
                            }
                            else{
                                alertDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(context, "There is a problem with your Internet connection", Toast.LENGTH_SHORT).show();
                            view.setClickable(true);
                        }
                    });
                }
            }
        });
    }
}
