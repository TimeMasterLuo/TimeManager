package com.example.timemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.asp.fliptimerviewlibrary.CountDownClock
import kotlinx.android.synthetic.main.activity_away_phone_timer.*


class AwayPhoneTimer : AppCompatActivity() {
    private var isRunning: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_away_phone_timer)

        val typeface = ResourcesCompat.getFont(this, R.font.roboto_bold)
        timerProgramCountdown.setCustomTypeface(typeface!!)
        timerProgramCountdown.startCountDown(99999999)
        timerProgramCountdown.setCountdownListener(object : CountDownClock.CountdownCallBack {
            override fun countdownAboutToFinish() {
                //TODO Add your code here
            }

            override fun countdownFinished() {
                Toast.makeText(this@AwayPhoneTimer, "Finished", Toast.LENGTH_SHORT).show()
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
    }
}