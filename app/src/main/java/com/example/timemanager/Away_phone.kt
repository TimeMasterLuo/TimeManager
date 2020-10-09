package com.example.timemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog

class Away_phone : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_away_phone)
        AlertDialog.Builder(this).apply {
            setTitle("Tips:")
            setMessage("普通模式下您仍可以使用部分软件，你可以在设置中进一步设置白名单，在专注模式下绝大部分功能将被禁用，切换屏幕会导致计时终止。")
            setCancelable(false)
            setPositiveButton("OK"){dialog, which ->  }
            show()
        }
    }
}