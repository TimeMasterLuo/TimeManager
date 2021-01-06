package com.example.timemanager.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.blankj.utilcode.util.ToastUtils
import com.example.timemanager.R
import com.example.timemanager.adapter.awayPhoneAdapter.WhitelistAdapter
import com.example.timemanager.adapter.messageAdapter.addFriendMessage
import com.example.timemanager.adapter.messageAdapter.addMessageAdapter
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.networkRequest.MySingleton
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_message_view.*
import kotlinx.android.synthetic.main.layout_title.*
import org.json.JSONArray
import org.json.JSONObject

class MessageView : AppCompatActivity() {
    private var addFriendMessageList: MutableList<addFriendMessage> = arrayListOf()
    var addFriendMessageAdapter: addMessageAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_message_view)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)

        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "消息"
        getAddFriendMessage()

    }

    private fun getAddFriendMessage()
    {
        val globalData: TimeManager = application as TimeManager
        val url = "http://59.78.38.19:8080/getAddMsg"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        //val params = JSONArray("""[{"Username":${globalData.username}}]""")
        val params = JSONObject("""{"Username":${globalData.username}}""")
        //发送请求


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, params,
            //成功获取返回时的callback
            { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                Log.e("mytest??", response.toString())
                if(response.getJSONArray("data").length() == 0) return@JsonObjectRequest
                else {
                    for(i in 0 until response.getJSONArray("data").length())
                    {
                        val myresponse = response.getJSONArray("data").getJSONObject(i)
                        val message: addFriendMessage = addFriendMessage()
                        message.id = myresponse.getString("id")
                        message.sender = myresponse.getString("sender")
                        message.receiver = myresponse.getString("receiver")
                        message.status = "待处理"
                        message.type = "好友请求"
                        addFriendMessageList.add(i, message)
                    }
                    addFriendMessageAdapter = addMessageAdapter(this, addFriendMessageList)
                    addFriendList.adapter = addFriendMessageAdapter
                }

            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Log.e("myerror", error.toString())
                ToastUtils.make().show("请求发送失败，请检查网络是否异常！");
            }
        )
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}

//val jsonArrayRequest = JsonArrayRequest(
//    Request.Method.POST, url, params,
//    //成功获取返回时的callback
//    { response ->
//        //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
//        Log.e("mytest??", response.toString())
//        if (response.length() != 0) {
//            for(i in 0 .. response.length()-1)
//            {
//                val myresponse = response.getJSONObject(i)
//                addFriendMessageList[i].id = myresponse.getString("id")
//                addFriendMessageList[i].sender = myresponse.getString("sender")
//                addFriendMessageList[i].receiver = myresponse.getString("receiver")
//                addFriendMessageList[i].status = "待处理"
//                addFriendMessageList[i].type = "好友请求"
//            }
//        } else {
//            ToastUtils.make().show("请求发送失败！");
//        }
//    },
//    //失败情况调用的callback
//    { error ->
//        // TODO: Handle error
//        Log.e("myerror", error.toString())
//        ToastUtils.make().show("请求发送失败，请检查网络是否异常！");
//    }
//)
