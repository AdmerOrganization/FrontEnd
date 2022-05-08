package com.example.tolearn.ui.classProfile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tolearn.Adapters.membersAdapter;
import com.example.tolearn.Entity.member;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.UserAPI;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassProfileFragment extends Fragment {
    TextView classroom_title;
    TextView classroom_teacher;
    TextView classroom_desc;
    TextView classroom_category;
    TextView classroom_limit;
    ListView classroom_members;
    membersAdapter membersAdap;
    ClassAPI classAPI;
    List<member> members;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_profile, container, false);
        classroom_title = rootView.findViewById(R.id.classroomTitleTV);
        classroom_teacher = rootView.findViewById(R.id.classroomTeacherTV);
        classroom_desc = rootView.findViewById(R.id.classroomDescTV);
        classroom_category = rootView.findViewById(R.id.classroomCategory);
        classroom_limit = rootView.findViewById(R.id.limitTV);
        classroom_members = rootView.findViewById(R.id.membersList);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();


        Retrofit classMembers = new Retrofit.Builder()
                .baseUrl(ClassAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI =classMembers.create(ClassAPI.class);

        SharedPreferences shP = getActivity().getSharedPreferences("userInformation", getActivity().MODE_PRIVATE);
        String token = shP.getString("token", "");

        SharedPreferences shp2 = getActivity().getSharedPreferences("classId",getActivity().MODE_PRIVATE);
        String Id = shp2.getString("Id","");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", Id);

        Call<myClass> callInfo = classAPI.classInfo("token "+ token,jsonObject );
        callInfo.enqueue(new Callback<myClass>() {
            @Override
            public void onResponse(Call<myClass> call, Response<myClass> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getActivity(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
                else{
                    myClass currentClass = response.body();
                    classroom_title.setText(currentClass.getTitle());
                    classroom_teacher.setText(currentClass.getTeacher_name());
                    classroom_desc.setText(currentClass.getDescription());
                    classroom_category.setText(currentClass.getCategory());
                    classroom_limit.setText(String.valueOf(currentClass.getLimit()));

                    Call<List<member>> callBack = classAPI.classMembers("token "+ token,jsonObject );
                    callBack.enqueue(new Callback<List<member>>() {
                        @Override
                        public void onResponse(Call<List<member>> call, Response<List<member>> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(getActivity(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                members = response.body();
                                membersAdap = new membersAdapter(getActivity(),members);
                                classroom_members.setAdapter(membersAdap);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<member>> call, Throwable t) {
                            Toast.makeText(getActivity(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<myClass> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
}
