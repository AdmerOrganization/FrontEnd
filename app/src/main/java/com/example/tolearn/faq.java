package com.example.tolearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.tolearn.Adapters.MovieAdapter;
import com.example.tolearn.Entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class faq extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faq);
        RecyclerView recyclerView;
        List<Movie> movieList;
        movieList = new ArrayList<>();
        movieList.add(new Movie("How to sign up?", "", "", "If you don't have one, You can easily make one in sign up page by entering username, email and password."));
        movieList.add(new Movie("How to login?", "", "", "After you made an account you can go to login page and enter your username and password."));
        movieList.add(new Movie("I can't remember my password", "", "", "if you forgot your password, click on the button 'I forgot password' then we email verification code to your email so you can reset your password."));
        movieList.add(new Movie("i can't update photo profile", "", "", "Check permission to access local files so you can choose on and upload it."));
        movieList.add(new Movie("How to add class?", "", "", "In main page, go to a navigation menu and create class .\nNow enter class information then press save button."));
        movieList.add(new Movie("Where can i see my classes?", "", "", "You can see your current classes in main page.\nAlso you can see them in joined class and created class in navigation menu  p(Press on button top left)."));
        movieList.add(new Movie("How to edit my profile?","","","In sidebar you can see an item named 'Edit Profile',Press on that and then press on Edit button again,Now enter you information and press button."));
        movieList.add(new Movie("Where can i see my homeworks?","","","There is an item named Homeworks located in class page,You can find it in sidebar."));
        movieList.add(new Movie("How to join an class?","","","You can go to search & join and enter class password"));
        movieList.add(new Movie("How to take exam?","","","There is an item named Exams located in class page,You can find it in sidebar."));
        MovieAdapter movieAdapter = new MovieAdapter(movieList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(faq.this));
        recyclerView.setAdapter(movieAdapter);
    }
}