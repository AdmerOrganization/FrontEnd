package com.example.tolearn.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tolearn.Entity.message;
import com.example.tolearn.R;

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
        if(view == null)
        {
            if(my_message_this_session == true)
            {
                view = LayoutInflater.from(context).inflate(R.layout.my_messages, null);
                TextView text = view.findViewById(R.id.message_body);
                text.setText(messageList.get(i).getMessage());
            }
            else{
                SharedPreferences shP = context.getSharedPreferences("userInformation", context.MODE_PRIVATE);
                String fname = shP.getString("firstname","");
                String lname = shP.getString("lastname","");
                if(messageList.get(i).getFname().equals(fname) && messageList.get(i).getLname().equals(lname))
                {
                    view = LayoutInflater.from(context).inflate(R.layout.my_messages, null);
                    TextView text = view.findViewById(R.id.message_body);
                    text.setText(messageList.get(i).getMessage());
                }
                else{
                    view = LayoutInflater.from(context).inflate(R.layout.their_messages_item, null);
                    TextView text = view.findViewById(R.id.message_body);
                    text.setText(messageList.get(i).getMessage());

                    TextView name = view.findViewById(R.id.name);
                    name.setText(messageList.get(i).getFname() + " " + messageList.get(i).getLname());
                }
            }
        }
        return view;
    }
}
