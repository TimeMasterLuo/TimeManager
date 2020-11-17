package com.example.timemanager.ui.friendlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.adapter.FriendListAdapter.ListItem
import com.example.timemanager.adapter.FriendListAdapter.MyAdapter
import com.example.timemanager.ui.systemconfig.SystemConfig
import kotlinx.android.synthetic.main.activity_send_http_request_example.view.*


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
}