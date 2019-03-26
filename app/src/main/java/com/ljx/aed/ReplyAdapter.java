package com.ljx.aed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ljx.aed.db.Advice;

import java.util.List;

public class ReplyAdapter extends ArrayAdapter {
    private final int resourceId;
    public ReplyAdapter(Context context, int textViewResourceId, List<Advice> adviceList){
        super(context,textViewResourceId,adviceList);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Advice advice = (Advice) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView reply = view.findViewById(R.id.reply);
        reply.setText("留言时间 "+advice.getGtime()+"\n"+"留言内容 "+advice.getMessage()+"\n"+"回复时间 "+advice.getRtime()+"\n"+"回复内容 "+advice.getReply());
        return view;
    }

}
