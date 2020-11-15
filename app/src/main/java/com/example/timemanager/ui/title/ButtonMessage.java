package com.example.timemanager.ui.title;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.timemanager.messagePackage.MessageView;


public class ButtonMessage implements View.OnClickListener {
    private Context context;
    private Activity activity;
    public ButtonMessage(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, MessageView.class);
        activity.startActivity(intent);
    }
}
