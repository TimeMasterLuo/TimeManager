package com.example.timemanager.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.home.Home
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.activity_edit_intro.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONObject


class EditIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_edit_intro)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "修改信息"

        newIntro.setText((this.application as TimeManager).intro)
    }
    fun confirmOnclick(view: View){
        val globalData: TimeManager = application as TimeManager
        val newIntro1 = findViewById<View>(R.id.newIntro) as EditText
        val textContent = newIntro1.text.toString()
        val url2 = "http://59.78.38.19:8080/editIntro"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":${globalData.username},"newIntro":${textContent}}""")
        //Toast.makeText(this, params.toString(), Toast.LENGTH_SHORT).show();
        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                if (response.get("message") == "success") {
                    // TODO: 2020/11/17  success
                    globalData.intro=textContent
                    //Complete and destroy login activity once successful
                    setResult(Activity.RESULT_OK)
                    finish()

                } else {
                    // TODO: 2020/11/17 fail
                    Toast.makeText(this, "修改失败，请重试。", Toast.LENGTH_SHORT).show();
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        )
         //下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}