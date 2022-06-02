package com.example.tolearn.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tolearn.Entity.question;
import com.example.tolearn.R;
import com.google.gson.JsonObject;

import java.util.List;

public class StudentExamQuestionsAdapter extends BaseAdapter {
    Context context;
    List<JsonObject> questionList;
    String right_ans;
    List<String> answers;

    public StudentExamQuestionsAdapter(Context context, List<JsonObject> questionList , List<String> answers) {
        this.context = context;
        this.questionList = questionList;
        this.answers = answers;
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
            view = LayoutInflater.from(context).inflate(R.layout.student_exam_questions_item, null);
        }
        TextView counter = view.findViewById(R.id.counter);
        TextView questionText = view.findViewById(R.id.question);
        TextView answer1Text = view.findViewById(R.id.answer1);
        TextView answer2Text = view.findViewById(R.id.answer2);
        TextView answer3Text = view.findViewById(R.id.answer3);
        TextView answer4Text = view.findViewById(R.id.answer4);

        question currentQuestion = new question(questionList.get(i));
        counter.setText(Integer.toString(currentQuestion.getQuestion_num()));
        questionText.setText(currentQuestion.getQuestion());
        answer1Text.setText(currentQuestion.getAnswer1());
        answer2Text.setText(currentQuestion.getAnswer2());
        answer3Text.setText(currentQuestion.getAnswer3());
        answer4Text.setText(currentQuestion.getAnswer4());

        right_ans = "";

        answer1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1Text.setBackgroundResource(R.drawable.answers_border);
                answer1Text.setTextColor(Color.GRAY);
                answer2Text.setBackgroundResource(R.drawable.answers_border);
                answer2Text.setTextColor(Color.GRAY);
                answer3Text.setBackgroundResource(R.drawable.answers_border);
                answer3Text.setTextColor(Color.GRAY);
                answer4Text.setBackgroundResource(R.drawable.answers_border);
                answer4Text.setTextColor(Color.GRAY);

                answer1Text.setBackgroundResource(R.drawable.red_button_circular);
                answer1Text.setTextColor(Color.WHITE);
                right_ans = "1";
            }
        });

        answer2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1Text.setBackgroundResource(R.drawable.answers_border);
                answer1Text.setTextColor(Color.GRAY);
                answer2Text.setBackgroundResource(R.drawable.answers_border);
                answer2Text.setTextColor(Color.GRAY);
                answer3Text.setBackgroundResource(R.drawable.answers_border);
                answer3Text.setTextColor(Color.GRAY);
                answer4Text.setBackgroundResource(R.drawable.answers_border);
                answer4Text.setTextColor(Color.GRAY);

                answer2Text.setBackgroundResource(R.drawable.red_button_circular);
                answer2Text.setTextColor(Color.WHITE);
                right_ans = "2";
            }
        });

        answer3Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1Text.setBackgroundResource(R.drawable.answers_border);
                answer1Text.setTextColor(Color.GRAY);
                answer2Text.setBackgroundResource(R.drawable.answers_border);
                answer2Text.setTextColor(Color.GRAY);
                answer3Text.setBackgroundResource(R.drawable.answers_border);
                answer3Text.setTextColor(Color.GRAY);
                answer4Text.setBackgroundResource(R.drawable.answers_border);
                answer4Text.setTextColor(Color.GRAY);

                answer3Text.setBackgroundResource(R.drawable.red_button_circular);
                answer3Text.setTextColor(Color.WHITE);
                right_ans = "3";
            }
        });


        answer4Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1Text.setBackgroundResource(R.drawable.answers_border);
                answer1Text.setTextColor(Color.GRAY);
                answer2Text.setBackgroundResource(R.drawable.answers_border);
                answer2Text.setTextColor(Color.GRAY);
                answer3Text.setBackgroundResource(R.drawable.answers_border);
                answer3Text.setTextColor(Color.GRAY);
                answer4Text.setBackgroundResource(R.drawable.answers_border);
                answer4Text.setTextColor(Color.GRAY);

                answer4Text.setBackgroundResource(R.drawable.red_button_circular);
                answer4Text.setTextColor(Color.WHITE);
                right_ans = "4";
            }
        });

        answers.add(right_ans);
        return view;
    }
}
