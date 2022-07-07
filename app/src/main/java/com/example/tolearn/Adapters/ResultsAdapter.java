package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tolearn.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends BaseAdapter {
    Context context;
    List<JsonObject> resultArray;
    public ResultsAdapter(Context context , JsonArray result_array)
    {
        resultArray = new ArrayList<>();
        this.context = context;
        for( int i= 0;i<result_array.size();i++)
        {
            JsonObject newResult = result_array.get(i).getAsJsonObject();
            resultArray.add(newResult);
        }
    }

    @Override
    public int getCount() {
        return resultArray.size();
    }

    @Override
    public Object getItem(int i) {
        return resultArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view== null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.student_result_view,null);
        }
        TextView firstLastName = view.findViewById(R.id.firsnameLastName);
        TextView score = view.findViewById(R.id.score);

        String firstName = resultArray.get(i).get("first_name").toString();
        String lastName = resultArray.get(i).get("last_name").toString();
        firstName = firstName.replace("\"" , "");
        lastName = lastName.replace("\"","");
        firstLastName.setText(firstName + " " + lastName);

        String scoreStr = resultArray.get(i).get("score").toString();
        scoreStr = scoreStr.replace("\"","");
        score.setText(scoreStr);

        return view ;
    }
}
