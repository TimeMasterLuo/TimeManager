package com.example.timemanager.utils.tools

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.alarm.AlramReceiver
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import java.text.SimpleDateFormat
import java.util.*

class AlarmTools {

    companion object {

        private var alarmManager = TimeManager.instance().getSystemService(Context.ALARM_SERVICE) as AlarmManager;

        @SuppressLint("SimpleDateFormat")
        fun setAlarm(context :Context, model: T_ALARM_CLOCK){
            var triggerAtTime = System.currentTimeMillis();

//                var calendar = Calendar.getInstance();
//                calendar.set(Calendar.YEAR, SimpleDateFormat("yyyy").format(model.Date).toInt());
//                calendar.set(Calendar.MONTH,model.Date.substring(model.Date.indexOf("/")+1).toInt())
//                calendar.set(Calendar.DAY_OF_MONTH,model.Date.substring(model.Date.indexOf("/")+2).toInt())
//                calendar.set(Calendar.HOUR_OF_DAY, model.TIME.substring(0,model.TIME.indexOf(":")).toInt());
//                calendar.set(Calendar.MINUTE,model.TIME.substring(model.TIME.indexOf(":")+1).toInt());
//                calendar.set(Calendar.SECOND,0);
                var intent = Intent(context, AlramReceiver::class.java);
                intent.action = TimeManager.instance().packageName;
                intent.putExtra("ID",model.ID);
                //intent.putExtra("TASK",model.Task);
            println("set alarm:"+model.ID);
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:SS") //注意月份是MM

            val date: Date = simpleDateFormat.parse(model.TIMESTAMP)
//                var timeToAlarm= Date(model.TIMESTAMP);
//                var calendar = Calendar.getInstance();
//                calendar.set(Calendar.YEAR, timeToAlarm.year);
//                calendar.set(Calendar.MONTH,timeToAlarm.month)
//                calendar.set(Calendar.DAY_OF_MONTH,timeToAlarm.day)
//                calendar.set(Calendar.HOUR_OF_DAY, timeToAlarm.hours);
//                calendar.set(Calendar.MINUTE,timeToAlarm.minutes);
//                calendar.set(Calendar.SECOND,0);
                var pi = PendingIntent.getBroadcast(context,model.ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,date.time,pi);
        }

        fun cancelAlarm(context :Context , model: T_ALARM_CLOCK){
                var intent = Intent(context, AlramReceiver::class.java);
                intent.action = TimeManager.instance().packageName;
                intent.putExtra("ID",model.ID);
                var pi = PendingIntent.getBroadcast(context,model.ID,intent,PendingIntent.FLAG_NO_CREATE);
                if(pi !=null){
                    alarmManager.cancel(pi)
                }
        }
    }
}