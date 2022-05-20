package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tolearn.Entity.question;
import com.example.tolearn.R;

import java.util.List;

public class questionsAdapter extends BaseAdapter {
    Context context;
    List<question> questionList;

    public questionsAdapter(Context context, List<question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int i) {
        return questionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.question_item_item, null);
        }

        TextView question = view.findViewById(R.id.question);
        com.google.android.material.button.MaterialButton editQuestion = view.findViewById(R.id.editQuestion);

        question currentQuestion = questionList.get(i);
        question.setText(currentQuestion.getQuestion());

        editQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        return view;
    }
}
