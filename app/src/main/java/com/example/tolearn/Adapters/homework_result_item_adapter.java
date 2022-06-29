package com.example.tolearn.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tolearn.AlertDialogs.Homework_result_detailed;
import com.example.tolearn.Entity.Homework_result;
import com.example.tolearn.Entity.User;
import com.example.tolearn.Homework_results;
import com.example.tolearn.R;
import com.example.tolearn.webService.HomeworkAPI;

import java.util.List;

public class homework_result_item_adapter extends BaseAdapter {

    private Context context;
    private List<Homework_result> results;
    private HomeworkAPI homeworkAPI;
    String userToken;

    public homework_result_item_adapter(Context context, List<Homework_result> results,String userToken) {
        this.context = context;
        this.results = results;
        this.userToken = userToken;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.member_homework_item,null);
        }

        Homework_result homework_result = results.get(i);

        TextView full_name = view.findViewById(R.id.memberName);
        com.google.android.material.button.MaterialButton downloadbtn = view.findViewById(R.id.downloadHomeworkPdf);

        String url = homework_result.getFile();
        User user = homework_result.getUser();
        String date = homework_result.getDate();

        String first_name = user.getFirst_name();
        String last_name = user.getLast_name();
        String full_name_str = first_name + " " + last_name;

        full_name.setText(full_name_str);
        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homework_result_detailed result_detailed = new Homework_result_detailed(context , full_name_str , date , url);
            }
        });
        return view;
    }
}
