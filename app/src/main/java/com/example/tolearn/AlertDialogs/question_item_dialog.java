package com.example.tolearn.AlertDialogs;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.tolearn.Entity.question;
import com.example.tolearn.R;

import java.util.List;

public class question_item_dialog {

    public AlertDialog alertDialog;
    TextView counterTv;
    EditText questionET;
    EditText answer1_ET;
    EditText answer2_ET;
    EditText answer3_ET;
    EditText answer4_ET;
    String right_ans;
    public Button DoneBtn;
    public question_item_dialog(Context context,String counter, List<question> questionList)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);

        View alertView = LayoutInflater.from(context).inflate(R.layout.exam_question_item_dialog,null);
        builder.setView(alertView);

        alertDialog = builder.create();
        alertDialog.show();

        counterTv = alertView.findViewById(R.id.counter);
        counterTv.setText(counter);
        questionET = alertView.findViewById(R.id.question);
        answer1_ET = alertView.findViewById(R.id.answer1);
        answer2_ET = alertView.findViewById(R.id.answer2);
        answer3_ET = alertView.findViewById(R.id.answer3);
        answer4_ET = alertView.findViewById(R.id.answer4);
        DoneBtn = alertView.findViewById(R.id.doneBtn);

        right_ans = "";

        answer1_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1_ET.setBackgroundResource(R.drawable.answers_border);
                answer1_ET.setTextColor(Color.GRAY);
                answer2_ET.setBackgroundResource(R.drawable.answers_border);
                answer2_ET.setTextColor(Color.GRAY);
                answer3_ET.setBackgroundResource(R.drawable.answers_border);
                answer3_ET.setTextColor(Color.GRAY);
                answer4_ET.setBackgroundResource(R.drawable.answers_border);
                answer4_ET.setTextColor(Color.GRAY);

                answer1_ET.setBackgroundResource(R.drawable.red_button_circular);
                answer1_ET.setTextColor(Color.WHITE);
                right_ans = "1";
            }
        });

        answer2_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1_ET.setBackgroundResource(R.drawable.answers_border);
                answer1_ET.setTextColor(Color.GRAY);
                answer2_ET.setBackgroundResource(R.drawable.answers_border);
                answer2_ET.setTextColor(Color.GRAY);
                answer3_ET.setBackgroundResource(R.drawable.answers_border);
                answer3_ET.setTextColor(Color.GRAY);
                answer4_ET.setBackgroundResource(R.drawable.answers_border);
                answer4_ET.setTextColor(Color.GRAY);

                answer2_ET.setBackgroundResource(R.drawable.red_button_circular);
                answer2_ET.setTextColor(Color.WHITE);
                right_ans = "2";
            }
        });

        answer3_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1_ET.setBackgroundResource(R.drawable.answers_border);
                answer1_ET.setTextColor(Color.GRAY);
                answer2_ET.setBackgroundResource(R.drawable.answers_border);
                answer2_ET.setTextColor(Color.GRAY);
                answer3_ET.setBackgroundResource(R.drawable.answers_border);
                answer3_ET.setTextColor(Color.GRAY);
                answer4_ET.setBackgroundResource(R.drawable.answers_border);
                answer4_ET.setTextColor(Color.GRAY);

                answer3_ET.setBackgroundResource(R.drawable.red_button_circular);
                answer3_ET.setTextColor(Color.WHITE);
                right_ans = "3";
            }
        });


        answer4_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1_ET.setBackgroundResource(R.drawable.answers_border);
                answer1_ET.setTextColor(Color.GRAY);
                answer2_ET.setBackgroundResource(R.drawable.answers_border);
                answer2_ET.setTextColor(Color.GRAY);
                answer3_ET.setBackgroundResource(R.drawable.answers_border);
                answer3_ET.setTextColor(Color.GRAY);
                answer4_ET.setBackgroundResource(R.drawable.answers_border);
                answer4_ET.setTextColor(Color.GRAY);

                answer4_ET.setBackgroundResource(R.drawable.red_button_circular);
                answer4_ET.setTextColor(Color.WHITE);
                right_ans = "4";
            }
        });

        DoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!IsQuestionOk(questionET.getText().toString(), context ))
                {
                    Toast.makeText(context, "question can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!IsQuestionOk(answer1_ET.getText().toString(), context ))
                {
                    Toast.makeText(context, "answer1 can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!IsQuestionOk(answer2_ET.getText().toString(), context ))
                {
                    Toast.makeText(context, "answer2 can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!IsQuestionOk(answer3_ET.getText().toString(), context ))
                {
                    Toast.makeText(context, "answer3 can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(!IsQuestionOk(answer4_ET.getText().toString(), context ))
                {
                    Toast.makeText(context, "answer4 can not be empty", Toast.LENGTH_SHORT).show();
                }
                else if(right_ans.equals(""))
                {
                    Toast.makeText(context, "chose the correct answer", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    question question = new question(counter,questionET.getText().toString(),answer1_ET.getText().toString(),answer2_ET.getText().toString(),answer3_ET.getText().toString(),answer4_ET.getText().toString(),right_ans);
                    questionList.add(question);
                    alertDialog.dismiss();
                }
            }
        });
    }

    public boolean IsQuestionOk(String text , Context context)
    {
        if(text.length()==0)
        {
            return false;
        }
        return true;
    }

}