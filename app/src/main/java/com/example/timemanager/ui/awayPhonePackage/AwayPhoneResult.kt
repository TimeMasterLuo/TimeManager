package com.example.timemanager.ui.awayPhonePackage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ServiceUtils
import com.example.timemanager.ui.home.Home
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_AWAY_PHONE
import kotlinx.android.synthetic.main.activity_away_phone_result.*
import kotlinx.android.synthetic.main.layout_title.*
import kotlin.math.roundToInt


class AwayPhoneResult : AppCompatActivity() {
    private var gettime : String?= "0"
    private var getstartTime: String? = null
    private var getendTime: String? = null
    private var model: String? = "normal"
    //private var myService: AwayPhoneService? = null
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
        totalTime.text = "持续时间：$gettime s"
        totalTime2.text = "持续时间：$gettime s"

        getstartTime = intent.getStringExtra("startTime")
        getendTime = intent.getStringExtra("endTime")
        startTime.text = "开始时间：$getstartTime"
        endTime.text = "结束时间：$getendTime"

        model = intent.getStringExtra("model")

        addAwayPhoneHistory()
    }

    fun close(view: View?) {
        ServiceUtils.stopService(AwayPhoneService::class.java)
        ActivityUtils.finishToActivity(Home::class.java, false, true)
    }

    private fun addAwayPhoneHistory() {
        val globalData: TimeManager = application as TimeManager
        val awayPhoneHistory = T_AWAY_PHONE()
        awayPhoneHistory.ID = getId()
        awayPhoneHistory.STARTDATE = getstartTime.toString()
        awayPhoneHistory.ENDDATE = getendTime.toString()
        awayPhoneHistory.TIME = gettime.toString()
        awayPhoneHistory.COINS = getCoins(gettime.toString())
        awayPhoneHistory.USERNAME = globalData.username
        awayPhoneHistory.USERID = globalData.uid
        awayPhoneHistory.MODEL = model.toString()
        DbTool.saveOrUpdate(awayPhoneHistory)
        Log.e("储存成功", "1")
    }

    private fun getCoins(time: String): Int {
        return time.toFloat().roundToInt()
    }

    private fun getId(): String {
        return getstartTime.toString() + gettime.toString()
    }
}