package com.example.timemanager.utils.LocalDataBase

enum class Tables(val tablepath:String){
    //闹钟表
    T_ALARM_CLOCK("com.example.timemanager.utils.LocalDataBase.T_ALARM_CLOCK"),

    //白名单表
    T_WHITELIST("com.example.timemanager.utils.LocalDataBase.T_WHITELIST"),

    //远离手机历史记录
    T_AWAY_PHONE("com.example.timemanager.utils.LocalDataBase.T_AWAY_PHONE")
}