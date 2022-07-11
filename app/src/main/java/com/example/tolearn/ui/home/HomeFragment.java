package com.example.tolearn.ui.home;

import android.content.Intent;
import com.example.tolearn.webService.chatAPI;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tolearn.Adapters.examAdapter;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.CustomeConfirmAlertDialog;
import com.example.tolearn.AlertDialogs.HomeworkEditDialog;
import com.example.tolearn.ChatActivity;
import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.DetailSubmit;
import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.ExamHomework;
import com.example.tolearn.Entity.ExamNew;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.ExamProfile;
import com.example.tolearn.ExamStart;
import com.example.tolearn.ExamUpdate;
import com.example.tolearn.Homework_results;
import com.example.tolearn.R;
import com.example.tolearn.StudentsExamResults;
import com.example.tolearn.databinding.ActivityClassProfileBinding;
import com.example.tolearn.databinding.FragmentHomeBinding;
import com.example.tolearn.ui.exam.ExamViewModel;
import com.example.tolearn.ui.homework.HomeworkFragment;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.ExamAPI;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    public String title, teacher, category, user_token, user_access;
    public int class_id;
    public List<Homework> homeworktypeList;
    public JsonArray examtypeList;
    public Homework latestHomework;
    public ExamNew latestExam;
    HomeworkAPI homeworkAPI;
    ExamAPI examAPI;
    ClassAPI classAPI;
    TextView homeworkTitleTextview;
    TextView examTitleTextview;
    TextView examCountTextview;
    TextView homeworkDeadlineTextview;
    chatAPI chatAPI;

    com.google.android.material.button.MaterialButton submit , submitExam , editExam , resultExam;
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
        Retrofit Classes = new Retrofit.Builder()
                .baseUrl(ClassAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI = Classes.create(ClassAPI.class);


        //  mFrameLayout = findViewById(R.id.shimmerLayout);

        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("classId",getContext().MODE_PRIVATE);
        String access = sharedPreferences2.getString("user_access","");
        homeworkTitleTextview = root.findViewById(R.id.homeworkTextview);
        examTitleTextview = root.findViewById(R.id.examTextview);
        examCountTextview = root.findViewById(R.id.questionCountTextview);
        homeworkDeadlineTextview = root.findViewById(R.id.deadlineTextview);
        submit = root.findViewById(R.id.SubmitBtn);
        examEditBtn = root.findViewById(R.id.exameditBtn);
        submitExam = root.findViewById(R.id.examSubmitBtn);
        resultExam = root.findViewById(R.id.resultBtn);
        Button editBtn = root.findViewById(R.id.editBtn);
        Button resultsBtn = root.findViewById(R.id.resultHomeworkBtn);
        if(access.equals("student"))
        {
            editBtn.setClickable(false);
            editBtn.setVisibility(View.INVISIBLE);
            resultsBtn.setClickable(false);
            resultsBtn.setVisibility(View.INVISIBLE);
        }
        else{
            resultsBtn.setClickable(false);
            resultsBtn.setVisibility(View.INVISIBLE);
            submit.setText("Results");
        }
        if(access.equals("student"))
        {
            examEditBtn.setClickable(false);
            examEditBtn.setVisibility(View.INVISIBLE);
            resultExam.setClickable(false);
            resultExam.setVisibility(View.INVISIBLE);
        }
        else{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar cal = Calendar.getInstance();
            String currentDateTime = (dateFormat.format(cal.getTime()));

            submitExam.setText("results");
            resultExam.setClickable(false);
            resultExam.setVisibility(View.INVISIBLE);
        }


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInformation", getActivity().MODE_PRIVATE);
        String user_token = sharedPreferences.getString("token", "");

        JsonObject jsonObject = new JsonObject();
        SharedPreferences shP2 = getContext().getSharedPreferences("classId", getContext().MODE_PRIVATE);
        String id = shP2.getString("Id", "");
        int classroom_id = Integer.parseInt(id);
        Call<ExamHomework> callBacklatest = classAPI.latestBoth("token " + user_token, classroom_id);
        callBacklatest.enqueue(new Callback<ExamHomework>() {
            @Override
            public void onResponse(Call<ExamHomework> call, Response<ExamHomework> response) {
                if (response.isSuccessful()) {
                    try {
                        ExamHomework Response = response.body();
                        latestExam = Response.getExam();
                        Log.i("EXAM IDDDDDDDDDDD", latestExam.getId().toString());
                        latestHomework = Response.getHomework();
                        homeworkTitleTextview.setText(latestHomework.getTitle());
                        homeworkDeadlineTextview.setText(latestHomework.getDeadline());
//                    JsonObject jo = Response.get(Response.size()-1).getAsJsonObject();
//                    String name = jo.get("name").toString();
//                    name = name.replace("\"","");
                        examTitleTextview.setText(latestExam.getName());
                        String start = latestExam.getStartDate().replace(" ","");
                        String end = latestExam.getEndDate().replace("","");

                        start = start.replace("T"," ");
                        end = end.replace("T"," ");
                        start = start.replace(":00Z","\n");
                        end = end.replace(":00Z","");
                        examCountTextview.setText(start +"\n" + end);
                        Log.i("RESPONSE", response.message());

//                                Homework hw = latestHomework;
//                                Intent goToSubmit = new Intent(getContext(), DetailSubmit.class);
//                                goToSubmit.putExtra("homework_title", hw.getTitle());
//                                goToSubmit.putExtra("homework_token", hw.getHomework_token());
//                                goToSubmit.putExtra("homework_deadline", hw.getDeadline());
//                                goToSubmit.putExtra("homework_id", (hw.getId()));
//                                startActivity(goToSubmit);
//                                FragmentManager fragmentManager = getFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                HomeworkFragment NAME = new HomeworkFragment();
//                                fragmentTransaction.replace(R.id.fragment_container, NAME);
//                                fragmentTransaction.commit();
                        submit.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          if(access.equals("student")){
                                                              Intent gotoSubmit = new Intent(getContext(), com.example.tolearn.DetailSubmit.class);
                                                              gotoSubmit.putExtra("homework_title",latestHomework.getTitle());
                                                              gotoSubmit.putExtra("homework_token",latestHomework.getHomework_token());
                                                              gotoSubmit.putExtra("homework_deadline",latestHomework.getDeadline());
                                                              gotoSubmit.putExtra("homework_id",latestHomework.getId());
                                                              getContext().startActivity(gotoSubmit);
                                                          }
                                                          else{
                                                              Intent goToResults = new Intent(getContext(), Homework_results.class);
                                                              goToResults.putExtra("title",latestHomework.getTitle());
                                                              goToResults.putExtra("deadline",latestHomework.getDeadline());
                                                              goToResults.putExtra("id",String.valueOf(latestHomework.getId()));
                                                              getContext().startActivity(goToResults);
                                                          }
                                                      }
                                                  }
                        );
                        editBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent gotoEdit = new Intent(getContext(), HomeworkEditDialog.class);
                                gotoEdit.putExtra("homework_token",latestHomework.getHomework_token());
                                getContext().startActivity(gotoEdit);

                            }
                        });
                        resultsBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent goToResults = new Intent(getContext(), Homework_results.class);
                                goToResults.putExtra("title",latestHomework.getTitle());
                                goToResults.putExtra("deadline",latestHomework.getDeadline());
                                goToResults.putExtra("id",String.valueOf(latestHomework.getId()));
                                getContext().startActivity(goToResults);
                            }
                        });
                        examEditBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent goToExamUpdate = new Intent(getContext(), ExamUpdate.class);
                                goToExamUpdate.putExtra("id",id);
                                goToExamUpdate.putExtra("question_count",String.valueOf(latestExam.getQuestions_count()));
                                goToExamUpdate.putExtra("name",latestExam.getName());
                                goToExamUpdate.putExtra("start_time",latestExam.getStartDate());
                                goToExamUpdate.putExtra("end_time",latestExam.getEndDate());
                                getContext().startActivity(goToExamUpdate);
                            }
                        });

                        submitExam.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(access.equals("student")) {
                                    String StartDate = latestExam.getStartDate();

                                    CustomeConfirmAlertDialog confirmAlertDialog = new CustomeConfirmAlertDialog(getContext(),"Exam","do you want to start this exam ?");
                                    confirmAlertDialog.image.setImageResource(R.drawable.question);
                                    confirmAlertDialog.No.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            confirmAlertDialog.alertDialog.dismiss();
                                        }
                                    });
                                    confirmAlertDialog.Yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(ExamTimeChecker(latestExam.getStartDate(),latestExam.getEndDate()))
                                            {
                                                Intent goToExam = new Intent(getContext(), ExamStart.class);
                                                goToExam.putExtra("examId",id);
                                                goToExam.putExtra("startDate",latestExam.getStartDate());
                                                goToExam.putExtra("endDate",latestExam.getEndDate());
                                                getContext().startActivity(goToExam);
                                                confirmAlertDialog.alertDialog.dismiss();
                                            }
                                            else{
                                                CustomeAlertDialog alertDialog = new CustomeAlertDialog(getContext(),"Error","Exam has not been started yet or has been finished!");
                                                alertDialog.imageView.setImageResource(R.drawable.error);
                                                alertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        confirmAlertDialog.alertDialog.dismiss();
                                                        alertDialog.alertDialog.dismiss();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                                else{
                                    if(ResultTimeChecker(latestExam.getEndDate()))
                                    {
                                        Intent goToResultActivity = new Intent(getContext() , StudentsExamResults.class);
                                        goToResultActivity.putExtra("exam_id",Integer.toString(latestExam.getId()));
                                        getContext().startActivity(goToResultActivity);
                                    }
                                    else{
                                        Toast.makeText(getContext(), "the exam has not ended yet", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });


                        examEditBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent goToExamUpdate = new Intent(getContext(), ExamUpdate.class);
                                goToExamUpdate.putExtra("id",latestExam.getId().toString());
                                goToExamUpdate.putExtra("question_count",String.valueOf(latestExam.getQuestions_count()));
                                goToExamUpdate.putExtra("name",latestExam.getName());
                                goToExamUpdate.putExtra("start_time",latestExam.getStartDate());
                                goToExamUpdate.putExtra("end_time",latestExam.getEndDate());
                                getContext().startActivity(goToExamUpdate);
                            }
                        });
//                    submitExam.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(access.equals("student")) {
//                                String StartDate = latestExam.getStartDate();
//
//                                CustomeConfirmAlertDialog confirmAlertDialog = new CustomeConfirmAlertDialog(getContext(),"Exam","do you want to start this exam ?");
//                                confirmAlertDialog.image.setImageResource(R.drawable.question);
//                                confirmAlertDialog.No.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        confirmAlertDialog.alertDialog.dismiss();
//                                    }
//                                });
//                                confirmAlertDialog.Yes.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        if(ExamTimeChecker(currentExam.getStartDate(),currentExam.getEndDate()))
//                                        {
//                                            Intent goToExam = new Intent(context, ExamStart.class);
//                                            goToExam.putExtra("examId",id);
//                                            goToExam.putExtra("startDate",currentExam.getStartDate());
//                                            goToExam.putExtra("endDate",currentExam.getEndDate());
//                                            context.startActivity(goToExam);
//                                            confirmAlertDialog.alertDialog.dismiss();
//                                        }
//                                        else{
//                                            CustomeAlertDialog alertDialog = new CustomeAlertDialog(context,"Error","Exam has not been started yet or has been finished!");
//                                            alertDialog.imageView.setImageResource(R.drawable.error);
//                                            alertDialog.btnOk.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View view) {
//                                                    confirmAlertDialog.alertDialog.dismiss();
//                                                    alertDialog.alertDialog.dismiss();
//                                                }
//                                            });
//                                        }
//                                    }
//                                });
//                            }
//                            else{
//                                if(ResultTimeChecker(currentExam.getEndDate()))
//                                {
//                                    Intent goToResultActivity = new Intent(context , StudentsExamResults.class);
//                                    goToResultActivity.putExtra("exam_id",Integer.toString(currentExam.getId()));
//                                    context.startActivity(goToResultActivity);
//                                }
//                                else{
//                                    Toast.makeText(context, "the exam has not ended yet", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        }
//                    });
//                    if(Response.size()>0)
//                    {
//                        Log.i("salam","sas222222222222");
//                        try {
//                            JsonObject jo = Response.get(Response.size()-1).getAsJsonObject();
//                            String name = jo.get("name").toString();
//                            name = name.replace("\"","");
//                            examTitleTextview.setText(name);
//                            String start = jo.get("start_time").toString();
//                            start = start.replace("T"," ");
//                            String end = jo.get("start_time").toString();
//                            end = end.replace("T"," ");
//                            start = start.replace(":00Z","\n");
//                            end = end.replace(":00Z","");
//                            start = start.replace("\"","");
//                            end = end.replace("\"","");
//
//                            examCountTextview.setText(start  + end);
//                        }
//                        catch (Exception exception)
//                        {
//                            //nothing
//                        }
//                    }
                    }
                    catch (Exception ex)
                    {
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
                                    }
                                } else {
                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ExamHomework> call, Throwable t) {
                                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(), "error", "there is a problem with your internet connection");
                                Log.i("ERROR33333", t.getMessage());
                            }
                        });
//        jsonObject.addProperty("classroom",classroom_id);
//        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+user_token,jsonObject);
//        callBack.enqueue(new Callback<List<Homework>>() {
//            @Override
//            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
//                if(!response.isSuccessful())
//                {
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    homeworktypeList = response.body();
//                    Log.i("salam","sas");
//                    if(homeworktypeList.size()>0)
//                    {
//                        try {
//                            homeworkTitleTextview.setText(homeworktypeList.get(homeworktypeList.size()-1).getTitle());
//                            homeworkDeadlineTextview.setText(homeworktypeList.get(homeworktypeList.size()-1).getDeadline());
//                        }
//                        catch (Exception exception)
//                        {
//                            //nothing
//                        }
//                    }
//                    //     mFrameLayout.startShimmer();
//                    //
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Homework>> call, Throwable t) {
//                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
//                Log.i("ERROR2",t.getMessage());
//            }
//        });
//
//
//
//        JsonObject classIdJson = new JsonObject();
//        classIdJson.addProperty("classroom",classroom_id);
//        Call<JsonArray> callBackNew = examAPI.GetAllExamsJsonForClass("token "+user_token,classIdJson);
//        callBackNew.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                if(response.isSuccessful())
//                {
//                    JsonArray Response = response.body();
//                    examtypeList = Response;
//                    if(Response.size()>0)
//                    {
//                        Log.i("salam","sas222222222222");
//                        try {
//                            JsonObject jo = Response.get(Response.size()-1).getAsJsonObject();
//                            String name = jo.get("name").toString();
//                            name = name.replace("\"","");
//                            examTitleTextview.setText(name);
//                            String start = jo.get("start_time").toString();
//                            start = start.replace("T"," ");
//                            String end = jo.get("start_time").toString();
//                            end = end.replace("T"," ");
//                            start = start.replace(":00Z","\n");
//                            end = end.replace(":00Z","");
//                            start = start.replace("\"","");
//                            end = end.replace("\"","");
//
//                            examCountTextview.setText(start  + end);
//                            Log.i("salam","sas222222222222");
//                        }
//                        catch (Exception exception)
//                        {
//                            //nothing
//                        }
//                    }
//                }
//                else{
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
//                Log.i("ERROR",t.getMessage());
//            }
//        });



        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
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
        Retrofit Classes = new Retrofit.Builder()
                .baseUrl(ClassAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI = Classes.create(ClassAPI.class);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInformation", getActivity().MODE_PRIVATE);
        String user_token = sharedPreferences.getString("token", "");

        JsonObject jsonObject = new JsonObject();
        SharedPreferences shP2 = getContext().getSharedPreferences("classId", getContext().MODE_PRIVATE);
        String id = shP2.getString("Id", "");
        int classroom_id = Integer.parseInt(id);
        Call<ExamHomework> callBacklatest = classAPI.latestBoth("token " + user_token, classroom_id);
        callBacklatest.enqueue(new Callback<ExamHomework>() {
            @Override
            public void onResponse(Call<ExamHomework> call, Response<ExamHomework> response) {
                if (response.isSuccessful()) {
                    ExamHomework Response = response.body();
                    latestExam = Response.getExam();
                    latestHomework = Response.getHomework();
                    Log.i("RESPONSEEEEEEEEEEEEEEEE", response.message());
//                    if(Response.size()>0)
//                    {
//                        Log.i("salam","sas222222222222");
//                        try {
//                            JsonObject jo = Response.get(Response.size()-1).getAsJsonObject();
//                            String name = jo.get("name").toString();
//                            name = name.replace("\"","");
//                            examTitleTextview.setText(name);
//                            String start = jo.get("start_time").toString();
//                            start = start.replace("T"," ");
//                            String end = jo.get("start_time").toString();
//                            end = end.replace("T"," ");
//                            start = start.replace(":00Z","\n");
//                            end = end.replace(":00Z","");
//                            start = start.replace("\"","");
//                            end = end.replace("\"","");
//
//                            examCountTextview.setText(start  + end);
//                        }
//                        catch (Exception exception)
//                        {
//                            //nothing
//                        }
//                    }
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ExamHomework> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(), "error", "there is a problem with your internet connection");
                Log.i("ERROR33333", t.getMessage());
            }
        });
//        jsonObject.addProperty("classroom",classroom_id);
//        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+user_token,jsonObject);
//        callBack.enqueue(new Callback<List<Homework>>() {
//            @Override
//            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
//                if(!response.isSuccessful())
//                {
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    homeworktypeList = response.body();
//                    Log.i("salam","sas");
//                    if(homeworktypeList.size()>0)
//                    {
//                        try {
//                            homeworkTitleTextview.setText(homeworktypeList.get(homeworktypeList.size()-1).getTitle());
//                            homeworkDeadlineTextview.setText(homeworktypeList.get(homeworktypeList.size()-1).getDeadline());
//                        }
//                        catch (Exception exception)
//                        {
//                            //nothing
//                        }
//                    }
//                    //     mFrameLayout.startShimmer();
//                    //
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Homework>> call, Throwable t) {
//                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
//                Log.i("ERROR2",t.getMessage());
//            }
//        });
//
//
//
//        JsonObject classIdJson = new JsonObject();
//        classIdJson.addProperty("classroom",classroom_id);
//        Call<JsonArray> callBackNew = examAPI.GetAllExamsJsonForClass("token "+user_token,classIdJson);
//        callBackNew.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                if(response.isSuccessful())
//                {
//                    JsonArray Response = response.body();
//                    examtypeList = Response;
//                    if(Response.size()>0)
//                    {
//                        Log.i("salam","sas222222222222");
//                        try {
//                            JsonObject jo = Response.get(Response.size()-1).getAsJsonObject();
//                            String name = jo.get("name").toString();
//                            name = name.replace("\"","");
//                            examTitleTextview.setText(name);
//                            String start = jo.get("start_time").toString();
//                            start = start.replace("T"," ");
//                            String end = jo.get("start_time").toString();
//                            end = end.replace("T"," ");
//                            start = start.replace(":00Z","\n");
//                            end = end.replace(":00Z","");
//                            start = start.replace("\"","");
//                            end = end.replace("\"","");
//
//                            examCountTextview.setText(start  + end);
//                            Log.i("salam","sas222222222222");
//                        }
//                        catch (Exception exception)
//                        {
//                            //nothing
//                        }
//                    }
//                }
//                else{
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
//                Log.i("ERROR",t.getMessage());
//            }
//        });

    }
    public boolean ResultTimeChecker (String endDate)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTime = (dateFormat.format(cal.getTime()));

        String [] dateTime = currentDateTime.split(" ");
        String [] dateInfo = dateTime[0].split("/");
        String [] timeInfo = dateTime[1].split(":");

        int currentYear = Integer.parseInt(dateInfo[0]);
        int currentMonth = Integer.parseInt(dateInfo[1]);
        int currentDay = Integer.parseInt(dateInfo[2]);
        int currentHour = Integer.parseInt(timeInfo[0]);
        int currentMinute = Integer.parseInt(timeInfo[1]);

        String currentMoment = String.valueOf(currentHour)+String.valueOf(currentMinute);
        int currentMomentInt = Integer.parseInt(currentMoment);

        String [] endDateTimeInfo = endDate.split("T");
        String [] endDateInfo = endDateTimeInfo[0].split("-");
        int endYear = Integer.parseInt(endDateInfo[0]);
        int endMonth = Integer.parseInt(endDateInfo[1]);
        int endDay = Integer.parseInt(endDateInfo[2]);

        String [] endTimeInfo = endDateTimeInfo[1].split(":");
        int endHour = Integer.parseInt(endTimeInfo[0]);
        int endMinute = Integer.parseInt(endTimeInfo[1]);

        String endMoment = String.valueOf(endHour)+String.valueOf(endMinute);
        int endMomentInt = Integer.parseInt(endMoment);

        if(currentYear > endYear)
        {
            Log.i("TIME",currentDateTime +  "    " + endDate);
            return true;
        }
        else if(currentYear == endYear)
        {
            if(currentMonth > endMonth)
            {
                Log.i("TIME",currentDateTime +  "    " + endDate);
                return true;
            }
            else if ( currentMonth == endMonth)
            {
                if (currentDay > endDay)
                {
                    Log.i("TIME",currentDateTime +  "    " + endDate);
                    return true;
                }
                else if ( currentDay == endDay)
                {
                    if( currentMomentInt > endMomentInt)
                    {
                        Log.i("TIME",currentDateTime +  "    " + endDate);
                        return true;
                    }
                    else
                    {
                        Log.i("TIME",currentDateTime +  "    " + endDate);
                        return false;
                    }
                }
                else{
                    Log.i("TIME",currentDateTime +  "    " + endDate);
                    return false;
                }
            }
            else{
                Log.i("TIME",currentDateTime +  "    " + endDate);
                return false;
            }
        }
        else
        {
            Log.i("TIME",currentDateTime +  "    " + endDate);
            return false;
        }
    }

    public boolean ExamTimeChecker (String startDate , String endDate)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTime = (dateFormat.format(cal.getTime()));

        String [] dateTime = currentDateTime.split(" ");
        String [] dateInfo = dateTime[0].split("/");
        String [] timeInfo = dateTime[1].split(":");

        int currentYear = Integer.parseInt(dateInfo[0]);
        int currentMonth = Integer.parseInt(dateInfo[1]);
        int currentDay = Integer.parseInt(dateInfo[2]);
        int currentHour = Integer.parseInt(timeInfo[0]);
        int currentMinute = Integer.parseInt(timeInfo[1]);

        String [] startDateTimeInfo = startDate.split("T");
        String [] startDateInfo = startDateTimeInfo[0].split("-");
        int startYear = Integer.parseInt(startDateInfo[0]);
        int startMonth = Integer.parseInt(startDateInfo[1]);
        int startDay = Integer.parseInt(startDateInfo[2]);

        String [] StartTimeInfo = startDateTimeInfo[1].split(":");
        int startHour = Integer.parseInt(StartTimeInfo[0]);
        int startMinute = Integer.parseInt(StartTimeInfo[1]);


        String [] endDateTimeInfo = endDate.split("T");
        String [] endDateInfo = endDateTimeInfo[0].split("-");
        int endYear = Integer.parseInt(endDateInfo[0]);
        int endMonth = Integer.parseInt(endDateInfo[1]);
        int endDay = Integer.parseInt(endDateInfo[2]);

        String [] endTimeInfo = endDateTimeInfo[1].split(":");
        int endHour = Integer.parseInt(endTimeInfo[0]);
        int endMinute = Integer.parseInt(endTimeInfo[1]);

        Log.i("DateTimeExam", String.valueOf(currentYear));
        Log.i("DateTimeExam", String.valueOf(currentMonth));
        Log.i("DateTimeExam", String.valueOf(currentDay));
        Log.i("DateTimeExam", String.valueOf(currentHour));
        Log.i("DateTimeExam", String.valueOf(currentMinute));

        Log.i("DateTimeExam", String.valueOf(startYear));
        Log.i("DateTimeExam", String.valueOf(startMonth));
        Log.i("DateTimeExam", String.valueOf(startDay));
        Log.i("DateTimeExam", String.valueOf(startHour));
        Log.i("DateTimeExam", String.valueOf(startMinute));

        Log.i("DateTimeExam", String.valueOf(endYear));
        Log.i("DateTimeExam", String.valueOf(endMonth));
        Log.i("DateTimeExam", String.valueOf(endDay));
        Log.i("DateTimeExam", String.valueOf(endHour));
        Log.i("DateTimeExam", String.valueOf(endMinute));


        String currentMoment = String.valueOf(currentHour)+String.valueOf(currentMinute);
        int currentMomentInt = Integer.parseInt(currentMoment);

        String startMoment = String.valueOf(startHour)+String.valueOf(startMinute);
        int startMomentInt = Integer.parseInt(startMoment);

        String endMoment = String.valueOf(endHour)+String.valueOf(endMinute);
        int endMomentInt = Integer.parseInt(endMoment);

        if(currentYear == startYear && currentMonth == startMonth && currentMonth == startMonth && currentDay == startDay )
        {
            if(currentMomentInt >= startMomentInt && currentMomentInt <= endMomentInt)
            {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean isFirstDateBeforeSecondDate (String firstDate , String secondDate)
    {
        Log.i("First",firstDate);
        Log.i("Second",secondDate);
        String [] firstDateTimeInfo = firstDate.split(" ");
        String [] firstDateInfo = firstDateTimeInfo[0].split("-");
        int firstYear = Integer.parseInt(firstDateInfo[0]);
        int firstMonth = Integer.parseInt(firstDateInfo[1]);
        int firstDay = Integer.parseInt(firstDateInfo[2]);

        String [] firstTimeInfo = firstDateTimeInfo[1].split(":");
        int firstHour = Integer.parseInt(firstTimeInfo[0]);
        firstTimeInfo[1] = firstTimeInfo[1].replace(" ","");
        int firstMinute = Integer.parseInt(firstTimeInfo[1]);

        String [] secondDateTimeInfo = secondDate.split(" ");
        String [] secondDateInfo = secondDateTimeInfo[0].split("-");
        int secondYear = Integer.parseInt(secondDateInfo[0]);
        int secondMonth = Integer.parseInt(secondDateInfo[1]);
        int secondDay = Integer.parseInt(secondDateInfo[2]);

        String [] secondTimeInfo = secondDateTimeInfo[1].split(":");
        int secondHour = Integer.parseInt(secondTimeInfo[0]);
        int secondMinute = Integer.parseInt(secondTimeInfo[1]);

        if(secondYear >  firstYear)
        {
            return true;
        }
        else if (secondYear == firstYear)
        {
            if ( secondMonth > firstMonth)
            {
                return true;
            }
            else if(secondMonth == firstMonth)
            {
                if(secondDay > firstDay)
                {
                    return true;
                }
                else if(secondDay == firstDay)
                {
                    String firstMoment = String.valueOf(firstHour)+String.valueOf(firstMinute);
                    int firstMomentInt = Integer.parseInt(firstMoment);
                    String secondMoment = String.valueOf(secondHour)+String.valueOf(secondMinute);
                    int secondMomentInt = Integer.parseInt(secondMoment);

                    if(secondMomentInt >= firstMomentInt)
                    {
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else {
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}