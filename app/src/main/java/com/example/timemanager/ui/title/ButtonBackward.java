package com.example.timemanager.ui.title;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class ButtonBackward implements View.OnClickListener {
    private Activity activity;
    public ButtonBackward(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.finish();
    }
}
