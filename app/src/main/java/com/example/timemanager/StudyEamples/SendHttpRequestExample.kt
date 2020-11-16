package com.example.timemanager.StudyEamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timemanager.R
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.*
import com.example.timemanager.utils.networkRequest.MySingleton
import org.json.JSONObject


class SendHttpRequestExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_http_request_example)
        val textView = findViewById<TextView>(R.id.text)
        val log = findViewById<TextView>(R.id.log)
        val requestcontent = findViewById<TextView>(R.id.request)

        //定义URL连接
        val url = "https://www.baidu.com"
        val url2 = "http://59.78.38.19:8080/login"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":"mike name222222", "password":"123456"}""")

        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2, params,
            //成功获取返回时的callback
            { response ->
                textView.text = "Response: %s${response}"
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT)
                    .show();
                log.text = "Response: %s".format(error.toString())
            }
        )

        // Access the RequestQueue through your singleton class.
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        //requestcontent是页面的一个edittext的指针，它将显示你request的返回消息
        requestcontent.text=jsonObjectRequest.toString()

        // ...

    }
    fun onclick(view: View){
        val textView = findViewById<TextView>(R.id.text)
        val log = findViewById<TextView>(R.id.log)
        val requestcontent = findViewById<TextView>(R.id.request)
        //定义URL连接
        val url2 = "http://59.78.38.19:8080/login"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        val params = JSONObject("""{"username":"mike name222222", "password":"123456"}""")

        //发送请求
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url2 , params,
            //成功获取返回时的callback
            { response ->
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT)
                    .show();
                textView.text = "Response: %s${response}"
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT)
                    .show();
                log.text = "error: %s".format(error.toString())
            }
        )

        // Access the RequestQueue through your singleton class.
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

        //requestcontent是页面的一个edittext的指针，它将显示你request的返回消息
        requestcontent.text=jsonObjectRequest.toString()

        // ...
    }
}