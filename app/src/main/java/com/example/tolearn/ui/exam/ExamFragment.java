package com.example.tolearn.ui.exam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tolearn.Adapters.examAdapter;
import com.example.tolearn.Adapters.homeworkAdapter;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.Entity.Exam;
import com.example.tolearn.Entity.ExamNew;
import com.example.tolearn.ExamProfile;
import com.example.tolearn.R;
import com.example.tolearn.databinding.FragmentExamBinding;
import com.example.tolearn.webService.ExamAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamFragment extends Fragment {

    private ExamViewModel examViewModel;
    private FragmentExamBinding binding;
    public JsonArray examtypeList;
    ExamAPI examAPI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        examViewModel =
                new ViewModelProvider(this).get(ExamViewModel.class);

        binding = FragmentExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Retrofit Exams = new Retrofit.Builder()
                .baseUrl(ExamAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        examAPI = Exams.create(ExamAPI.class);

        ListView examsListview = root.findViewById(R.id.examsList);
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("userInformation",root.getContext().MODE_PRIVATE);
        String user_token = sharedPreferences.getString("token","");
        SharedPreferences shP2 = getContext().getSharedPreferences("classId", getContext().MODE_PRIVATE);
        String id = shP2.getString("Id", "");
        String role = shP2.getString("user_access","");
        int classroom_id = Integer.parseInt(id);
        JsonObject classroomID = new JsonObject();
        classroomID.addProperty("classroom",classroom_id );
        Call<JsonArray> callBackNew = examAPI.GetAllExamsJsonForClass("token "+user_token,classroomID);
        callBackNew.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                examtypeList = response.body();
                Log.i("salam","sas222222222222");
                examAdapter myadap = new examAdapter(root.getContext(),examtypeList,"");
                examsListview.setAdapter(myadap);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(root.getContext(),"error","there is a problem with your internet connection");
                Log.i("ERROR",t.getMessage());
            }
        });

        examAdapter myadap = new examAdapter(root.getContext(),((ClassProfileActivity)getActivity()).examtypeList,"");
        examsListview.setAdapter(myadap);
        final com.google.android.material.floatingactionbutton.FloatingActionButton addExamBtn = binding.addExam;
        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("teacher"))
                {
                    Intent goToExamCreation = new Intent(getActivity() , ExamProfile.class);
                    startActivity(goToExamCreation);
                }
                else{
                    Toast.makeText(getContext(), "you do not have right permission to create exams", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume() {
        super.onResume();
        examViewModel =
                new ViewModelProvider(this).get(ExamViewModel.class);

        View root = binding.getRoot();
        Retrofit Exams = new Retrofit.Builder()
                .baseUrl(ExamAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        examAPI = Exams.create(ExamAPI.class);

        ListView examsListview = root.findViewById(R.id.examsList);

        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("userInformation",root.getContext().MODE_PRIVATE);
        String user_token = sharedPreferences.getString("token","");
        SharedPreferences shP2 = getContext().getSharedPreferences("classId", getContext().MODE_PRIVATE);
        String id = shP2.getString("Id", "");
        String role = shP2.getString("user_access","");
        int classroom_id = Integer.parseInt(id);
        JsonObject classroomID = new JsonObject();
        classroomID.addProperty("classroom",classroom_id );
        Call<JsonArray> callBackNew = examAPI.GetAllExamsJsonForClass("token "+user_token,classroomID);
        callBackNew.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                examtypeList = response.body();
                Log.i("salam","sas222222222222");
                examAdapter myadap = new examAdapter(root.getContext(),examtypeList,"");
                examsListview.setAdapter(myadap);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(root.getContext(),"error","there is a problem with your internet connection");
                Log.i("ERROR",t.getMessage());
            }
        });

        examAdapter myadap = new examAdapter(root.getContext(),((ClassProfileActivity)getActivity()).examtypeList,"");
        examsListview.setAdapter(myadap);
        final com.google.android.material.floatingactionbutton.FloatingActionButton addExamBtn = binding.addExam;
        addExamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals("teacher"))
                {
                    Intent goToExamCreation = new Intent(getActivity() , ExamProfile.class);
                    startActivity(goToExamCreation);
                }
                else{
                    Toast.makeText(getContext(), "you do not have right permission to create exams", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}