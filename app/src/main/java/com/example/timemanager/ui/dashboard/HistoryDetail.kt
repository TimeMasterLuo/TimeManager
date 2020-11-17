package com.example.timemanager.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.timemanager.R
import com.example.timemanager.utils.clock.Clock
import java.text.SimpleDateFormat

class HistoryDetail : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_history_detail)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()
        val clock: Clock = intent.getSerializableExtra("clock") as Clock
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date=format.format(clock.date)
        val type=findViewById<TextView>(R.id.typeText)
        type.text=clock.type
        val user=findViewById<TextView>(R.id.set_user)
        user.text=clock.set_person
        val dating=findViewById<TextView>(R.id.date)
        dating.text=date
        val time=findViewById<TextView>(R.id.clock_time)
        time.text=clock.set_time
        val finishTime=findViewById<TextView>(R.id.time_to_finish)
        finishTime.text=clock.time_to_finish
        val coins=findViewById<TextView>(R.id.coins)
        coins.text= clock.coins.toString()
        val grade=findViewById<TextView>(R.id.grade)
        grade.text=clock.grade
        val notes=findViewById<TextView>(R.id.notes)
        notes.text=clock.notes


    }
}