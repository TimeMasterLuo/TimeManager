package com.example.timemanager.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import kotlinx.android.synthetic.main.layout_title.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_profile)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "个人主页"
    }
}