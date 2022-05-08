package com.example.tolearn.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CreatedClassesAdapterMainAct extends RecyclerView.Adapter<CreatedClassesAdapterMainAct.ViewHolder>{
    private List<myClass> list;
    private Context context;
    String userToken;
    String userAccess;

    public CreatedClassesAdapterMainAct(Context context , List<myClass> list,String userToken , String userAccess){
        this.list = list;
        this.context = context;
        this.userToken = userToken;
        this.userAccess = userAccess;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_classes_main_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = list.get(position).getTitle();
        String teacher_name = list.get(position).getTeacher_name();
        String category = list.get(position).getCategory();
        String avatar = list.get(position).getAvatar();
        Integer classId =(list.get(position).getId());
        holder.setData(title,teacher_name,category,avatar,classId);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView teacherTextView;
        private ImageView classImage;
        String category;
        String class_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTV);
            teacherTextView = itemView.findViewById(R.id.teacherTV);
            classImage = itemView.findViewById(R.id.categoryImageItemEventView);
        }

        public void setData(String title, String teacher, String category , String avatar , Integer class_id) {
            titleTextView.setText(title);
            teacherTextView.setText(teacher);
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
                    Picasso.get().load(avatar).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(classImage);
                    break;
            }
            classImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent event = new Intent(context, ClassProfileActivity.class);
                    event.putExtra("class_name",title);
                    event.putExtra("class_teacher",teacher);
                    event.putExtra("class_category",category);
                    event.putExtra("class_id",class_id);
                    event.putExtra("user_token",userToken);
                    event.putExtra("user_access",userAccess);
                    context.startActivity(event);
                }
            });
        }
    }
}
