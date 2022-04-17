package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.tolearn.Adapters.classAdapterManage;
import com.example.tolearn.Adapters.classAdapterSearch;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.webService.ClassAPI;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassSearch extends AppCompatActivity {
    ConstraintLayout noItemFound;
    ClassAPI classAPI;
    EditText searchEt;
    String userToken;
    ListView myEventsList;
    List<myClass> myCreatedClasses;
    classAdapterSearch myClassesAdap;
    private ShimmerFrameLayout mFrameLayout;
    NetworkInfo mWifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_search);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mFrameLayout.startShimmer();
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        //        if (mWifi.isConnected()) {
//            fillList();
//        }
//        else{
//            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//        }
        fillList();
    }

    public void init()
    {
        noItemFound = findViewById(R.id.notFoundContainer);
        LinearLayout.LayoutParams noItemParams = (LinearLayout.LayoutParams) noItemFound.getLayoutParams();
        noItemParams.height = 1;
        noItemFound.setVisibility(View.INVISIBLE);

        searchEt = findViewById(R.id.searchEventEditText);
        myEventsList = findViewById(R.id.eventsList);

        SharedPreferences sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);
        userToken = sharedPreferences.getString("token","");


        Retrofit myClasses = new Retrofit.Builder()
                .baseUrl(ClassAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI = myClasses.create(ClassAPI.class);


        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myClassesAdap.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void fillList()
    {

        Log.i("SALAM","RESID");
        Call<List<myClass>> callBack = classAPI.GetAllClasses("token "+userToken);
        callBack.enqueue(new Callback<List<myClass>>() {
            @Override
            public void onResponse(Call<List<myClass>> call, Response<List<myClass>> response) {
                if(!response.isSuccessful())
                {
                    CustomeAlertDialog myEvents = new CustomeAlertDialog(ClassSearch.this,"Response Error","There is a problem with your internet connection");
                }
                else{
                    int responseCode = response.code();
                    myCreatedClasses = response.body();
                    // Toast.makeText(my_created_events.this, Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
                    myClassesAdap = new classAdapterSearch(ClassSearch.this,myCreatedClasses,"Search");

//                    myEventsAdap = new myEventsAdapter(my_created_events.this,myEvents);
                    myEventsList.setAdapter(myClassesAdap);
                    if(myClassesAdap.getCount() == 0)
                    {
                        LinearLayout.LayoutParams noItemParams = (LinearLayout.LayoutParams) noItemFound.getLayoutParams();
                        noItemParams.height = LinearLayout.LayoutParams.MATCH_PARENT;
                        noItemFound.setVisibility(View.VISIBLE);
                    }
                    Log.i("oomad","To Else");
                    mFrameLayout.startShimmer();
                    mFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<myClass>> call, Throwable t) {
                CustomeAlertDialog myEvents = new CustomeAlertDialog(ClassSearch.this,"Error","There is a problem with your internet connection");
            }
        });
    }

    //    public void goToAddEvent(View view) {
//        Intent intent = new Intent(my_created_events.this,AddEvent.class);
//        startActivity(intent);
//        finish();
//    }
    @Override
    protected void onPause() {
        mFrameLayout.stopShimmer();
        super.onPause();
    }

}