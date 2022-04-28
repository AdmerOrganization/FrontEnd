package com.example.tolearn.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
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

import com.example.tolearn.AlertDialogs.CustomEditClassAlertDialog;
import com.example.tolearn.AlertDialogs.CustomeAlertDialog;
import com.example.tolearn.AlertDialogs.CustomeConfirmAlertDialog;
import com.example.tolearn.ClassProfileActivity;
import com.example.tolearn.ClassSearch;
import com.example.tolearn.Entity.myClass;
import com.example.tolearn.Login;
import com.example.tolearn.MainActivity;
import com.example.tolearn.R;
import com.example.tolearn.manageClass;
import com.example.tolearn.webService.ClassAPI;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class classAdapterManage extends BaseAdapter implements Filterable {

    private Context context;
    private List<myClass> list;
    private List<myClass> temp;
    private ClassAPI classAPI;
    String userToken;
    String type;

    public classAdapterManage(Context context, List<myClass> list, String type, String userToken) {
        this.context = context;
        this.list = list;
        this.temp = list;
        this.type = type;
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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_class_view, null);
        }

        myClass currentMyClass = list.get(i);

        TextView title = view.findViewById(R.id.titleEventView);
        TextView date = view.findViewById(R.id.dateTimeEventView);
        TextView teacher = view.findViewById(R.id.TeacherTextView);
        TextView desc = view.findViewById(R.id.descEventView);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);
        Button editOrJoinBtn = view.findViewById(R.id.eventEditOrJoinBtn);
        editOrJoinBtn.setText("Edit");
        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItemEventView);
        String dateTime = currentMyClass.getTime().toString();
        String[] dateTimeInfo = dateTime.split("T");
        dateTime = dateTimeInfo[0];
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        imageViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event = new Intent(context, ClassProfileActivity.class);
                event.putExtra("class_name",currentMyClass.getTitle());
                event.putExtra("class_teacher",currentMyClass.getTeacher_name());
                event.putExtra("class_category",currentMyClass.getCategory());
                context.startActivity(event);
            }
        });
        Retrofit taskStatus = new Retrofit.Builder()
                .baseUrl(ClassAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        classAPI = taskStatus.create(ClassAPI.class);
        editOrJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentMyClass.getAvatar() != null){
                CustomEditClassAlertDialog edit = new CustomEditClassAlertDialog(context,userToken,currentMyClass.getClassroom_token(),currentMyClass.getTitle().toString(),currentMyClass.getTeacher_name().toString(),currentMyClass.getDescription().toString(),"Password",Integer.toString(currentMyClass.getLimit()),currentMyClass.getCategory().toString(),currentMyClass.getAvatar().toString());
                }
                else {
                    CustomEditClassAlertDialog edit = new  CustomEditClassAlertDialog(context,userToken,currentMyClass.getClassroom_token(),currentMyClass.getTitle().toString(),currentMyClass.getTeacher_name().toString(),currentMyClass.getDescription().toString(),"Password",Integer.toString(currentMyClass.getLimit()),currentMyClass.getCategory().toString(),"NULL");

                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomeConfirmAlertDialog confirmCancel = new CustomeConfirmAlertDialog(context,"Confirmation","Do you want to delete this class("+currentMyClass.getTitle().toString()+")?");
                confirmCancel.Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmCancel.alertDialog.dismiss();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("classroom_token", currentMyClass.getClassroom_token());
                        Call<JsonObject> request = classAPI.deleteClass("token "+userToken,jsonObject);
                        request.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(!response.isSuccessful())
                                {
                                    CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");

                                }
                                else{
                                    String code = Integer.toString(response.code());
                                    //Toast.makeText(context, code, Toast.LENGTH_SHORT).show();
                                    remove(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");
                            }
                        });

//                        //offline part....
//                        tasksDB tasksdb = new tasksDB(context);
//                        tasksdb.deleteTask(currentTask.getTaskToken());

                    }
                });

                confirmCancel.No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmCancel.alertDialog.dismiss();
                    }
                });
            }
        });
        title.setText(currentMyClass.getTitle().toString());
        date.setText(dateTime);
        teacher.setText(currentMyClass.getTeacher_name().toString());
        desc.setText(currentMyClass.getDescription().toString());
        String category = currentMyClass.getCategory().toString();
        //Picasso.get().load(currentMyClass.getAvatar()).placeholder(R.drawable.learninglogo2).error(R.drawable.learninglogo2).into(imageViewCategory);

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
        return view;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                ArrayList<myClass> filterList = new ArrayList<>();
                for (myClass item : temp) {
                    if (item.getTitle().toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filterList.add(item);
                    }
                }

                filterResults.count = filterList.size();
                filterResults.values = filterList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<myClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public void remove(int position){
        list.remove(list.get(position));
        this.notifyDataSetChanged();
    }


}
