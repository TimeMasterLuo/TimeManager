package com.example.timemanager.ui.awayPhonePackage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.blankj.utilcode.util.*
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.home.Home
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_AWAY_PHONE
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.activity_away_phone_result.*
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONObject
import kotlin.math.roundToInt


class AwayPhoneResult : AppCompatActivity() {
    private var gettime : String?= "0"
    private var getstartDate: String? = null
    private var getendDate: String? = null
    private var model: String? = "normal"
    private var id = "0"
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
        getcoins.text = "恭喜您获得${getCoins(gettime.toString())} 枚金币！请再接再厉哦！"
        getcoins2.text = "获得金币：${getCoins(gettime.toString())}"

        getstartDate = intent.getStringExtra("startTime")
        getendDate = intent.getStringExtra("endTime")
        startTime.text = "开始时间：$getstartDate"
        endTime.text = "结束时间：$getendDate"

        val hour = TimeUtils.getSafeDateFormat("HH:mm")
        val data = TimeUtils.getSafeDateFormat("MM-dd")

        timeRange.text = "${hour.format(TimeUtils.string2Date(getstartDate))} ~ ${hour.format(TimeUtils.string2Date(getendDate))}"
        time.text = "$gettime s"

        model = intent.getStringExtra("model")
        if(model == "normal"){
            modelname.setImageDrawable(ResourceUtils.getDrawable(ResourceUtils.getIdByName("@drawable/artwords_normal")))
        }

        else{
            modelname.setImageDrawable(ResourceUtils.getDrawable(ResourceUtils.getIdByName("@drawable/artwords_focus")))
        }

        addAwayPhoneHistoryinServer()
        //Log.e("test", TimeUtils.string2Date(getstartDate).toString())
        //addAwayPhoneHistoryinLocal()
    }

    fun close(view: View?) {
        ServiceUtils.stopService(AwayPhoneService::class.java)
        ActivityUtils.finishToActivity(Home::class.java, false, true)
        //finish()
    }

    private fun addAwayPhoneHistoryinLocal(id: String) {
        val globalData: TimeManager = application as TimeManager
        val awayPhoneHistory = T_AWAY_PHONE()
        awayPhoneHistory.ID = id
        awayPhoneHistory.STARTDATE = getstartDate.toString()
        awayPhoneHistory.ENDDATE = getendDate.toString()
        awayPhoneHistory.TIME = gettime.toString()
        awayPhoneHistory.COINS = getCoins(gettime.toString())
        awayPhoneHistory.USERNAME = globalData.username
        awayPhoneHistory.USERID = globalData.uid
        awayPhoneHistory.TYPE = model.toString()
        DbTool.saveOrUpdate(awayPhoneHistory)
    }

    private fun getCoins(time: String): Int {
        return time.toFloat().roundToInt()
    }

    private fun getId(): String {
        return getstartDate.toString() + gettime.toString()
    }

    private fun addAwayPhoneHistoryinServer() {
        val globalData: TimeManager = application as TimeManager
        val data = JSONObject()
        val url = "http://139.196.200.26:8080/addFocus"
        //data.put("id", getId())
        data.put("startDate", getstartDate.toString())
        data.put("endDate", getendDate.toString())
        data.put("time", gettime.toString())
        data.put("coins", getCoins(gettime.toString()))
        data.put("username", globalData.username)
        data.put("userid", globalData.uid.toInt())
        data.put("type", model.toString())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url , data,
            //成功获取返回时的callback
            { response ->

                //ToastUtils.make().show(response.toString())
                id = response.get("message").toString()
                addAwayPhoneHistoryinLocal(id)
                Log.e("response", response.get("message").toString())

            },
            //失败情况调用的callback
            { error ->

                //ToastUtils.make().show(error.toString())
                Log.e("error", error.toString())

            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun testHttp(){
        val url2 = "http://47.112.132.142:8088/loginmessage"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val data = JSONObject()
        data.put("username","Youyu")
        data.put("password", "123")
        //val params = JSONObject("""{"username":"Youyu", "password":"123"}""")


        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2 , data,
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