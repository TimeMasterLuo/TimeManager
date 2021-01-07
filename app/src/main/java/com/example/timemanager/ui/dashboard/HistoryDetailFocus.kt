package com.example.timemanager.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.ui.home.Home
import com.example.timemanager.ui.home.HomeFragment
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.clock.Clock
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.layout_title.*
import org.jetbrains.anko.alert
import org.json.JSONObject
import java.text.SimpleDateFormat

class HistoryDetailFocus : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_history_detail_focus)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        text_title.text = "详情"
        button_backward.setOnClickListener(ButtonBackward(this))
        val clock: Clock = intent.getSerializableExtra("clock") as Clock
        val format = SimpleDateFormat("yyyy-MM-dd")
        val format1 = SimpleDateFormat("HH:mm")
        val date=format.format(clock.start_time)
        val date1=format1.format(clock.start_time)
        val date2=format1.format(clock.end_time)
        val type=findViewById<TextView>(R.id.typeText)
        if (clock.type=="normal") {
            type.text = "普通模式"
        }else{
            type.text = "专注模式"
        }
        val user=findViewById<TextView>(R.id.set_user)
        user.text=clock.user
        val dating=findViewById<TextView>(R.id.date)
        dating.text=date
        val time=findViewById<TextView>(R.id.clock_time)
        time.text=date1
        val finishTime=findViewById<TextView>(R.id.time_to_finish)
        finishTime.text=date2
        val lastTime=findViewById<TextView>(R.id.last_time)
        lastTime.text=clock.last_time+"s"
        val coins=findViewById<TextView>(R.id.coins)
        coins.text= clock.coins.toString()+"金币"
        val btn: Button =findViewById(R.id.button_focus)
        val context=this
        btn.setOnClickListener {
            alert ("确认删除此条记录吗"){
                positiveButton("取消"){
                }
                negativeButton("删除"){
                    val param= JSONObject()
                    param.put("id",clock.id)
                    param.put("type","focus")
                    val url="http://59.78.38.19:8080/deleteRecord"
                    val jsonObjectRequest = JsonObjectRequest(
                            Request.Method.POST, url, param,
                            //成功获取返回时的callback
                            { response ->
                                Log.e("response success", response.toString())
                                val intents=Intent()
                                intents.setClass(this@HistoryDetailFocus,Home::class.java)
                                startActivity(intents)
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