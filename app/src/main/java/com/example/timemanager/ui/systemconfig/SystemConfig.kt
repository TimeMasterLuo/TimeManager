package com.example.timemanager.ui.systemconfig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.timemanager.FriendProfile
import com.example.timemanager.GlobalData
import com.example.timemanager.Home
import com.example.timemanager.R

class SystemConfig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_config)
    }
    fun Logout(view: View){
        //设置全局数据，记入登录状态
        val globalData: GlobalData = application as GlobalData
        globalData.login_flag=false

        val intent = Intent(this, Home::class.java).apply {
        }
        startActivity(intent)
    }
}