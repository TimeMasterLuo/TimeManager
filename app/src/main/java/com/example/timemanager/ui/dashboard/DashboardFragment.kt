package com.example.timemanager.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.timemanager.R
import com.example.timemanager.adapter.dashBoardAdapter.ClockAdapter
import com.example.timemanager.application.TimeManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.example.timemanager.utils.clock.Clock
import com.example.timemanager.utils.networkRequest.MySingleton
import org.json.JSONObject
import java.text.SimpleDateFormat


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var clocks: MutableList<Clock>
    private var clockAdapter: ClockAdapter? = null
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        var root = inflater.inflate(R.layout.fragment_friendlist_unauthorized, container, false)
        var flag: Boolean = (activity!!.application as TimeManager).login_flag
        if(flag) {
             root = inflater.inflate(R.layout.fragment_dashboard, container, false)
            var user: Int = (activity!!.application as TimeManager).uid.toInt()
            val param = JSONObject()
            param.put("id", 5)
            val url = "http://59.78.38.19:8080/getAllRecord"
            var listview = root.findViewById<ListView>(R.id.Clock_list)
            clocks = arrayListOf()
            val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST, url, param,
                    //成功获取返回时的callback
                    { response ->
//                        Log.e("response error", response.toString())
                        val clockArray = response.getJSONArray("clock_data")
                        val focusArray= response.getJSONArray("focus_data")
                        var i=clockArray.length()-1
                        var j=focusArray.length()-1
                        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        while (i>=0&&j>=0){
                            val id=clockArray.getJSONObject(i).getInt("id")
                            val date_clock=format.parse(clockArray.getJSONObject(i).getString("recordTime"))
                            val date_focus=format.parse(focusArray.getJSONObject(j).getString("startTime"))
                            if(date_clock.after(date_focus)){
                                val setter = clockArray.getJSONObject(i).getString("fromName")
                                val user = clockArray.getJSONObject(i).getString("toName")
                                val note = clockArray.getJSONObject(i).getString("note")
                                val task = clockArray.getJSONObject(i).getString("task")
                                val type = if (setter == user) {
                                      "自设闹钟"
                                   } else {
                                      "好友闹钟"
                                }
                                val status = clockArray.getJSONObject(i).getString("status")
                                val grade = clockArray.getJSONObject(i).getInt("score").toString()
                                val coins = clockArray.getJSONObject(i).getInt("coins")
                                val clock =object :Clock(id,"闹钟",type,task,setter,date_clock,status,grade,coins,note,user){}
                                clocks.add(clock)
                                i--
                            }else{
                                val id=focusArray.getJSONObject(j).getInt("id")
                                val user=focusArray.getJSONObject(j).getString("userName")
                                val end_time=format.parse(focusArray.getJSONObject(j).getString("endTime"))
                                val type=focusArray.getJSONObject(j).getString("type")
                                val coins=focusArray.getJSONObject(j).getInt("coins")
                                val time=focusArray.getJSONObject(j).getString("time")
                                val clock=object :Clock(id,"远离手机",type,date_focus,end_time,user,time,coins){}
                                clocks.add(clock)
                                j--
                            }
                        }
                        if(i<0){
                            while (j>=0){
                                val id=focusArray.getJSONObject(j).getInt("id")
                                val date_focus=format.parse(focusArray.getJSONObject(j).getString("startTime"))
                                val user=focusArray.getJSONObject(j).getString("userName")
                                val end_time=format.parse(focusArray.getJSONObject(j).getString("endTime"))
                                val type=focusArray.getJSONObject(j).getString("type")
                                val coins=focusArray.getJSONObject(j).getInt("coins")
                                val time=focusArray.getJSONObject(j).getString("time")
                                val clock=object :Clock(id,"远离手机",type,date_focus,end_time,user,time,coins){}
                                clocks.add(clock)
                                j--
                            }
                        }else{
                            while (i>=0){
                                val id=clockArray.getJSONObject(i).getInt("id")
                                val date_clock=format.parse(clockArray.getJSONObject(i).getString("recordTime"))
                                val setter = clockArray.getJSONObject(i).getString("fromName")
                                val user = clockArray.getJSONObject(i).getString("toName")
                                val note = clockArray.getJSONObject(i).getString("note")
                                val task = clockArray.getJSONObject(i).getString("task")
                                val type = if (setter == user) {
                                    "自设闹钟"
                                } else {
                                    "好友闹钟"
                                }
                                val status = clockArray.getJSONObject(i).getString("status")
                                val grade = clockArray.getJSONObject(i).getInt("score").toString()
                                val coins = clockArray.getJSONObject(i).getInt("coins")
                                val clock =object :Clock(id,"闹钟",type,task,setter,date_clock,status,grade,coins,note,user){}
                                clocks.add(clock)
                                i--
                            }
                        }
                        clockAdapter = ClockAdapter(this.activity, clocks)
                        listview.adapter = clockAdapter
                    },
                    //失败情况调用的callback
                    { error ->
                        // TODO: Handle error
                        Log.e("response error", error.toString())
                    }
            )
            this.activity?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }
            val btn1: FloatingActionButton = root.findViewById(R.id.analyze_button)
            btn1.setOnClickListener {
                val intent = Intent(activity, Analyze::class.java).apply {
                }
                val bundle=Bundle()
                bundle.putSerializable("clocks", clocks as ArrayList<Clock>)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            val tabLayout: TabLayout = root.findViewById(R.id.tab_layout)
            val activity=this.activity
            val mTabSelectedColorListener = object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                val tag=tab.text as String
                val tmp: MutableList<Clock> = arrayListOf()
                if(tag=="自设闹钟"){
                    for (i in clocks.indices){
                        if(clocks[i].kind=="闹钟"&&clocks[i].type=="自设闹钟"){
                            tmp.add(clocks[i])
                        }
                    }
                    val tmpAdapter = ClockAdapter(activity, tmp)
                    listview.adapter = tmpAdapter
                }
                else if(tag=="好友闹钟"){
                    for (i in clocks.indices){
                        if(clocks[i].kind=="闹钟"&&clocks[i].type=="好友闹钟"){
                            tmp.add(clocks[i])
                        }
                    }
                    val tmpAdapter = ClockAdapter(activity, tmp)
                    listview.adapter = tmpAdapter
                }else if(tag=="远离手机"){
                    for (i in clocks.indices){
                        if(clocks[i].kind=="远离手机"){
                            tmp.add(clocks[i])
                        }
                    }
                    val tmpAdapter = ClockAdapter(activity, tmp)
                    listview.adapter = tmpAdapter
                }else{
                    listview.adapter = clockAdapter
                 }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            }
            tabLayout.addOnTabSelectedListener(mTabSelectedColorListener)
        }
        return root
    }
}