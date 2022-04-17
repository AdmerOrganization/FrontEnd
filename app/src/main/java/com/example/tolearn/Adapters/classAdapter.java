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

import com.example.tolearn.Entity.myClass;
import com.example.tolearn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class classAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<myClass> list;
    private List<myClass> temp;

    public classAdapter(Context context, List<myClass> list) {
        this.context = context;
        this.list = list;
        this.temp = list;
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
        TextView desc = view.findViewById(R.id.descEventView);
        Button editBtn = view.findViewById(R.id.eventEditBtn);
        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItemEventView);
        String dateTime = currentMyClass.getTime().toString();
        String [] dateTimeInfo = dateTime.split("T");
        dateTime = dateTimeInfo[0];
        title.setText(currentMyClass.getTitle().toString());
        date.setText(dateTime);
        teacher.setText(currentMyClass.getTeacher_name().toString());
        desc.setText(currentMyClass.getDescription().toString());

        String category = currentMyClass.getCategory().toString();

        if(currentMyClass.getAvatar().toString().equals(""))
        {
            Picasso.get().load(currentMyClass.getAvatar()).placeholder(R.drawable.acount_circle).error(R.drawable.acount_circle).into(imageViewCategory);
        }
        else{
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
            }
        }




//        editBtn.setOnClickListener(new View.OnClickListener() {
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
