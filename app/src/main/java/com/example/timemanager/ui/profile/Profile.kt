package com.example.timemanager.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.awayphone.AwayPhone
import kotlinx.android.synthetic.main.activity_profile.*
import com.example.timemanager.ui.title.ButtonBackward
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.layout_title.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_profile)

        //对相应组件传入对应的用户info
        username_profile_text.text=(this.application as TimeManager).username
        myIntro.text="个人介绍："+(this.application as TimeManager).intro
        myGender.text=(this.application as TimeManager).gender
        myLevel.text=(this.application as TimeManager).userLevel
        myRegisterTime.text="注册时间："+(this.application as TimeManager).register_time
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "个人主页"
    }

    override fun onResume() {
        super.onResume()
        //对相应组件传入对应的用户info
        username_profile_text.text=(this.application as TimeManager).username
        myIntro.text="个人介绍："+(this.application as TimeManager).intro
        myGender.text=(this.application as TimeManager).gender
        myLevel.text=(this.application as TimeManager).userLevel
        myRegisterTime.text="注册时间："+(this.application as TimeManager).register_time
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()
    }

    fun editIntroOnclick(view: View){
        val intent = Intent(this, EditIntro::class.java).apply {
        }
        startActivity(intent)
    }
}