package com.example.tolearn.ui.homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tolearn.databinding.FragmentHomeworkBinding;

public class HomeworkFragment extends Fragment {

    private HomeWorkViewModel homeWorkViewModel;
    private FragmentHomeworkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeWorkViewModel =
                new ViewModelProvider(this).get(HomeWorkViewModel.class);

        binding = FragmentHomeworkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHomework;
        homeWorkViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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