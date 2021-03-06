package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tolearn.AlertDialogs.CustomeAlertdialogJoinClass;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class classAdapterSearch extends BaseAdapter implements Filterable {

    private Context context;
    private List<myClass> list;
    private List<myClass> temp;
    String type;

    public classAdapterSearch(Context context, List<myClass> list, String type) {
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
            view = LayoutInflater.from(context).inflate(R.layout.my_class_view,null);
        }

        myClass currentMyClass = list.get(i);

        TextView title = view.findViewById(R.id.titleEventView);
        TextView date = view.findViewById(R.id.dateTimeEventView);
        TextView teacher = view.findViewById(R.id.TeacherTextView);
        com.google.android.material.button.MaterialButton editOrJoinBtn = view.findViewById(R.id.eventEditOrJoinBtn);
        com.google.android.material.button.MaterialButton  deleteBtn = view.findViewById(R.id.deleteBtn);
        deleteBtn.setVisibility(View.INVISIBLE);
        editOrJoinBtn.setIconResource(R.drawable.ic_baseline_person_add_24);
        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItemEventView);
        String dateTime = currentMyClass.getTime().toString();
        String [] dateTimeInfo = dateTime.split("T");
        dateTime = dateTimeInfo[0];
        title.setText(currentMyClass.getTitle().toString());
        date.setText(dateTime);
        teacher.setText(currentMyClass.getTeacher_name().toString());
        //desc.setText(currentMyClass.getDescription().toString());

        //Picasso.get().load(currentMyClass.getAvatar()).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(imageViewCategory);

        try{
            String category = currentMyClass.getCategory().toString();

            switch (category)
            {
                case "Math":
                    imageViewCategory.setImageResource(R.drawable.math);
                    break;
                case "Chemistry":
                    imageViewCategory.setImageResource(R.drawable.chemistry);
                    break;
                case "Physics":
                    imageViewCategory.setImageResource(R.drawable.atom);
                    break;
                case "Engineering":
                    imageViewCategory.setImageResource(R.drawable.engineering);
                    break;
                case "Paint":
                    imageViewCategory.setImageResource(R.drawable.paint);
                    break;
                case "Music":
                    imageViewCategory.setImageResource(R.drawable.musical);
                    break;
                case "Cinema":
                    imageViewCategory.setImageResource(R.drawable.clapperboard);
                    break;
                case "athletic":
                    imageViewCategory.setImageResource(R.drawable.athletics);
                    break;
                case "computer science":
                    imageViewCategory.setImageResource(R.drawable.responsive);
                    break;
                case "language":
                    imageViewCategory.setImageResource(R.drawable.languages);
                    break;
                default:
                    Picasso.get().load(currentMyClass.getAvatar()).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(imageViewCategory);
                    break;
            }
        }
        catch (Exception exception)
        {
            imageViewCategory.setImageResource(R.drawable.learninglogo2);
        }


        editOrJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classroom_token = currentMyClass.getClassroom_token().toString();
                CustomeAlertdialogJoinClass joinClass = new CustomeAlertdialogJoinClass(context,classroom_token);

            }
        });


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

                ArrayList<myClass> filterList = new ArrayList<>();
                for(myClass item:temp)
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
                list = (List<myClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
