package com.example.tolearn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.HomeworkCreationDialog;
import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.ExamNew;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.databinding.ActivityClassProfileBinding;
import com.example.tolearn.webService.ExamAPI;
import com.example.tolearn.webService.HomeworkAPI;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassProfileActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityClassProfileBinding binding;
    Bundle extras;
    public String title,teacher,category,user_token,user_access;
    public int class_id;
    public List<Homework> homeworktypeList;
    public JsonArray examtypeList;
    HomeworkAPI homeworkAPI;
    ExamAPI examAPI;
    TextView homeworkTitleTextview;
    TextView examTitleTextview;
    TextView examCountTextview;
    TextView homeworkDeadlineTextview ;
    private ShimmerFrameLayout mFrameLayout;

    public void fillItems(){
        homeworktypeList = new ArrayList<>();
        examtypeList = new JsonArray();

        SharedPreferences sharedPreferences = ClassProfileActivity.this.getSharedPreferences("userInformation",ClassProfileActivity.this.MODE_PRIVATE);
        String user_token = sharedPreferences.getString("token","");

        JsonObject jsonObject = new JsonObject();
        SharedPreferences shP2 = ClassProfileActivity.this.getSharedPreferences("classId", ClassProfileActivity.this.MODE_PRIVATE);
        String id = shP2.getString("Id", "");
        int classroom_id = Integer.parseInt(id);
        jsonObject.addProperty("classroom",classroom_id);
        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+user_token,jsonObject);
        callBack.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(ClassProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                else{
                    homeworktypeList = response.body();
                    Log.i("salam","sas");
                    if(homeworktypeList.size()>0)
                    {
                        try {
                            homeworkTitleTextview.setText(homeworktypeList.get(homeworktypeList.size()-1).getTitle());
                            homeworkDeadlineTextview.setText(homeworktypeList.get(homeworktypeList.size()-1).getDeadline());
                        }
                        catch (Exception exception)
                        {
                            //nothing
                        }
                    }
                    //     mFrameLayout.startShimmer();
                    //
                }
            }

            @Override
            public void onFailure(Call<List<Homework>> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(ClassProfileActivity.this,"error","there is a problem with your internet connection");
                Log.i("ERROR2",t.getMessage());
            }
        });



        JsonObject classIdJson = new JsonObject();
        classIdJson.addProperty("classroom",classroom_id);
        Call<JsonArray> callBackNew = examAPI.GetAllExamsJsonForClass("token "+user_token,classIdJson);
        callBackNew.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful())
                {
                    JsonArray Response = response.body();
                    examtypeList = Response;
                    if(Response.size()>0)
                    {
                        Log.i("salam","sas222222222222");
                        try {
                            JsonObject jo = Response.get(Response.size()-1).getAsJsonObject();
                            String name = jo.get("name").toString();
                            name = name.replace("\"","");
                            examTitleTextview.setText(name);
                            String start = jo.get("start_time").toString();
                            start = start.replace("T"," ");
                            String end = jo.get("start_time").toString();
                            end = end.replace("T"," ");
                            start = start.replace(":00Z","\n");
                            end = end.replace(":00Z","");
                            start = start.replace("\"","");
                            end = end.replace("\"","");

                            examCountTextview.setText(start  + end);
                        }
                        catch (Exception exception)
                        {
                            //nothing
                        }
                    }
                }
                else{
                    Toast.makeText(ClassProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(ClassProfileActivity.this,"error","there is a problem with your internet connection");
                Log.i("ERROR",t.getMessage());
            }
        });
    }
    @Override
    protected void onPause() {
    //    mFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit Homeworks = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI = Homeworks.create(HomeworkAPI.class);
        Retrofit Exams = new Retrofit.Builder()
                .baseUrl(ExamAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        examAPI = Exams.create(ExamAPI.class);


        //  mFrameLayout = findViewById(R.id.shimmerLayout);
        extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("class_name");
            teacher = extras.getString("class_teacher");
            category = extras.getString("class_category");
            class_id = extras.getInt("class_id");
            user_token = extras.getString("user_token");
            user_access = extras.getString("user_access");

            SharedPreferences classId = getSharedPreferences("classId",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = classId.edit();
            myEdit.putString("Id",String.valueOf(class_id));
            myEdit.putString("user_access",user_access);
            myEdit.apply();
        }

        binding = ActivityClassProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        homeworkTitleTextview = findViewById(R.id.homeworkTextview);
        examTitleTextview = findViewById(R.id.examTextview);
        examCountTextview = findViewById(R.id.questionCountTextview);
        homeworkDeadlineTextview = findViewById(R.id.deadlineTextview);

        setSupportActionBar(binding.appBarClassProfile.classProfiletoolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_homework, R.id.nav_exam)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_class_profile);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.classNavName)).setText(title);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.classNavTeacher)).setText(teacher);
        if(!category.equals("")){
            ImageView imageViewCategory = ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.classNavImage));
            switch (category)
            {
                case "Math":
                    imageViewCategory.setImageResource(R.drawable.math);
                    break;
                case "Chemistry":
                    imageViewCategory.setImageResource(R.drawable.chemistry);
                    break;
                case "Physics":
                    imageViewCategory.setImageResource(R.drawable.atom);
                    break;
                case "Engineering":
                    imageViewCategory.setImageResource(R.drawable.engineering);
                    break;
                case "Paint":
                    imageViewCategory.setImageResource(R.drawable.paint);
                    break;
                case "Music":
                    imageViewCategory.setImageResource(R.drawable.musical);
                    break;
                case "Cinema":
                    imageViewCategory.setImageResource(R.drawable.clapperboard);
                    break;
                case "athletic":
                    imageViewCategory.setImageResource(R.drawable.athletics);
                    break;
                case "computer science":
                    imageViewCategory.setImageResource(R.drawable.responsive);
                    break;
                case "language":
                    imageViewCategory.setImageResource(R.drawable.languages);
                    break;
                default:
                    Picasso.get().load("123").placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(imageViewCategory);
                    break;
            }
        }
        fillItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.class_profile, menu);
        return true;
    }
    public List<Homework> getHomeworktypeList() {
        return homeworktypeList;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_class_profile);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}