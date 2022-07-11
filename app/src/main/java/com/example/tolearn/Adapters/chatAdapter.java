package com.example.tolearn.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tolearn.Entity.message;
import com.example.tolearn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class chatAdapter extends BaseAdapter {
    List<message>  messageList;
    Context context;
    boolean my_message_this_session;

    public chatAdapter(List<message> messageList, Context context,boolean my_message_this_session) {
        this.messageList = messageList;
        this.context = context;
        this.my_message_this_session = my_message_this_session;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SharedPreferences shP = context.getSharedPreferences("userInformation", context.MODE_PRIVATE);
        String fname = shP.getString("firstname","");
        String lname = shP.getString("lastname","");
        boolean isMe = messageList.get(i).getFname().equals(fname) && messageList.get(i).getLname().equals(lname);

        if(my_message_this_session == true)
        {
            if(view == null)
            {
                view = LayoutInflater.from(context).inflate(R.layout.my_messages, null);
            }
            TextView text = view.findViewById(R.id.message_body);
            text.setText(messageList.get(i).getMessage());
        }
        else{
            if(view == null && isMe) {
                view = LayoutInflater.from(context).inflate(R.layout.my_messages, null);
            }
            else{
                view = LayoutInflater.from(context).inflate(R.layout.their_messages_item, null);
            }

            if(isMe)
            {
                view = LayoutInflater.from(context).inflate(R.layout.my_messages, null);
                TextView text = view.findViewById(R.id.message_body);
                text.setText(messageList.get(i).getMessage());
                TextView timestamp = view.findViewById(R.id.timeText);
                String HourMin = timeStampToHourMin(messageList.get(i).getTimestamp());
                timestamp.setText(HourMin);
                Log.i("CHAAAAAATTTT",messageList.get(i).getMessage());
            }
            else{
                TextView text = view.findViewById(R.id.message_body);
                text.setText(messageList.get(i).getMessage());
                ImageView avatar = view.findViewById(R.id.avatar);
                String avatarStr = messageList.get(i).getAvatar().replace("\"","");
                Log.i("avattar",avatarStr);

                if(messageList.get(i).getLname().equals("Rezakhoo"))
                {
                    Picasso.get().load(avatarStr).placeholder(R.drawable.alireza).error(R.drawable.alireza).into(avatar);
                }
                else{
                    Picasso.get().load(avatarStr).placeholder(R.drawable.acount_circle).error(R.drawable.acount_circle).into(avatar);

                }

                TextView name = view.findViewById(R.id.name);
                name.setText(messageList.get(i).getFname() + " " + messageList.get(i).getLname());

                TextView timestamp = view.findViewById(R.id.timeText);
                String HourMin = timeStampToHourMin(messageList.get(i).getTimestamp());
                timestamp.setText(HourMin);
                Log.i("CHAAAAAATTTT",messageList.get(i).getMessage());
            }
        }
//            if(my_message_this_session == true)
//            {
//                view = LayoutInflater.from(context).inflate(R.layout.my_messages, null);
//                TextView text = view.findViewById(R.id.message_body);
//                text.setText(messageList.get(i).getMessage());
//            }
//            else{
//                SharedPreferences shP = context.getSharedPreferences("userInformation", context.MODE_PRIVATE);
//                String fname = shP.getString("firstname","");
//                String lname = shP.getString("lastname","");
//                if(messageList.get(i).getFname().equals(fname) && messageList.get(i).getLname().equals(lname))
//                {
//                    view = LayoutInflater.from(context).inflate(R.layout.my_messages, null);
//                    TextView text = view.findViewById(R.id.message_body);
//                    text.setText(messageList.get(i).getMessage());
//                }
//                else{
//                    view = LayoutInflater.from(context).inflate(R.layout.their_messages_item, null);
//                    TextView text = view.findViewById(R.id.message_body);
//                    text.setText(messageList.get(i).getMessage());
//
//                    TextView name = view.findViewById(R.id.name);
//                    name.setText(messageList.get(i).getFname() + " " + messageList.get(i).getLname());
//                }
//            }
        return view;

    }

    public String timeStampToHourMin(String timeStamp){
        String [] timeStampArr = timeStamp.split(" ");
        String time = timeStampArr[1].toString();
        String hourMin[] = time.split(":");
        String HourMinStr = hourMin[0] +":" + hourMin[1];
        return HourMinStr;
    }
}
