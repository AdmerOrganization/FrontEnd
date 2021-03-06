package com.example.tolearn.ui.homework;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tolearn.Adapters.homeworkAdapter;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.HomeworkCreationDialog;
import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.R;
import com.example.tolearn.databinding.FragmentHomeworkBinding;
import com.example.tolearn.webService.HomeworkAPI;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeworkFragment extends Fragment {

    private HomeWorkViewModel homeWorkViewModel;
    private FragmentHomeworkBinding binding;
    private ConstraintLayout constraintLayout ;
    homeworkAdapter myadap;
    HomeworkAPI  homeworkAPI;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeWorkViewModel =
                new ViewModelProvider(this).get(HomeWorkViewModel.class);

        binding = FragmentHomeworkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        while(((ClassProfileActivity)getActivity()).homeworktypeList == null){
//
//        }
        Retrofit Homeworks = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI = Homeworks.create(HomeworkAPI.class);
        SharedPreferences shP = getContext().getSharedPreferences("classId", getContext().MODE_PRIVATE);
        String id = shP.getString("Id", "");
        int classroom_id = Integer.parseInt(id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("classroom",classroom_id);

        SharedPreferences shP2 = getContext().getSharedPreferences("userInformation", getContext().MODE_PRIVATE);
        String token = shP2.getString("token", "");

        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+token,jsonObject);
        callBack.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                ((ClassProfileActivity)getActivity()).homeworktypeList = response.body();
                myadap.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Homework>> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
                Log.i("ERROR2",t.getMessage());
            }
        });

        ListView homeworkListview = root.findViewById(R.id.homeworksList);
        myadap = new homeworkAdapter(this.getActivity(),((ClassProfileActivity)getActivity()).homeworktypeList,"");
        homeworkListview.setAdapter(myadap);

        com.google.android.material.floatingactionbutton.FloatingActionButton addHomework = root.findViewById(R.id.fab);
        addHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shp2 = getActivity().getSharedPreferences("classId",getActivity().MODE_PRIVATE);
                String access = shp2.getString("user_access","");
                String class_id = shp2.getString("class_id","");

                if(access.equals("teacher"))
                {
                    Intent createHomework = new Intent(getActivity(), HomeworkCreationDialog.class);
                    createHomework.putExtra("id",class_id);
                    startActivity(createHomework);
                }
                else{
                    Toast.makeText(getActivity(), "You don't have the right access to create a homework for this class", Toast.LENGTH_LONG).show();
                }
            }
        });
//        final TextView textView = binding.textHomework;
//        homeWorkViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
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
        Retrofit Homeworks = new Retrofit.Builder()
                .baseUrl(HomeworkAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homeworkAPI = Homeworks.create(HomeworkAPI.class);
        SharedPreferences shP = getContext().getSharedPreferences("classId", getContext().MODE_PRIVATE);
        String id = shP.getString("Id", "");
        int classroom_id = Integer.parseInt(id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("classroom",classroom_id);

        SharedPreferences shP2 = getContext().getSharedPreferences("userInformation", getContext().MODE_PRIVATE);
        String token = shP2.getString("token", "");

        Call<List<Homework>> callBack = homeworkAPI.GetAllHomework("token "+token,jsonObject);
        callBack.enqueue(new Callback<List<Homework>>() {
            @Override
            public void onResponse(Call<List<Homework>> call, Response<List<Homework>> response) {
                ((ClassProfileActivity)getActivity()).homeworktypeList = response.body();
                myadap.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Homework>> call, Throwable t) {
                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(getContext(),"error","there is a problem with your internet connection");
                Log.i("ERROR2",t.getMessage());
            }
        });
    }
}