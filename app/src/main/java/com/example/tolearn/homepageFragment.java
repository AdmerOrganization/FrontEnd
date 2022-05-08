package com.example.tolearn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tolearn.Adapters.CreatedClassesAdapterMainAct;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.webService.ClassAPI;
import com.example.tolearn.webService.UserAPI;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class homepageFragment extends Fragment {

    RecyclerView createdClassesList;
    RecyclerView joinedClassesList;
    List<myClass> myCreatedClasses;
    List<myClass> myjoinedClasses;
    CreatedClassesAdapterMainAct createdClassAdapter;
    CreatedClassesAdapterMainAct joinedClassAdapter;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;
    UserAPI userAPI;
    String userToken;
    ClassAPI classAPI;
    TextView joinedClassesTV;
    TextView createdClassesTV;
    ShimmerFrameLayout mFrameLayout;
    LinearLayoutManager HorizontalLayout2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_fragment, container, false);

        mFrameLayout = rootView.findViewById(R.id.shimmerLayout);
        joinedClassesTV = rootView.findViewById(R.id.joinedClassesTV);
        createdClassesTV = rootView.findViewById(R.id.createdClassesTV);
        joinedClassesList = rootView.findViewById(R.id.joinedClassesScroll);
        createdClassesList = rootView.findViewById(R.id.createdClassList);
//        RecyclerViewLayoutManager
//                = new LinearLayoutManager(
//                getApplicationContext());
//        createdClassesList.setLayoutManager(RecyclerViewLayoutManager);

        HorizontalLayout
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        HorizontalLayout2
                = new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);
        createdClassesList.setLayoutManager(HorizontalLayout);
        joinedClassesList.setLayoutManager(HorizontalLayout2);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInformation",getActivity().MODE_PRIVATE);
        userToken = sharedPreferences.getString("token","");

        Retrofit myClasses = new Retrofit.Builder()
                .baseUrl(ClassAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI = myClasses.create(ClassAPI.class);

        Call<List<myClass>> callBack = classAPI.GetCreatedClasses("token "+userToken);
        callBack.enqueue(new Callback<List<myClass>>() {
            @Override
            public void onResponse(Call<List<myClass>> call, Response<List<myClass>> response) {
                if(!response.isSuccessful())
                {
                    CustomeAlertDialog myClass = new CustomeAlertDialog(getActivity(),"Response Error","There is a problem with your internet connection");

                }
                else{
                    int responseCode = response.code();
                    myCreatedClasses = response.body();
                    createdClassAdapter = new CreatedClassesAdapterMainAct(getActivity(),myCreatedClasses,userToken);

                    createdClassesList.setAdapter(createdClassAdapter);
                    mFrameLayout.startShimmer();
                    mFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<myClass>> call, Throwable t) {
                CustomeAlertDialog myClass = new CustomeAlertDialog(getActivity(),"Response Error","There is a problem with your internet connection");
            }
        });

        Call<List<myClass>> callBack1 = classAPI.joinedClasses("token "+userToken);
        callBack1.enqueue(new Callback<List<myClass>>() {
            @Override
            public void onResponse(Call<List<myClass>> call, Response<List<myClass>> response) {
                if(!response.isSuccessful())
                {
                    CustomeAlertDialog myClass = new CustomeAlertDialog(getActivity(),"Response Error","There is a problem with your internet connection");
                }
                else{
                    int responseCode = response.code();
                    myjoinedClasses = response.body();
                    joinedClassAdapter = new CreatedClassesAdapterMainAct(getActivity(),myjoinedClasses,userToken);

                    joinedClassesList.setAdapter(joinedClassAdapter);
                    mFrameLayout.startShimmer();
                    mFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<myClass>> call, Throwable t) {
                CustomeAlertDialog myClass = new CustomeAlertDialog(getActivity(),"Response Error","There is a problem with your internet connection");
            }
        });
        return rootView;
    }
}
