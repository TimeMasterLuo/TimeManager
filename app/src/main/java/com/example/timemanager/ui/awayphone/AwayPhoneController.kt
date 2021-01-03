package com.example.timemanager.ui.awayphone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.ServiceUtils

import com.example.timemanager.ui.awayPhonePackage.AwayPhoneScreen
import com.example.timemanager.ui.awayPhonePackage.AwayPhoneService
import com.example.timemanager.ui.awayPhonePackage.AwayPhoneWhitelist

import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import kotlinx.android.synthetic.main.activity_away_phone_timer.*
import kotlinx.android.synthetic.main.layout_title.*


class AwayPhoneController : AppCompatActivity() {
    private var isRunning: Boolean = false
    private var model: String? = "normal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_away_phone_timer)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)

        this.supportActionBar?.hide()

        button_backward.setOnClickListener(ButtonBackward(this))

        model = intent.getStringExtra("model")
        if(model == "normal"){
            text_title.text = "远离手机：普通模式"
            artwords_model.background = ResourceUtils.getDrawable(ResourceUtils.getIdByName("@drawable/artwords_normal"))
            whitelist.visibility = View.VISIBLE
        }
        else{
            text_title.text = "远离手机：深度模式"
            artwords_model.background = ResourceUtils.getDrawable(ResourceUtils.getIdByName("@drawable/artwords_focus"))
        }

        /*val typeface = ResourcesCompat.getFont(this, R.font.roboto_bold)
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
        })*/


        startAwayPhone.setOnClickListener {
                openService(it)
                //startAwayPhone.setText("结束")
                val intent = Intent(this, AwayPhoneScreen::class.java).apply {  }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
        }
        whitelist.setOnClickListener {
            val intent = Intent(this, AwayPhoneWhitelist::class.java).apply {  }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    fun openService(view: View?) {
        val intent = Intent(this@AwayPhoneController, AwayPhoneService::class.java)
        intent.putExtra("model", model)
        startService(intent)
        Toast.makeText(this, "服务已开启！", Toast.LENGTH_SHORT).show()
    }

    fun closeService(view: View?) {
        stopService(Intent(this@AwayPhoneController, AwayPhoneService::class.java))
        Toast.makeText(this, "服务已关闭！", Toast.LENGTH_SHORT).show()
    }

    fun checkService(): Boolean {
        return ServiceUtils.isServiceRunning(AwayPhoneService::class.java)
    }

}