package com.example.timemanager.AwayPhonePackage

import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*


class AwayPhoneService : Service() {
    private var mContext: Context? = null
    private var mTimer: Timer? = null
    private var mytime: Float = 0.0F
    private var running = true
    private var model: String? = "normal"
    private val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    private var r = RingtoneManager.getRingtone(application, notification)

    private var whitelistFocus: Array<String> = arrayOf("com.example.timemanager")
    private var whitelistNormal: Array<String> = arrayOf("com.example.timemanager", "com.android.chrome")
    private var whitelist: Array<String> = arrayOf("")

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        model = intent.getStringExtra("model")
        whitelist = if(model.equals("normal")){
            whitelistNormal
        } else {
            whitelistFocus
        }

        mytime = 0.0F
        running = true
        mTimer = Timer()
        r = RingtoneManager.getRingtone(applicationContext, notification)

        val handler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if(running){
                    mytime += 200F
                    //Log.d("TAG", "handleMessage: $mytime")
                }
            }
        }
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                val msg: Message = handler.obtainMessage()
                msg.what = 1
                msg.obj = ""
                msg.sendToTarget()
                topApp
            }
        }
        mTimer!!.schedule(task, 0, 200)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mTimer!!.cancel()
        if(r.isPlaying)
            r.stop()
        super.onDestroy()
    }

    private val topApp: Unit
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        get() {
            if(!running) return

            val mUsageStatsManager =
                getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager //usagestats
            val time = System.currentTimeMillis()
            val usageStatsList =
                mUsageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_BEST,
                    time - 5000,
                    time
                )
            if (usageStatsList != null && usageStatsList.isNotEmpty()) {
                val usageStatsMap: SortedMap<Long, UsageStats> =
                    TreeMap()
                for (usageStats in usageStatsList) {
                    usageStatsMap[usageStats.lastTimeUsed] = usageStats
                }
                if (!usageStatsMap.isEmpty()) {
                    val topPackageName =
                        usageStatsMap[usageStatsMap.lastKey()]!!.packageName


                    if (getLauncherPackageName(mContext) == topPackageName || whitelist.contains(topPackageName)) {
                        return
                    }

                    Log.e("TopPackage Name", topPackageName)
                    //模拟home键点击
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.addCategory(Intent.CATEGORY_HOME)
                    startActivity(intent)
                    //Toast.makeText(this, "服务已开启！", Toast.LENGTH_SHORT).show()
                    //启动提示页面
                    val intent1 = Intent(mContext, AwayPhoneResult::class.java).apply {  }
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val seconds = mytime/1000
                    intent1.putExtra("time",seconds.toString())
                    intent1.putExtra("clock", r.toString())
                    startActivity(intent1)
                    Log.e("alarm", r.toString())

                    r.play()
                    running = false
                    //stopSelf();
                }
            }
        }

    private fun getLauncherPackageName(context: Context?): String {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val res = context!!.packageManager.resolveActivity(intent, 0)!!
        if (res.activityInfo == null) {
            return ""
        }
        return if (res.activityInfo.packageName == "android") {
            ""
        } else {
            res.activityInfo.packageName
        }
    }
}