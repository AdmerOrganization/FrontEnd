package com.example.tolearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tolearn.Entity.member;
import com.example.tolearn.R;

import java.util.List;
import java.util.zip.Inflater;

public class membersAdapter extends BaseAdapter {
    Context context;
    List<member> members;

    public membersAdapter(Context context,List<member> members)
    {
        this.context = context;
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int i) {
        return members.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.member_item,null);
        }

        TextView memberName = view.findViewById(R.id.memberName);
        member currentMember = members.get(i);
        memberName.setText(currentMember.getFullName());
        return view;
    }
}
