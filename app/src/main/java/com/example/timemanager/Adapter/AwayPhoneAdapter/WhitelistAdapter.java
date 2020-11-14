package com.example.timemanager.Adapter.AwayPhoneAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.example.timemanager.Adapter.MyBaseAdapter;
import com.example.timemanager.R;

import java.util.List;

public class WhitelistAdapter extends MyBaseAdapter<AppUtils.AppInfo> {
    public WhitelistAdapter(Context context, List<AppUtils.AppInfo> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_test1, null);
        }
        String userName = mList.get(position).getName();
        ImageView appIcon = (ImageView) convertView.findViewById(R.id.appIcon);
        appIcon.setBackground(mList.get(position).getIcon());
        TextView appName = (TextView) convertView.findViewById(R.id.appName);
        appName.setText(mList.get(position).getName());
        TextView appVersion = (TextView) convertView.findViewById(R.id.appVersion);
        appVersion.setText(mList.get(position).getVersionName());
        return convertView;
    }
}
