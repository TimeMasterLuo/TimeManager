package com.example.timemanager.application

import android.app.Application
import org.xutils.x
import kotlin.properties.Delegates

//app开始运行时初始化，可以用来放全局变量等信息

class TimeManager : Application() {

    var login_flag = false

    override fun onCreate() {
        login_flag = false
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