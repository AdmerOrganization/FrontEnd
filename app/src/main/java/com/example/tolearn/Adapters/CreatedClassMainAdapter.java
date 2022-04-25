package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CreatedClassMainAdapter extends BaseAdapter{

    private Context context;
    private List<myClass> list;
    String userToken;

    public CreatedClassMainAdapter(Context context, List<myClass> list, String userToken) {
        this.context = context;
        this.list = list;
        this.userToken = userToken;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.joined_classes_main_view, null);
        }
        myClass currentClass = list.get(i);

        TextView title = view.findViewById(R.id.titleTV);
        TextView teacher = view.findViewById(R.id.teacherTV);
        ImageView classImage = view.findViewById(R.id.categoryImageItemEventView);

        title.setText(currentClass.getTitle());
        teacher.setText(currentClass.getTeacher_name());
        String category = currentClass.getCategory();

        switch (category)
        {
            case "Math":
                classImage.setImageResource(R.drawable.math);
                break;
            case "Chemistry":
                classImage.setImageResource(R.drawable.chemistry);
                break;
            case "Physics":
                classImage.setImageResource(R.drawable.atom);
                break;
            case "Engineering":
                classImage.setImageResource(R.drawable.engineering);
                break;
            case "Paint":
                classImage.setImageResource(R.drawable.paint);
                break;
            case "Music":
                classImage.setImageResource(R.drawable.musical);
                break;
            case "Cinema":
                classImage.setImageResource(R.drawable.clapperboard);
                break;
            case "athletic":
                classImage.setImageResource(R.drawable.athletics);
                break;
            case "computer science":
                classImage.setImageResource(R.drawable.responsive);
                break;
            case "language":
                classImage.setImageResource(R.drawable.languages);
                break;
            default:
                Picasso.get().load(currentClass.getAvatar()).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(classImage);
                break;
        }
        return view;
    }
}
