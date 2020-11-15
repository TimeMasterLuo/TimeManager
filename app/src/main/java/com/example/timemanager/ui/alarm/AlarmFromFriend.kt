package com.example.timemanager.ui.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timemanager.R
import kotlinx.android.synthetic.main.activity_alarm_from_friend.*

class AlarmFromFriend : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_from_friend)
        dateTimePicker2.setOnDateTimeChangedListener { millisecond ->  }
    }
}