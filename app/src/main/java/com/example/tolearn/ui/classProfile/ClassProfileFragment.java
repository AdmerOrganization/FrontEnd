package com.example.tolearn.ui.classProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tolearn.Adapters.membersAdapter;
import com.example.tolearn.Entity.member;
import com.example.tolearn.R;

import java.util.ArrayList;
import java.util.List;

public class ClassProfileFragment extends Fragment {
    TextView classroom_title;
    TextView classroom_teacher;
    TextView classroom_desc;
    TextView classroom_category;
    TextView classroom_limit;
    ListView classroom_members;
    membersAdapter membersAdap;

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
        //retrofit for getting the information from the backend

        //this code have to be deleted in the future..........................................................
        List<member> members = new ArrayList<>();
        member s1 = new member("saman");
        member s2 = new member("barbod");
        members.add(s1);
        members.add(s2);
        //until this line......................................................................................



        //these two lines have to be in the retrofit body
        membersAdap = new membersAdapter(getActivity(),members);
        classroom_members.setAdapter(membersAdap);

        return rootView;
    }
}
