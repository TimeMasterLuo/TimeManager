package com.example.timemanager.ui.AwayPhonePackage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import com.example.timemanager.Adapter.AwayPhoneAdapter.WhitelistAdapter
import com.example.timemanager.R
import com.example.timemanager.ui.title.ButtonBackward
import kotlinx.android.synthetic.main.layout_title.*


class AwayPhoneWhitelist : AppCompatActivity() {
    private var listView: ListView? = null
    private lateinit var appsInfos: List<AppUtils.AppInfo>
    private lateinit var appsInfosNoSystem: MutableList<AppUtils.AppInfo>
    private var myadapter: WhitelistAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_away_phone_whitelist)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)

        this.supportActionBar?.hide()

        text_title.text = "管理APP白名单"
        button_backward.setOnClickListener(ButtonBackward(this))
        listView = findViewById<View>(R.id.whitelist) as ListView

        appsInfos = AppUtils.getAppsInfo()
        appsInfosNoSystem = arrayListOf()
        Log.e("appInfo", appsInfos.get(0).toString())

        for(i in appsInfos.indices){
            if(appsInfos.get(i).isSystem) appsInfosNoSystem.add(appsInfos.get(i))
        }

        myadapter = WhitelistAdapter(this, appsInfos)
        listView!!.adapter = myadapter
    }
}

