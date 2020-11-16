package com.example.timemanager.ui.awayPhonePackage

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
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.TimeUtils
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_WHITELIST
import java.util.*


class AwayPhoneService : Service() {
    private var mContext: Context? = null
    private var mBinder: MyBinder = MyBinder()
    private var mTimer: Timer? = null
    private var mytime: Float = 0.0F
    private var running = true
    private var model: String? = "normal"
    private val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    private val notification2: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
    private var r = RingtoneManager.getRingtone(application, notification)

    private var whitelistFocus: Array<String> = arrayOf("com.example.timemanager")
    private var whitelistNormal: Array<String> = arrayOf("com.example.timemanager", "com.android.chrome")
    private var whitelist: List<T_WHITELIST>? = null

    private lateinit var startTime: Date
    private lateinit var endTime: Date

    internal inner class MyBinder : Binder() {
        val service: AwayPhoneService
            get() = this@AwayPhoneService
    }
    override fun onBind(intent: Intent): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        startTime = TimeUtils.getNowDate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        findAllAppsinWhitelist()
        model = intent.getStringExtra("model")
//        whitelist = if(model.equals("normal")){
//            whitelistNormal
//        } else {
//            whitelistFocus
//        }

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


                    if ((getLauncherPackageName(mContext) == topPackageName || topPackageName == "com.example.timemanager")) {
                        return
                    }
                    for(app in this.whitelist!!){
                        if(app.PACKAGENAME ==  topPackageName)
                            return
                    }

                    Log.e("TopPackage Name", topPackageName)
                    //模拟home键点击
                    ClickUtils.back2HomeFriendly("远离手机模式结束！")

                    //转到提示页面
                    val intent1 = Intent(mContext, AwayPhoneResult::class.java).apply {  }
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    endTime = TimeUtils.getNowDate()
                    val seconds = TimeUtils.getTimeSpan(endTime, startTime, TimeConstants.MSEC)/1000F
                    intent1.putExtra("time",seconds.toString())
                    intent1.putExtra("startTime",TimeUtils.date2String(startTime))
                    intent1.putExtra("endTime",TimeUtils.date2String(endTime))
                    //intent1.putExtra("clock", r.toString())
                    startActivity(intent1)
                    Log.e("startTime", TimeUtils.date2String(startTime))
                    Log.e("endTime", TimeUtils.date2String(endTime))

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

    fun manualStop(){
        val intent1 = Intent(mContext, AwayPhoneResult::class.java).apply {  }
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        endTime = TimeUtils.getNowDate()
        val seconds = TimeUtils.getTimeSpan(endTime, startTime, TimeConstants.MSEC)/1000F
        intent1.putExtra("time",seconds.toString())
        intent1.putExtra("startTime",TimeUtils.date2String(startTime))
        intent1.putExtra("endTime",TimeUtils.date2String(endTime))
        //r = RingtoneManager.getRingtone(applicationContext, notification2)
        startActivity(intent1)
        r.play()
        running = false
    }

    private fun findAllAppsinWhitelist(){
        whitelist = DbTool.getDbManager().selector(T_WHITELIST::class.java).findAll()
    }

}