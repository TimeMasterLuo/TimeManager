package com.example.timemanager.adapter.dashBoardAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.example.timemanager.R;
import com.example.timemanager.adapter.MyBaseAdapter;
import com.example.timemanager.ui.dashboard.HistoryDetail;
import com.example.timemanager.utils.clock.Clock;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClockAdapter extends MyBaseAdapter<Clock> {
    public ClockAdapter(Context context, List<Clock> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.clock_list_adapter_item, null);
        }
        String setter = mList.get(position).getSet_person();
        String user= mList.get(position).getUser();
        if(!setter.equals(user)){
            ImageView image=(ImageView) convertView.findViewById(R.id.setter);
            image.setImageResource(R.drawable.friend_clock);
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 =new SimpleDateFormat("HH:mm");
        format.format(mList.get(position).getDate());
        format1.format(mList.get(position).getDate());
        TextView clockName = (TextView) convertView.findViewById(R.id.detail);
        clockName.setText(format.format(mList.get(position).getDate())+" "+format1.format(mList.get(position).getDate()));
        Button detail=convertView.findViewById(R.id.detail_button);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, HistoryDetail.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("clock",mList.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public void resetData(List<Clock> list) {
        if(list != null){
            mList.clear();
            mList.addAll(list);
        }
    }

    public List<Clock> getData() {
        return mList;
    }
}
