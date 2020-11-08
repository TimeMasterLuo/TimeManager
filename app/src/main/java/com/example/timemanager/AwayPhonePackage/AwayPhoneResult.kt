package com.example.timemanager.AwayPhonePackage

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ServiceUtils
import com.example.timemanager.Home
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import kotlinx.android.synthetic.main.activity_away_phone_result.*
import kotlinx.android.synthetic.main.layout_title.*


class AwayPhoneResult : AppCompatActivity() {
    private var gettime : String?= "0"
    private var myService: AwayPhoneService? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_away_phone_result)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)

        this.supportActionBar?.hide()

        button_backward.visibility = View.INVISIBLE
        text_title.text = "远离手机：结果报告"

        folding_cell.setOnClickListener {
            folding_cell.toggle(false)
        }

        gettime = intent.getStringExtra("time")
        gettime += "s"
        totalTime.text = "持续时间："+gettime
        totalTime2.text = "持续时间："+gettime

        var getstartTime = intent.getStringExtra("startTime")
        var getendTime = intent.getStringExtra("endTime")
        startTime.text = "开始时间："+getstartTime
        endTime.text = "结束时间："+getendTime

    }

    fun close(view: View?) {
        ServiceUtils.stopService(AwayPhoneService::class.java)
        ActivityUtils.finishToActivity(Home::class.java, false, true)
    }
}