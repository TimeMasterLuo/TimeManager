package com.example.timemanager.adapter.messageAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.blankj.utilcode.util.ToastUtils
import com.example.timemanager.R
import com.example.timemanager.adapter.MyBaseAdapter
import com.example.timemanager.utils.networkRequest.MySingleton
import org.json.JSONObject

class addMessageAdapter(
    context: Context?,
    list: List<addFriendMessage?>?
) : MyBaseAdapter<addFriendMessage?>(context, list) {

    override fun getView(
        position: Int,
        myconvertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = myconvertView
        if (convertView == null) {
         convertView =
                LayoutInflater.from(mContext).inflate(R.layout.adapter_addmessage_item, null)
        }
        val message = mList[position]!!
        if (message.type == "好友请求") {
            val addAvatar =
                convertView?.findViewById<View>(R.id.appIcon) as ImageView?
            val addUsername =
                convertView?.findViewById<View>(R.id.addUsername) as TextView?
            val addIntro =
                convertView?.findViewById<View>(R.id.addIntro) as TextView?
            val addReject =
                convertView?.findViewById<View>(R.id.addReject) as ImageButton?
            val addAgree =
                convertView?.findViewById<View>(R.id.addAgree) as ImageButton?
            val addResult =
                convertView?.findViewById<View>(R.id.addResult) as TextView?
            if (addReject != null && addAgree != null && addResult != null) {
                addResult.visibility = View.GONE
                addReject.setOnClickListener {
                    sendResult(message.id, 2)
                    addReject.visibility = View.GONE
                    addAgree.visibility = View.GONE
                    addResult.visibility = View.VISIBLE
                    addResult.text = "已拒绝"
                }
                addAgree.setOnClickListener {
                    sendResult(message.id, 1)
                    addReject.visibility = View.GONE
                    addAgree.visibility = View.GONE
                    addResult.visibility = View.VISIBLE
                    addResult.text = "已同意"
                    //updateFriendlist()
                }

            }
            if (addUsername != null) {
                addUsername.text = message.sender
            }
            val taskClockMessage =
                convertView?.findViewById<View>(R.id.taskClockMessage) as CardView
            taskClockMessage.visibility = View.GONE
        }
        return convertView
    }

    private fun sendResult(id: String, result: Int) {
        val url = "http://59.78.38.19:8080/handleAddFriend"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        //val params = JSONArray("""[{"Username":${globalData.username}}]""")
        val params = JSONObject("""{"MessageId":${id},"Result":${result}}""")
        //发送请求


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, params,
            //成功获取返回时的callback
            { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                //Log.e("mytest??", response.toString())
                if (response.getString("title") != "") {
                    ToastUtils.make().show(response.getString("message"))
                } else {
                    ToastUtils.make().show("请求发送失败！");
                }
            },
            //失败情况调用的callback
            { error ->
                // TODO: Handle error
                Log.e("request", params.toString())
                Log.e("myerror", error.toString())
                ToastUtils.make().show("请求发送失败，请检查网络是否异常！");
            }
        )
        // 下面这行意思是将你的request加入到android维护的一个线程队列中，这个队列会依据其自带逻辑处理你的request
        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest)
    }


}