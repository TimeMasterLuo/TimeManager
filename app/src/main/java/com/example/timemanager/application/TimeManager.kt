package com.example.timemanager.application

import android.app.Application
import org.xutils.x
import kotlin.properties.Delegates

//app开始运行时初始化，可以用来放全局变量等信息

class TimeManager : Application() {
    //这里存放所有全局变量，kotlin自带get，set方法
    var login_flag = false
    var username = "YueYi"
    var gender = "男"
    var userLevel = "资深用户"
    var register_time = "2020-10-01"
    var intro = "这个人很懒，什么也没写。"
    var uid = "1"
    var email = "1481796592@qq.com"
    var alarmCount = 0 //用于闹钟设置

    override fun onCreate() {
        login_flag = true
        super.onCreate()
        instance = this;
        x.Ext.init(instance);
        x.Ext.setDebug(false);
    }

    companion object {
        private var instance : TimeManager by Delegates.notNull();
        fun instance() = instance;
    }
}