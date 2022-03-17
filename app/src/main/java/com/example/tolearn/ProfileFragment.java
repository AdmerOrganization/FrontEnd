package com.example.tolearn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView firstNameLastNameProfileFragment;
        TextView userNameProfileFragment;
        TextView emailProfileFragment;
        TextView phoneProfileFragment;
        ImageView profileImage;
        profileImage = rootView.findViewById(R.id.profileImageSource);
        firstNameLastNameProfileFragment = rootView.findViewById(R.id.firstNameLastNameTextView);
        userNameProfileFragment = rootView.findViewById(R.id.userNameTextViewProfileFragment);
        emailProfileFragment = rootView.findViewById(R.id.emailTextViewProfileFragment);
        phoneProfileFragment = rootView.findViewById(R.id.phoneNumberProfileFragment);
        SharedPreferences shP = getActivity().getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String userName = shP.getString("username","");
        String firstName = shP.getString("firstname","");
        String avatarUrl = shP.getString("avatar","");
        String lastName = shP.getString("lastname","");
        String email = shP.getString("email","");
        String phoneNumber = shP.getString("phonenumber","");
        firstNameLastNameProfileFragment.setText(new StringBuilder().append(firstName).append(" ").append(lastName).toString());
        if(!avatarUrl.equals("")){
            Picasso.get().load(avatarUrl).placeholder(R.drawable.acount_circle).error(R.drawable.acount_circle).into(profileImage);
        }
        if(!userName.equals("")){
            userNameProfileFragment.setText(userName);
        }
        else{
            userNameProfileFragment.setText("NO USERNAME");
        }
        if(!email.equals("")){
            emailProfileFragment.setText(email);
        }
        else{
            emailProfileFragment.setText("NO EMAIL");
        }
        if(!phoneNumber.equals("")){
            phoneProfileFragment.setText(phoneNumber);
        }
        else{
            phoneProfileFragment.setText("NO PHONE");
        }
        return rootView;
    }
}
