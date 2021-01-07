package com.example.timemanager.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.clock.Clock
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.activity_history_detail.*
import kotlinx.android.synthetic.main.layout_title.*
import java.text.SimpleDateFormat
import org.jetbrains.anko.alert
import org.json.JSONObject
import java.util.*

class HistoryDetail : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_history_detail)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        text_title.text = "详情"
        button_backward.setOnClickListener(ButtonBackward(this))

        val clock: Clock = intent.getSerializableExtra("clock") as Clock
        Log.e("current time",clock.start_time.toString())
        val calender=Calendar.getInstance()
        calender.time=clock.start_time
        calender.add(Calendar.MONTH,3)
        Log.e("after time",calender.time.toString())
        val format = SimpleDateFormat("yyyy-MM-dd")
        val format1 = SimpleDateFormat("HH:mm")
        val date=format.format(clock.start_time)
        val date1=format1.format(clock.start_time)
        val type=findViewById<TextView>(R.id.typeText)
        type.text=clock.type
        val user=findViewById<TextView>(R.id.set_user)
        user.text=clock.set_person
        val dating=findViewById<TextView>(R.id.date)
        dating.text=date
        val time=findViewById<TextView>(R.id.clock_time)
        time.text=date1
        val finishTime=findViewById<TextView>(R.id.time_to_finish)
        finishTime.text=clock.status
        val coins=findViewById<TextView>(R.id.coins)
        coins.text= clock.coins.toString()+"金币"
        val grade=findViewById<TextView>(R.id.grade)
        grade.text=clock.grade
        val notes=findViewById<TextView>(R.id.notes)
        notes.text=clock.notes
        val task=findViewById<TextView>(R.id.task)
        task.text=clock.task
        val btn:Button=findViewById(R.id.button_clock)
        val context=this
        btn.setOnClickListener {
            alert ("确认删除此条记录吗"){
                positiveButton("取消"){}
                negativeButton("删除"){
                    val param=JSONObject()
                    param.put("id",clock.id)
                    param.put("type","clock")
                    val url="http://59.78.38.19:8080/deleteRecord"
                    val jsonObjectRequest = JsonObjectRequest(
                            Request.Method.POST, url, param,
                            //成功获取返回时的callback
                            { response ->
                                Log.e("response error", response.toString())
                                ButtonBackward(context)
                            },
                            //失败情况调用的callback
                            { error ->
                                Log.e("response error", error.toString())
                            }
                    )
                    MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
                }
            }.show()
        }
    }
}