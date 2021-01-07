package com.example.timemanager.ui.systemconfig

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.home.Home
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK
import com.example.timemanager.utils.tools.AlarmTools
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

        //清空之前账户的闹钟数据
        var localalarm = DbTool.findAll(T_ALARM_CLOCK::class.java);
        localalarm?.forEach{ item->
            DbTool.delete(item)
            AlarmTools.cancelAlarm(this, item)
        }

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

    fun contractUs(view: View){
        //设置全局数据，记入登录状态

        val alertDialog2: android.app.AlertDialog? = android.app.AlertDialog.Builder(this)
            .setTitle("联系方式")
            .setMessage("邮箱：xxx@xxx.com \n开发交流群：54749110")
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                //添加"Yes"按钮
                Toast.makeText(this, "感谢您的支持", Toast.LENGTH_SHORT).show()
            })
            .create()
        if (alertDialog2 != null) {
            alertDialog2.show()
        }
    }

    fun versionInfo(view: View){
        //设置全局数据，记入登录状态

        val alertDialog2: android.app.AlertDialog? = android.app.AlertDialog.Builder(this)
            .setTitle("当前版本")
            .setMessage("开发测试beta2.0 \n")
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                //添加"Yes"按钮
                Toast.makeText(this, "感谢您的支持", Toast.LENGTH_SHORT).show()
            })
            .create()
        if (alertDialog2 != null) {
            alertDialog2.show()
        }
    }
}