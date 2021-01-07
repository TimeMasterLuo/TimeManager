package com.example.timemanager.ui.systemconfig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.Window
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.home.Home
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import kotlinx.android.synthetic.main.layout_title.*

class SystemConfig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_system_config)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "设置"
    }
    fun Logout(view: View){
        //设置全局数据，记入登录状态
        val globalData: TimeManager = application as TimeManager
        globalData.friendlist.clear()
        globalData.login_flag=false

        val intent = Intent(this, Home::class.java).apply {
            //清空之前堆叠的栈
            Toast.makeText(applicationContext, "登出成功", Toast.LENGTH_SHORT).show()
            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }
    fun resetPassword(view: View){
        //设置全局数据，记入登录状态
        val intent = Intent(this, ResetPassword::class.java).apply {
        }
        startActivity(intent)
    }
}