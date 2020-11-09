package com.example.timemanager.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timemanager.*
import com.example.timemanager.ui.login.Login

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        //条件渲染：根据全局的登入判断符来选择应该渲染的fragment
        //默认为未登入
        var root = inflater.inflate(R.layout.fragment_setting_unauthorized, container, false)
        //获取全局变量flag
        var flag: Boolean = (activity!!.application as GlobalData).login_flag

        if (flag) {
            //渲染fragment_setting_authorized并绑定监听
            root = inflater.inflate(R.layout.fragment_setting_authorized, container, false)
            val btn1 : Button = root.findViewById(R.id.logout_button)
            btn1.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), Login::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
            val btn2 : Button = root.findViewById(R.id.friend_button)
            btn2.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), FriendList::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
            val btn3 : Button = root.findViewById(R.id.profile_entry)
            btn3.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), Profile::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
            val btn4 : Button = root.findViewById(R.id.themeStore_button)
            btn4.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), ThemeStore::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
        }else{
            ////渲染fragment_setting_unauthorized并绑定监听
            val btn1 : Button = root.findViewById(R.id.login_button)
            btn1.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), Login::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
        }
        return root
    }
}