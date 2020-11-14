package com.example.timemanager.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.timemanager.application.TimeManager

class AlramReceiver :BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == TimeManager.instance().packageName){
            var intentContent = Intent(context, AlarmActivity::class.java);
            intentContent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            intentContent.putExtra("ID",intent.getStringExtra("ID"));
            intentContent.putExtra("TASK",intent.getStringExtra("TASK"));
            context!!.startActivity(intentContent);
        }
    }
}