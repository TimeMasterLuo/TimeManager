package com.example.timemanager.ui.profile

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timemanager.*
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.login.Login
import com.example.timemanager.ui.register.Register
import com.example.timemanager.ui.systemconfig.SystemConfig
import com.example.timemanager.ui.themestore.ThemeStore

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
        var root = inflater.inflate(R.layout.fragment_profile_unauthorized, container, false)
        //获取全局变量flag
        var flag: Boolean = (activity!!.application as TimeManager).login_flag

        if (flag) {
            //渲染fragment_profile_authorized并绑定监听
            root = inflater.inflate(R.layout.fragment_profile_authorized, container, false)

            //对相应组件传入对应的用户info
            val usernameShow : TextView = root.findViewById(R.id.username_text)
            usernameShow.text=(activity!!.application as TimeManager).username

            val userIntro : TextView = root.findViewById(R.id.intro_text)
            userIntro.text=(activity!!.application as TimeManager).intro

            val btn1 : Button = root.findViewById(R.id.config_button)
            btn1.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), SystemConfig::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
            val ssb_btn2 : Button = root.findViewById(R.id.subscribe_button)
            ssb_btn2.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    // TODO: 2020/11/14
                    val alertDialog2: android.app.AlertDialog? = android.app.AlertDialog.Builder(getActivity())
                        .setTitle("订阅功能")
                        .setMessage("功能开发中，敬请期待。 \n")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                            //添加"Yes"按钮
                            Toast.makeText(getActivity(), "感谢您的支持", Toast.LENGTH_SHORT).show()
                        })
                        .create()
                    if (alertDialog2 != null) {
                        alertDialog2.show()
                    }
                }
            })
            val wallet_btn : Button = root.findViewById(R.id.wallet_button)
            wallet_btn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    // TODO: 2020/11/14
                    val alertDialog2: android.app.AlertDialog? = android.app.AlertDialog.Builder(getActivity())
                        .setTitle("余额查看")
                        .setMessage("功能开发中，敬请期待。 \n")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                            //添加"Yes"按钮
                            Toast.makeText(getActivity(), "感谢您的支持", Toast.LENGTH_SHORT).show()
                        })
                        .create()
                    if (alertDialog2 != null) {
                        alertDialog2.show()
                    }
                }
            })
            val staff_btn : Button = root.findViewById(R.id.staff_button)
            staff_btn.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    // TODO: 2020/11/14
                    val alertDialog2: android.app.AlertDialog? = android.app.AlertDialog.Builder(getActivity())
                        .setTitle("客服咨询")
                        .setMessage("功能开发中，敬请期待。 \n")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                            //添加"Yes"按钮
                            Toast.makeText(getActivity(), "感谢您的支持", Toast.LENGTH_SHORT).show()
                        })
                        .create()
                    if (alertDialog2 != null) {
                        alertDialog2.show()
                    }
                }
            })
            val btn3 : Button = root.findViewById(R.id.profile_entry)
            btn3.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), Profile::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
            val btn4 : Button = root.findViewById(R.id.themeStore_button)
            btn4.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    // TODO: 2020/11/14
                    val alertDialog2: android.app.AlertDialog? = android.app.AlertDialog.Builder(getActivity())
                        .setTitle("更换主题")
                        .setMessage("功能维护中，还请谅解。 \n")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                            //添加"Yes"按钮
                            Toast.makeText(getActivity(), "感谢您的支持", Toast.LENGTH_SHORT).show()
                        })
                        .create()
                    if (alertDialog2 != null) {
                        alertDialog2.show()
                    }
//                    val intent = Intent(getActivity(), ThemeStore::class.java).apply {
//                    }
//                    startActivity(intent)
                }
            })
        }else{
            ////渲染fragment_profile_unauthorized并绑定监听
            val btn1 : Button = root.findViewById(R.id.login_button)
            btn1.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), Login::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
            val btn2 : Button = root.findViewById(R.id.register_button)
            btn2.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val intent = Intent(getActivity(), Register::class.java).apply {
                    }
                    startActivity(intent)
                }
            })
        }
        return root
    }

}


