package com.example.timemanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.asp.fliptimerviewlibrary.CountDownClock
import com.example.timemanager.AwayPhonePackage.AwayPhoneService
import kotlinx.android.synthetic.main.activity_away_phone_timer.*


class AwayPhoneController : AppCompatActivity() {
    private var isRunning: Boolean = false
    private var model: String? = "normal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_away_phone_timer)
        model = intent.getStringExtra("model")

        val typeface = ResourcesCompat.getFont(this, R.font.roboto_bold)
        timerProgramCountdown.setCustomTypeface(typeface!!)
        timerProgramCountdown.startCountDown(99999999)
        timerProgramCountdown.setCountdownListener(object : CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                //TODO Add your code here
            }

            override fun countdownFinished() {
                Toast.makeText(this@AwayPhoneController, "Finished", Toast.LENGTH_SHORT).show()
                timerProgramCountdown.resetCountdownTimer()
                isRunning = false
                btnPause.isEnabled = false
            }
        })


        btnPause.setOnClickListener {

            if(isRunning){
                isRunning = false
                timerProgramCountdown.pauseCountDownTimer()
                btnPause.text = getString(R.string.resume_timer)
            }else{
                isRunning = true
                timerProgramCountdown.resumeCountDownTimer()
                btnPause.text = getString(R.string.pause_timer)
            }
        }

        start.setOnClickListener {
            val intent = Intent(this@AwayPhoneController, AwayPhoneService::class.java).apply {  }
            intent.putExtra("model", model)
            startService(intent)
            Toast.makeText(this, "服务已开启！", Toast.LENGTH_SHORT).show()
        }

        stop.setOnClickListener {
            stopService(Intent(this@AwayPhoneController, AwayPhoneService::class.java))
            Toast.makeText(this, "服务已关闭！", Toast.LENGTH_SHORT).show()
        }
    }

    fun openService(view: View?) {
        startService(Intent(this@AwayPhoneController, AwayPhoneService::class.java))
        Toast.makeText(this, "服务已开启！", Toast.LENGTH_SHORT).show()
    }

    fun closeService(view: View?) {
        stopService(Intent(this@AwayPhoneController, AwayPhoneService::class.java))
        Toast.makeText(this, "服务已关闭！", Toast.LENGTH_SHORT).show()
    }

}