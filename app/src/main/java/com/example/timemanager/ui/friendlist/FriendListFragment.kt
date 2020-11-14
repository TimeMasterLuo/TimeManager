package com.example.timemanager.ui.friendlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.example.timemanager.*
import com.example.timemanager.application.TimeManager


class FriendListFragment : Fragment() {

    private lateinit var friendListViewModel:FriendListViewModel

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
            val btn1 : Button = root.findViewById(R.id.button71)
            btn1.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), FriendProfile::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
        }else{
            //渲染fragment_friendlist_unauthorized并绑定监听
            //TODO("绑定未登录状态下展示UI的监听")
        }
        return root
    }
}