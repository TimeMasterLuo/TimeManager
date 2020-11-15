package com.example.timemanager.awayPhonePackage

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.AppUtils
import com.example.timemanager.R
import com.example.timemanager.adapter.awayPhoneAdapter.WhitelistAdapter
import com.example.timemanager.ui.title.ButtonBackward
import com.example.timemanager.utils.LocalDataBase.DbTool
import com.example.timemanager.utils.LocalDataBase.T_WHITELIST
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_away_phone_whitelist.*
import kotlinx.android.synthetic.main.layout_title.*


class AwayPhoneWhitelist : AppCompatActivity() {
    private var INWHITELIST = "INWHITELIST"
    private var NOTINWHITELIST = "NOTINWHITELIST"
    private var type = INWHITELIST
    private var context = this

    private var listView: ListView? = null
    private lateinit var appsNotinWhitelistInfo: MutableList<AppUtils.AppInfo>
    private lateinit var appsinWhitelistInfo: MutableList<AppUtils.AppInfo>
    private var targetlist: MutableList<AppUtils.AppInfo> = arrayListOf()
    private var myadapter: WhitelistAdapter? = null
    private var resolveInfoList: List<ResolveInfo>? = null
    private var whitelist: List<T_WHITELIST>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_away_phone_whitelist)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)

        this.supportActionBar?.hide()

        text_title.text = "管理APP白名单"
        button_backward.setOnClickListener(ButtonBackward(this))
        listView = findViewById<View>(R.id.whitelist) as ListView

        findAllAppsinWhitelist()

        appsNotinWhitelistInfo = arrayListOf()
        appsinWhitelistInfo = arrayListOf()
        getLauncherApp()

        for(resolve in this.resolveInfoList!!){
            var inWhitelist = false
            val appinfo = AppUtils.getAppInfo(resolve.activityInfo.packageName)
            for(appinWhitelist in this.whitelist!!){
                if(appinfo.packageName == appinWhitelist.PACKAGENAME){
                    inWhitelist = true
                    break
                }
            }
            if(inWhitelist)
                appsinWhitelistInfo.add(appinfo)
            else
                appsNotinWhitelistInfo.add(appinfo)
        }

        if(type == INWHITELIST)
            targetlist.addAll(appsinWhitelistInfo)
        else
            targetlist.addAll(appsNotinWhitelistInfo)
        myadapter = WhitelistAdapter(this, targetlist)
        listView!!.adapter = myadapter
        Log.e("?","t")
        whitelistTab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab != null) {
                    if (tab.position == 0){
                        myadapter!!.resetData(appsinWhitelistInfo)
                    }
                    else if(tab.position == 1){
                        myadapter!!.resetData(appsNotinWhitelistInfo)
                    }
                    myadapter!!.notifyDataSetChanged()
                }
            }

        })
    }

    private fun saveApptoWhitelist(appinfo:AppUtils.AppInfo){
        val appinfoTable = T_WHITELIST()
        appinfoTable.PACKAGENAME = appinfo.packageName
        appinfoTable.NAME = appinfo.name
        //appinfoTable.ICON = ConvertUtils.drawable2Bytes(appinfo.icon)
        appinfoTable.PACKAGEPATH = appinfo.packagePath
        appinfoTable.VERSIONNAME = appinfo.versionName
        appinfoTable.VERSIONCODE = appinfo.versionCode
        appinfoTable.ISSYSTEM = appinfo.isSystem
        DbTool.saveOrUpdate(appinfoTable)
    }

    private fun findAllAppsinWhitelist(){
        whitelist = DbTool.getDbManager().selector(T_WHITELIST::class.java).findAll()
    }

    private fun findAppinWhitelist(packagename:String) : T_WHITELIST{
        return DbTool.getDbManager().selector(T_WHITELIST::class.java)
            .where("PACKAGENAME", "=", packagename)
            .findFirst()
    }

    private fun deleteAppinWhitelist(packagename: String){
        val target = DbTool.getDbManager().selector(T_WHITELIST::class.java)
            .where("PACKAGENAME", "=", packagename).findFirst()
            ?: return
        DbTool.delete(target as Any)
    }

    private fun getLauncherApp() {
        // 桌面应用的启动在INTENT中需要包含ACTION_MAIN 和CATEGORY_HOME.
        val intent = Intent()
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.action = Intent.ACTION_MAIN
        resolveInfoList = packageManager.queryIntentActivities(intent, 0)
    }
}

