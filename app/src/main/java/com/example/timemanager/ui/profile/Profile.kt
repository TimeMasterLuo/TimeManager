package com.example.timemanager.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //对相应组件传入对应的用户info
        username_profile_text.text=(this.application as TimeManager).username
        myIntro.text="个人介绍："+(this.application as TimeManager).intro
        myGender.text=(this.application as TimeManager).gender
        myLevel.text=(this.application as TimeManager).userLevel
        myRegisterTime.text="注册时间："+(this.application as TimeManager).register_time
    }
}