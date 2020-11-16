package com.example.timemanager.ui.awayphone

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_AWAY_PHONE
import com.example.timemanager.utils.dialog.ShowDialog
import kotlinx.android.synthetic.main.activity_away_phone.*
import kotlinx.android.synthetic.main.layout_title.*
import kotlin.math.roundToInt

class AwayPhone : AppCompatActivity() {
    private var mContext: Context? = null
    private var localAwayPhoneHistory: List<T_AWAY_PHONE>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_away_phone)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)
        //button_backward是回退键
        button_backward.setOnClickListener(ButtonBackward(this))
        text_title.text = "远离手机"

        this.supportActionBar?.hide()
//        AlertDialog.Builder(this).apply {
//            setTitle("Tips:")
//            setMessage("普通模式下您仍可以使用部分软件，你可以在设置中进一步设置白名单，在专注模式下绝大部分功能将被禁用，切换屏幕会导致计时终止。")
//            setCancelable(false)
//            setPositiveButton("OK"){dialog, which ->  }
//            show()
//        }


        getAllLoacalAwayPhoneHistory()
        val time = localAwayPhoneHistory?.let { getLocalAwayPhoneTime(it) }
        awayPhonetime.text = "您今天已经远离手机 $time 分钟"

        if(!checkPermission()){
            ShowDialog().show(mContext, "该功能需要读取App使用情况，是否前往开启权限？", object: ShowDialog.OnBottomClickListener {
                override fun positive(){
                    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    startActivityForResult(intent, 100)
                }
                override fun negative(){
                    Toast.makeText(mContext, "您需要打开权限才能使用该功能！", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
        }

        normal.setOnClickListener {
            val intent = Intent(this, AwayPhoneController::class.java).apply {
            }
            intent.putExtra("model", "normal")
            startActivity(intent)
        }

        focus.setOnClickListener {
            val intent = Intent(this, AwayPhoneController::class.java).apply {
            }
            intent.putExtra("model", "foucs")
            startActivity(intent)
        }
    }
    fun startTimer(view: View){
        val intent = Intent(this, AwayPhoneController::class.java).apply {
        }
        startActivity(intent)
    }

    fun openPermission(view: View?) {
        if (!checkPermission()) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivityForResult(intent, 100)
        } else {
            Toast.makeText(this, "权限已开启！", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (!checkPermission()) {
                Toast.makeText(this, "您需要打开权限才能使用该功能！", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "权限已开启！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermission(): Boolean {
        val appOps =
            getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode: Int
        mode = appOps.checkOpNoThrow(
            "android:get_usage_stats",
            Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun getAllLoacalAwayPhoneHistory() {
        localAwayPhoneHistory = DbTool.getDbManager().selector(T_AWAY_PHONE::class.java).findAll()
    }

    private fun getLocalAwayPhoneTime(list: List<T_AWAY_PHONE>) : Int{
        var time = 0F
        for(awayPhoneHistory in list){
            time = time + awayPhoneHistory.TIME.toFloat()
            Log.e("time", awayPhoneHistory.TIME)
        }
        return time.roundToInt()
    }


}