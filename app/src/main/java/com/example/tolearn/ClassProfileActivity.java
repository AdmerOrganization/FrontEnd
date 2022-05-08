package com.example.tolearn;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.databinding.ActivityClassProfileBinding;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

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
    HomeworkAPI homeworkAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit Homeworks = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI = Homeworks.create(HomeworkAPI.class);

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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("classroom",class_id);
        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+user_token,jsonObject);
        callBack.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                homeworktypeList = response.body();
                Log.i("salam","sas");
            }

            @Override
            public void onFailure(Call<List<Homework>> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(ClassProfileActivity.this,"error","there is a problem with your internet connection");

            }
        });


        setSupportActionBar(binding.appBarClassProfile.classProfiletoolbar);
        binding.appBarClassProfile.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shp2 = getSharedPreferences("classId",MODE_PRIVATE);
                String access = shp2.getString("user_access","");

                if(access.equals("teacher"))
                {
                    Intent createHomework = new Intent(ClassProfileActivity.this,HomeworkCreationDialog.class);
                    createHomework.putExtra("id",class_id);
                    startActivity(createHomework);
                }
                else{
                    Toast.makeText(ClassProfileActivity.this, "You don't have the right access to create a homework for this class", Toast.LENGTH_LONG).show();
                }
            }
        });
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