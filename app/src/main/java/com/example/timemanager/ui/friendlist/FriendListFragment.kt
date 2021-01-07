package com.example.timemanager.ui.friendlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.blankj.utilcode.util.ToastUtils

import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.adapter.FriendListAdapter.ListItem
import com.example.timemanager.adapter.FriendListAdapter.MyAdapter
import com.example.timemanager.ui.systemconfig.SystemConfig
import com.example.timemanager.utils.networkRequest.MySingleton
import com.getbase.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_send_http_request_example.view.*
import org.json.JSONObject


class FriendListFragment : Fragment() {

    private lateinit var friendListViewModel:FriendListViewModel
    private val friends=ArrayList<ListItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        friendListViewModel =
            ViewModelProviders.of(this).get(FriendListViewModel::class.java)
        //条件渲染：根据全局的登入判断符来选择应该渲染的fragment
        //默认为未登入
        var root = inflater.inflate(R.layout.fragment_friendlist_unauthorized, container, false)
        //获取全局变量flag
        var flag: Boolean = (activity!!.application as TimeManager).login_flag

        if (flag) {
            //渲染fragment_friendlist_authorized并绑定监听
            root = inflater.inflate(R.layout.fragment_friendlist_authorized, container, false)

            //添加好友按钮事件绑定
            val addFriend : com.google.android.material.floatingactionbutton.FloatingActionButton = root.findViewById(R.id.addFriendButton)
            addFriend.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), AddFriend::class.java).apply {
                    }
                    startActivity(intent)
                }
            })

            //load friend list with the data from adapter
            initFriends() //初始化列表数据
            val adapter= MyAdapter(activity!!, R.layout.list_item, friends)
            val listview:ListView = root.findViewById(R.id.listview)
            listview.adapter=adapter
            listview.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
                //define onclick func
                val intent = Intent(getActivity(), FriendProfile::class.java).putExtra("friendChoosed",friends[i].name).apply {
                }
                startActivity(intent)
            })
        }else{
            //渲染fragment_friendlist_unauthorized并绑定监听
            //TODO("绑定未登录状态下展示UI的监听")
        }
        updateFriendlist()
        return root
    }

    private fun initFriends(){
        var friendArray = (activity!!.application as TimeManager).friendlist
//        Toast.makeText(activity, friendArray.toString(), Toast.LENGTH_SHORT).show()
        repeat(1){
            for (item in friendArray){
                friends.add(ListItem(item, R.drawable.avatar_1))
            }
        }
    }

    private fun updateFriendlist()
    {
        val globalData: TimeManager = this.activity?.application as TimeManager
        if(globalData.login_flag == false) return
        val url = "http://59.78.38.19:8080/getFriends"
        //定义发送的json数据，JSONObject初始化的其他方式还需自行探索
        //val params = JSONArray("""[{"Username":${globalData.username}}]""")
        val params = JSONObject("""{"username":${globalData.username}}""")
        //发送请求


        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, params,
            //成功获取返回时的callback
            { response ->
                if(response.getJSONArray("friend_names").length() == 0) return@JsonObjectRequest
                else {
                    globalData.friendlist.clear()
                    for(i in 0 until response.getJSONArray("friend_names").length())
                    {
                        globalData.friendlist.add(response.getJSONArray("friend_names").getString(i))
                    }

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
        this.activity?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }
    }


}