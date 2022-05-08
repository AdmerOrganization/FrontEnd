package com.example.tolearn.ui.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tolearn.Adapters.homeworkAdapter;
import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.R;
import com.example.tolearn.databinding.FragmentHomeworkBinding;

public class HomeworkFragment extends Fragment {

    private HomeWorkViewModel homeWorkViewModel;
    private FragmentHomeworkBinding binding;
    private ConstraintLayout constraintLayout ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeWorkViewModel =
                new ViewModelProvider(this).get(HomeWorkViewModel.class);

        binding = FragmentHomeworkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        while(((ClassProfileActivity)getActivity()).homeworktypeList == null){
//
//        }
        ListView homeworkListview = root.findViewById(R.id.homeworksList);
        homeworkAdapter myadap = new homeworkAdapter(this.getActivity(),((ClassProfileActivity)getActivity()).homeworktypeList,"");
        homeworkListview.setAdapter(myadap);

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
}