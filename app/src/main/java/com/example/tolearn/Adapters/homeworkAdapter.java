package com.example.tolearn.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tolearn.AlertDialogs.CustomeAlertdialogJoinClass;
import com.example.tolearn.Entity.Homework;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class homeworkAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Homework> list;
    private List<Homework> temp;
    String type;

    public homeworkAdapter(Context context, List<Homework> list, String type) {
        this.context = context;
        this.list = list;
        this.temp = list;
        this.type = type;
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
            view = LayoutInflater.from(context).inflate(R.layout.homework_list_view,null);
        }

        Homework currentHomework = list.get(i);
        TextView title = view.findViewById(R.id.homeworkTextview);
        TextView deadline = view.findViewById(R.id.deadlineTextview);
        title.setText(currentHomework.getTitle());
        deadline.setText(currentHomework.getDeadline());

        Button editBtn = view.findViewById(R.id.editBtn);
        SharedPreferences shp2 = context.getSharedPreferences("classId",context.MODE_PRIVATE);
        String access = shp2.getString("user_access","");
        if(access.equals("student"))
        {
            editBtn.setClickable(false);
            editBtn.setVisibility(View.INVISIBLE);
        }
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click here to go to the edit class", Toast.LENGTH_SHORT).show();
                //Intent to edit homework page ....
            }
        });

//        TextView title = view.findViewById(R.id.titleEventView);
//        TextView date = view.findViewById(R.id.dateTimeEventView);
//        TextView teacher = view.findViewById(R.id.TeacherTextView);
//        TextView desc = view.findViewById(R.id.descEventView);
//        Button editOrJoinBtn = view.findViewById(R.id.eventEditOrJoinBtn);
//        Button deleteBtn = view.findViewById(R.id.deleteBtn);
//        deleteBtn.setVisibility(View.INVISIBLE);
//        editOrJoinBtn.setText("Join");
//        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItemEventView);
//        String dateTime = currentHomework.getTime().toString();
//        String [] dateTimeInfo = dateTime.split("T");
//        dateTime = dateTimeInfo[0];
//        title.setText(currentHomework.getTitle().toString());
//        date.setText(dateTime);
//        teacher.setText(currentHomework.getTeacher_name().toString());
        //desc.setText(currentHomework.getDescription().toString());

        //Picasso.get().load(currentHomework.getAvatar()).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(imageViewCategory);

//        try{
//            String category = currentHomework.getCategory().toString();
//
//            switch (category)
//            {
//                case "Math":
//                    imageViewCategory.setImageResource(R.drawable.math);
//                    break;
//                case "Chemistry":
//                    imageViewCategory.setImageResource(R.drawable.chemistry);
//                    break;
//                case "Physics":
//                    imageViewCategory.setImageResource(R.drawable.atom);
//                    break;
//                case "Engineering":
//                    imageViewCategory.setImageResource(R.drawable.engineering);
//                    break;
//                case "Paint":
//                    imageViewCategory.setImageResource(R.drawable.paint);
//                    break;
//                case "Music":
//                    imageViewCategory.setImageResource(R.drawable.musical);
//                    break;
//                case "Cinema":
//                    imageViewCategory.setImageResource(R.drawable.clapperboard);
//                    break;
//                case "athletic":
//                    imageViewCategory.setImageResource(R.drawable.athletics);
//                    break;
//                case "computer science":
//                    imageViewCategory.setImageResource(R.drawable.responsive);
//                    break;
//                case "language":
//                    imageViewCategory.setImageResource(R.drawable.languages);
//                    break;
//                default:
//                    Picasso.get().load(currentHomework.getAvatar()).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(imageViewCategory);
//                    break;
//            }
//        }
//        catch (Exception exception)
//        {
//            imageViewCategory.setImageResource(R.drawable.learninglogo2);
//        }


//        editOrJoinBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String classroom_token = currentHomework.getClassroom_token().toString();
//                CustomeAlertdialogJoinClass joinClass = new CustomeAlertdialogJoinClass(context,classroom_token);
//
//            }
//        });


//        editOrJoinBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                Intent intent = new Intent(context, EditEventActivity.class);
//                intent.putExtra("token", currentEvent.getEvent_token());
//                context.startActivity(intent);
//            }
//        });

        return view;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                ArrayList<Homework> filterList = new ArrayList<>();
                for(Homework item:temp)
                {
                    if(item.getTitle().toString().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filterList.add(item);
                    }
                }

                filterResults.count = filterList.size();
                filterResults.values = filterList;

                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<Homework>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
