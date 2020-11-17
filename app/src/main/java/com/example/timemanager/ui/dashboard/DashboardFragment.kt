package com.example.timemanager.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.blankj.utilcode.util.AppUtils
import com.example.timemanager.R
import com.example.timemanager.adapter.awayPhoneAdapter.WhitelistAdapter
import com.example.timemanager.adapter.dashBoardAdapter.ClockAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.example.timemanager.utils.clock.Clock
import com.example.timemanager.utils.networkRequest.MySingleton
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var clocks: MutableList<Clock>
    private var clockAdapter: ClockAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val param=JSONObject()
        param.put("toid",5)
        val url="http://59.78.38.19:8080/getAllClock"
        var listview=root.findViewById<ListView>(R.id.Clock_list)
        clocks= arrayListOf()
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, url, param,
                //成功获取返回时的callback
                { response ->
                    Log.e("response error",response.toString())
                   val clockArray=response.getJSONArray("data")
                    Log.e("length",clockArray.length().toString())
                    var index=0
                    while (index<clockArray.length()){
                        val format =SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        val date=format.parse(clockArray.getJSONObject(index).getString("recordTime"))
                        Log.e("date",date.toString())
                        val setter=clockArray.getJSONObject(index).getString("fromName")
                        val user=clockArray.getJSONObject(index).getString("toName")
                        val note=clockArray.getJSONObject(index).getString("note")
                        val type = if(setter==user){
                            "自设闹钟"
                        }else{
                            "好友闹钟"
                        }
                        val status=clockArray.getJSONObject(index).getString("status")
                        val grade=clockArray.getJSONObject(index).getInt("score").toString()
                        val coins=clockArray.getJSONObject(index).getInt("coins")
                        val clock=object :Clock(type,setter,date,status,grade,coins,note,user){}
                        clocks.add(clock)
                        Log.e("date",clocks.toString())
                        index++
                    }
                    clockAdapter= ClockAdapter(this.activity,clocks)
                    listview.adapter=clockAdapter
                },
                //失败情况调用的callback
                { error ->
                    // TODO: Handle error
                    Log.e("response error",error.toString())
                }
        )
        this.activity?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }
        val btn1 : FloatingActionButton = root.findViewById(R.id.analyze_button)
        btn1.setOnClickListener {
            val intent = Intent(activity, Analyze::class.java).apply {
            }
            val bundle = Bundle()
            intent.putExtras(bundle)
            startActivity(intent)
        }
        val tabLayout: TabLayout=root.findViewById(R.id.tab_layout)
        val mTabSelectedColorListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
//                val tag=tab.text as String
//                if(tag=="任务闹钟"){
//                    val card:CardView=root.findViewById(R.id.away)
//                    card.visibility = GONE
//                }else{
//                    val card:CardView=root.findViewById(R.id.away)
//                    card.visibility = VISIBLE
//                }
//                if(tag=="好友闹钟"){
//                    val card:CardView=root.findViewById(R.id.friend)
//                    card.visibility = GONE
//                }else{
//                    val card:CardView=root.findViewById(R.id.friend)
//                    card.visibility = VISIBLE
//                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e("unselected",tab.text as String)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e("reselected",tab.text as String)
            }
        }
        tabLayout.addOnTabSelectedListener(mTabSelectedColorListener);
//        val btn2 : Button = root.findViewById(R.id.config_button)
//        btn2.setOnClickListener {
//            val intent = Intent(activity, HistoryDetail::class.java)
//            val format =SimpleDateFormat("yyyy-MM-dd")
//            val date=format.parse("2020-11-16")
//            val clock=object :Clock("自设闹钟","月一老贼",date,"11:00","10min","A",1000,"这是备注",""){}
//            val bundle=Bundle()
//            bundle.putSerializable("clock",clock)
//            intent.putExtras(bundle)
//            startActivity(intent)
//        }
        return root
    }
}