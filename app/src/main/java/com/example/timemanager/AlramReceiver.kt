package com.example.timemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.startActivity

class AlramReceiver :BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == TimeManager.instance().packageName){
            var intentContent = Intent(context,AlarmActivity::class.java);
            intentContent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            intentContent.putExtra("ID",intent.getStringExtra("ID"));
            intentContent.putExtra("TASK",intent.getStringExtra("TASK"));
            context!!.startActivity(intentContent);
        }
    }
}