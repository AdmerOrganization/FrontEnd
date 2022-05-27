package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.AlertDialogs.CustomeConfirmAlertDialog;
import com.example.tolearn.AlertDialogs.question_item_dialog;
import com.example.tolearn.Entity.question;
import com.example.tolearn.R;

import java.util.List;

public class questionsAdapter extends BaseAdapter {
    Context context;
    List<question> questionList;
    ListView questionListView;
    questionsAdapter adapter;

    public questionsAdapter(Context context, List<question> questionList,ListView listView ) {
        this.context = context;
        this.questionList = questionList;
        this.questionListView = listView;
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
        com.google.android.material.button.MaterialButton deleteQuestion = view.findViewById(R.id.deleteQuestion);

        question currentQuestion = questionList.get(i);
        question.setText(currentQuestion.getQuestion());

        editQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question_item_dialog question_item = new question_item_dialog(context,String.valueOf(i+1),questionList,questionListView,currentQuestion.getQuestion(), currentQuestion.getAnswer1(), currentQuestion.getAnswer2(), currentQuestion.getAnswer3(), currentQuestion.getAnswer4(),currentQuestion.getRigh_ans());
                question_item.DoneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!question_item.IsQuestionOk(question_item.questionET.getText().toString(), context ))
                        {
                            Toast.makeText(context, "question can not be empty", Toast.LENGTH_SHORT).show();
                        }
                        else if(!question_item.IsQuestionOk(question_item.answer1_ET.getText().toString(), context ))
                        {
                            Toast.makeText(context, "answer1 can not be empty", Toast.LENGTH_SHORT).show();
                        }
                        else if(!question_item.IsQuestionOk(question_item.answer2_ET.getText().toString(), context ))
                        {
                            Toast.makeText(context, "answer2 can not be empty", Toast.LENGTH_SHORT).show();
                        }
                        else if(!question_item.IsQuestionOk(question_item.answer3_ET.getText().toString(), context ))
                        {
                            Toast.makeText(context, "answer3 can not be empty", Toast.LENGTH_SHORT).show();
                        }
                        else if(!question_item.IsQuestionOk(question_item.answer4_ET.getText().toString(), context ))
                        {
                            Toast.makeText(context, "answer4 can not be empty", Toast.LENGTH_SHORT).show();
                        }
                        else if(question_item.right_ans.equals(""))
                        {
                            Toast.makeText(context, "chose the correct answer", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            question question = new question(String.valueOf(i+1),question_item.questionET.getText().toString(),question_item.answer1_ET.getText().toString(),question_item.answer2_ET.getText().toString(),question_item.answer3_ET.getText().toString(),question_item.answer4_ET.getText().toString(),question_item.right_ans);
                            questionList.set(i,question);
                            notifyDataSetChanged();
                            question_item.alertDialog.dismiss();
                        }
                    }
                });

            }
        });

        deleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeConfirmAlertDialog deleteConfirmation = new CustomeConfirmAlertDialog(context , "Confrimation"  , "Are you sure you want to delete this question ?");
                deleteConfirmation.image.setImageResource(R.drawable.trash);
                deleteConfirmation.No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteConfirmation.alertDialog.dismiss();
                    }
                });
                deleteConfirmation.Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionList.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "question deleted", Toast.LENGTH_SHORT).show();
                        deleteConfirmation.alertDialog.dismiss();
                    }
                });
            }
        });

        return view;
    }
}
