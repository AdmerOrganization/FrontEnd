package com.example.tolearn.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.DetailSubmit;
import com.example.tolearn.Entity.ExamNew;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.ExamUpdate;
import com.example.tolearn.R;
import com.example.tolearn.databinding.ActivityClassProfileBinding;
import com.example.tolearn.databinding.FragmentHomeBinding;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.ExamAPI;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
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
    com.google.android.material.button.MaterialButton submit;
    com.google.android.material.button.MaterialButton examEditBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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



        homeworkTitleTextview = root.findViewById(R.id.homeworkTextview);
        examTitleTextview = root.findViewById(R.id.examTextview);
        examCountTextview = root.findViewById(R.id.questionCountTextview);
        homeworkDeadlineTextview = root.findViewById(R.id.deadlineTextview);
        submit = root.findViewById(R.id.SubmitBtn);
        examEditBtn = root.findViewById(R.id.exameditBtn);

        homeworktypeList = new ArrayList<>();
        examtypeList = new JsonArray();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInformation",getActivity().MODE_PRIVATE);
        String user_token = sharedPreferences.getString("token","");

        JsonObject jsonObject = new JsonObject();
        SharedPreferences shP2 = getContext().getSharedPreferences("classId", getContext().MODE_PRIVATE);
        String id = shP2.getString("Id", "");
        int classroom_id = Integer.parseInt(id);
        jsonObject.addProperty("classroom",classroom_id);
        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+user_token,jsonObject);
        callBack.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
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
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
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
                            examTitleTextview.setText(jo.get("name").toString());
                            examCountTextview.setText(jo.get("start_time").toString() + "\n" + jo.get("start_time").toString());
                            Log.i("salam","sas222222222222");
                        }
                        catch (Exception exception)
                        {
                            //nothing
                        }
                    }
                }
                else{
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
                Log.i("ERROR",t.getMessage());
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Homework hw = homeworktypeList.get(homeworktypeList.size()-1);
                    Intent goToSubmit = new Intent(getContext() , DetailSubmit.class);
                    goToSubmit.putExtra("homework_title",hw.getTitle());
                    goToSubmit.putExtra("homework_token",hw.getHomework_token());
                    goToSubmit.putExtra("homework_deadline",hw.getDeadline());
                    goToSubmit.putExtra("homework_id",(hw.getId()));
                    startActivity(goToSubmit);
                }
                catch (Exception ex)
                {
                    //nothing
                }
            }
        });

        examEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ExamNew currentExam = new ExamNew(examtypeList.get(examtypeList.size()-1).getAsJsonObject());
                    Intent goToEditExam = new Intent(getContext(), ExamUpdate.class);
                    String e_id = currentExam.getId().toString();
                    goToEditExam.putExtra("id",e_id);
                    goToEditExam.putExtra("question_count",String.valueOf(currentExam.getQuestions_count()));
                    goToEditExam.putExtra("name",currentExam.getName());
                    goToEditExam.putExtra("start_time",currentExam.getStartDate());
                    goToEditExam.putExtra("end_time",currentExam.getEndDate());
                    startActivity(goToEditExam);
                }
                catch (Exception exception)
                {
                    //nothing
                }
            }
        });
        return root;

        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}