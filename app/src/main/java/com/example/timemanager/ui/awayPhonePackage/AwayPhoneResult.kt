package com.example.timemanager.ui.awayPhonePackage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ServiceUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.timemanager.ui.home.Home
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_AWAY_PHONE
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.activity_away_phone_result.*
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONObject
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

        testHttpbtn.setOnClickListener {
            testHttp()
        }
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

    private fun testHttp(){
        val url2 = "http://47.112.132.142:8088/loginmessage"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":"Youyu", "password":"123"}""")

        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2 , params,
            //成功获取返回时的callback
            { response ->

                ToastUtils.make().show(response.toString())
                Log.e("response", response.toString())

            },
            //失败情况调用的callback
            { error ->

                ToastUtils.make().show(error.toString())
                Log.e("error", error.toString())

            }
        )

        // Access the RequestQueue through your singleton class.
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}