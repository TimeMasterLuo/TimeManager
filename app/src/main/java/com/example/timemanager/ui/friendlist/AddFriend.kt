package com.example.timemanager.ui.friendlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONObject

class AddFriend : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_add_friend)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "添加好友"
    }
    fun confirmAddOnclick(view: View){
        val globalData: TimeManager = application as TimeManager
        val searchName = findViewById<View>(R.id.searchName) as EditText
        val textContent = searchName.text.toString()
        val url2 = "http://59.78.38.19:8080/addfriend"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":${globalData.username},"friendname":${textContent}}""")
        //Toast.makeText(this, params.toString(), Toast.LENGTH_SHORT).show();
        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                Toast.makeText(this, "请求已发送。", Toast.LENGTH_SHORT).show();
                if (response.get("message") == "success") {
                    // TODO: 2020/11/17  success
                    //Complete and destroy login activity once successful
                    setResult(RESULT_OK)
                    finish()

                } else {
                    // TODO: 2020/11/17 fail
                    Toast.makeText(this, "该用户不存在。", Toast.LENGTH_SHORT).show();
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(this,"连接超时", Toast.LENGTH_SHORT).show();
            }
        )
        //下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}