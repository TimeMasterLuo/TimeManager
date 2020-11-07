package com.example.timemanager.AwayPhonePackage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.timemanager.R
import kotlinx.android.synthetic.main.activity_away_phone_result.*


class AwayPhoneResult : AppCompatActivity() {
    private var gettime : String?= "0"
    private var r : String?= "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_away_phone_result)
        gettime = intent.getStringExtra("time")
        gettime += "s"
        time.text = gettime

        r = intent.getStringExtra("alarm")
    }

    fun close(view: View?) {
        finish()
    }
}