package com.example.timemanager.ui.awayPhonePackage

import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.blankj.utilcode.util.ToastUtils
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
    
    private lateinit var appsNotinWhitelistInfo: MutableList<AppUtils.AppInfo>
    private lateinit var appsinWhitelistInfo: MutableList<AppUtils.AppInfo>
    private var tmpinWhitelist: MutableList<AppUtils.AppInfo> = arrayListOf()
    private var tmpNotinWhitelist: MutableList<AppUtils.AppInfo> = arrayListOf()
    private var inWhitelistadapter: WhitelistAdapter? = null
    private var notinWhitelistadapter: WhitelistAdapter? = null
    private var resolveInfoList: List<ResolveInfo>? = null
    private var whitelist: List<T_WHITELIST>? = null
    private lateinit var deleteCreator: SwipeMenuCreator
    private lateinit var addCreator: SwipeMenuCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)
        setContentView(R.layout.activity_away_phone_whitelist)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)

        this.supportActionBar?.hide()

        text_title.text = "管理APP白名单"
        button_backward.setOnClickListener(ButtonBackward(this))
        
        createDeleteSwipMenu()
        inWitelistView.setMenuCreator(deleteCreator)
        setRemoveOnClikcListener(inWitelistView)
        
        createAddSwipMenu()
        notinWitelistView.setMenuCreator(addCreator)
        setAddOnClikcListener(notinWitelistView)

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

        tmpinWhitelist.addAll(appsinWhitelistInfo)
        tmpNotinWhitelist.addAll(appsNotinWhitelistInfo)

        inWhitelistadapter = WhitelistAdapter(this, tmpinWhitelist)
        notinWhitelistadapter = WhitelistAdapter(this, tmpNotinWhitelist)
        inWitelistView.adapter = inWhitelistadapter
        notinWitelistView.adapter = notinWhitelistadapter

        whitelistTab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab != null) {
                    if (tab.position == 0){
                        inWitelistView.visibility = View.VISIBLE
                        notinWitelistView.visibility = View.GONE
                    }
                    else if(tab.position == 1){
                        inWitelistView.visibility = View.GONE
                        notinWitelistView.visibility = View.VISIBLE
                    }
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
        //DbTool.saveOrUpdate(appinfoTable)
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

    private fun createDeleteSwipMenu() {
        deleteCreator = SwipeMenuCreator { menu ->
            val openItem = SwipeMenuItem(
                applicationContext
            )
            // set item background
            openItem.background = ColorDrawable(
                Color.rgb(
                    0xF9, 0x3F, 0x25
                )
            )
            // set item width
            openItem.width = dp2px(90F)
            // set item title
            openItem.title = "删除"
            // set item title fontsize
            openItem.titleSize = 18
            // set item title font color
            openItem.titleColor = Color.WHITE
            // add to menu
            menu.addMenuItem(openItem)
        }
    }

    private fun createAddSwipMenu() {
        addCreator = SwipeMenuCreator { menu ->
            val openItem = SwipeMenuItem(
                applicationContext
            )
            // set item background
            openItem.background = ColorDrawable(
                Color.rgb(
                    0xC9, 0xC9, 0xCE
                )
            )
            // set item width
            openItem.width = dp2px(90F)
            // set item title
            openItem.title = "添加"
            // set item title fontsize
            openItem.titleSize = 18
            // set item title font color
            openItem.titleColor = Color.WHITE
            // add to menu
            menu.addMenuItem(openItem)
        }
    }


    private fun setAddOnClikcListener(swipeMenuListView : SwipeMenuListView){
        swipeMenuListView.setOnMenuItemClickListener { position, menu, index ->
            val appinfo = appsNotinWhitelistInfo.get(position)
            Log.e("add", "testadd")
            addtoWhitelist(appinfo)
            return@setOnMenuItemClickListener false
        }
    }

    private fun setRemoveOnClikcListener(swipeMenuListView : SwipeMenuListView){
        swipeMenuListView.setOnMenuItemClickListener { position, menu, index ->
            val appinfo = appsinWhitelistInfo.get(position)
            Log.e("remove", "testremove")
            deleteinWhitelist(appinfo)
            return@setOnMenuItemClickListener false
        }
    }

    private fun addtoWhitelist(appinfo: AppUtils.AppInfo) {
        appsNotinWhitelistInfo.remove(appinfo)
        appsinWhitelistInfo.add(appinfo)

        notinWhitelistadapter?.resetData(appsNotinWhitelistInfo)
        inWhitelistadapter?.resetData(appsinWhitelistInfo)
        notinWhitelistadapter?.notifyDataSetChanged()
        inWhitelistadapter?.notifyDataSetChanged()
        saveApptoWhitelist(appinfo)
        ToastUtils.make().show("成功添加到App白名单！")
    }

    private fun deleteinWhitelist(appinfo: AppUtils.AppInfo) {
        Log.e("indelete", appinfo.toString())
        appsinWhitelistInfo.remove(appinfo)
        appsNotinWhitelistInfo.add(appinfo)

        inWhitelistadapter?.resetData(appsinWhitelistInfo)
        notinWhitelistadapter?.resetData(appsNotinWhitelistInfo)
        inWhitelistadapter?.notifyDataSetChanged()
        notinWhitelistadapter?.notifyDataSetChanged()
        deleteAppinWhitelist(appinfo.packageName)
        ToastUtils.make().show("成功从App白名单中移除！")
    }

}

