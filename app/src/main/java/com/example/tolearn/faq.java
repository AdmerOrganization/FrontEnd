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
        movieList.add(new Movie("How to add task?", "", "", "In calendar page, go to a specific day(by pressing on day number) then you can see a plus sign in right corner.\nNow enter task information then press save button."));
        movieList.add(new Movie("Where can i see my tasks?", "", "", "You can see your current day tasks in list below calendar.\nAlso you can see tasks in day page(Press on day number to go to day page)."));
        movieList.add(new Movie("How to edit my profile?","","","In sidebar you can see an item named 'Edit Profile',Press on that and then press on Edit button again,Now enter you information and press button."));
        movieList.add(new Movie("What is an event?","","","Basically an event is a task shared between a group of users,you can create one in My event item."));
        movieList.add(new Movie("Where can i see my events?","","","There is an item named 'My events',You can find it in sidebar."));
        movieList.add(new Movie("How to join an event?","","","If it is private go to private section in side bar else go to Search & Join"));
        movieList.add(new Movie("How to sync with google calendar?","","","There is a button named sync you can enter you google account information there."));
        movieList.add(new Movie("What are sessions in event?","","","Basically sessions divide event to various time parts,So you can reserve a specific time."));
        MovieAdapter movieAdapter = new MovieAdapter(movieList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(faq.this));
        recyclerView.setAdapter(movieAdapter);
    }
}