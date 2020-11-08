package com.example.timemanager.AwayPhonePackage

import android.app.Service
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ServiceUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.timemanager.R
import kotlinx.android.synthetic.main.activity_away_phone_screen.*


class AwayPhoneScreen : AppCompatActivity() {
    private var myService: AwayPhoneService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_away_phone_screen)
        BarUtils.setStatusBarVisibility(this, false)
        timecount.base = SystemClock.elapsedRealtime() - 0
        timecount.start()

        val conn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder: AwayPhoneService.MyBinder = service as AwayPhoneService.MyBinder
                myService = binder.service
            }

            override fun onServiceDisconnected(name: ComponentName) {
                myService = null
            }
        }
        ServiceUtils.bindService(AwayPhoneService::class.java, conn, Service.BIND_WAIVE_PRIORITY)

        stopAwayPhone.setOnClickListener {
            myService?.manualStop()
            ServiceUtils.unbindService(conn)
            finish()
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event != null) {
            if(event.keyCode == KeyEvent.KEYCODE_BACK){
                ToastUtils.make().show("返回键已禁用，请先结束远离手机模式！")
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }
}