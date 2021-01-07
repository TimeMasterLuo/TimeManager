package com.example.timemanager.ui.friendlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.blankj.utilcode.util.ToastUtils
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.components.CircleImageView
import com.example.timemanager.ui.login.LoggedInUserView
import com.example.timemanager.ui.login.LoginResult
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.networkRequest.MySingleton
import kotlinx.android.synthetic.main.activity_addfriend_profile.*
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONArray
import org.json.JSONObject

class AddFriendProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_addfriend_profile)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "好友主页"

        //friendData
        val avatar = findViewById<CircleImageView>(R.id.friendAvatar)
        val name = findViewById<TextView>(R.id.friendName)
        val level = findViewById<TextView>(R.id.friendLevel)
        val gender = findViewById<Button>(R.id.friendGender)
        val intro = findViewById<Button>(R.id.friendIntro)
        val registerTime = findViewById<TextView>(R.id.friend_register_time)
        val globalData: TimeManager = application as TimeManager
        addFriendButton.setOnClickListener {
            val url = "http://59.78.38.19:8080/addFriend"
            //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
            val params = JSONObject("""{"Sender":${globalData.username},"Receiver":${name.text}}""")
            //发送请求
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, params,
                //成功获取返回时的callback
                { response ->
                    //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                    if (response.get("title").toString() != "") {
                        ToastUtils.make().show("请求已发送，请等待对方确认！");//"请求已发送，请等待对方确认！"
                        finish();
                    } else {
                        ToastUtils.make().show("请求发送失败！");
                    }
                },
                //失败情况调用的callback
                { error ->
                    // TODO: Handle error
                    ToastUtils.make().show("请求发送失败，请检查网络是否异常！");
                }
            )
            // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }
        // TODO: 2020/11/17 other data

        val intent:Intent = intent
        var msg:String = intent.getStringExtra("friendChoosed");
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        // can be launched in a separate asynchronous job
        val url2 = "http://59.78.38.19:8080/getuser"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":${msg}}""")
        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.get("id") != -1) {
                    try {
                        val jsonObject = JSONObject(response.toString())
                        name.text = jsonObject.optString("name", null)
                        registerTime.text = "注册时间:   "+jsonObject.optString("logintime", null)
                        intro.text = jsonObject.optString("intro", null)
                        gender.text = jsonObject.optString("gender", null)
                        level.text = jsonObject.optString("level", null)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                } else {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        )
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}