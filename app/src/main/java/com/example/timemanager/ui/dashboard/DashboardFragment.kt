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
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timemanager.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val btn1 : FloatingActionButton = root.findViewById(R.id.analyze_button)
        btn1.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(activity, Analyze::class.java).apply {
                }
                val bundle = Bundle()
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        val tab_layout: TabLayout=root.findViewById(R.id.tab_layout)
        val mTabSelectedColorListener = object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tag=tab.text as String
                if(tag=="任务闹钟"){
                    val card:CardView=root.findViewById(R.id.away)
                    card.visibility = GONE
                }else{
                    val card:CardView=root.findViewById(R.id.away)
                    card.visibility = VISIBLE
                }
                if(tag=="好友闹钟"){
                    val card:CardView=root.findViewById(R.id.friend)
                    card.visibility = GONE
                }else{
                    val card:CardView=root.findViewById(R.id.friend)
                    card.visibility = VISIBLE
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
                Log.e("unselected",tab.text as String)
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.e("reselected",tab.text as String)
            }
        }
        tab_layout.addOnTabSelectedListener(mTabSelectedColorListener);
        val btn2 : Button = root.findViewById(R.id.config_button)
        btn2.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(getActivity(), HistoryDetail::class.java).apply {
                }
                startActivity(intent)
            }
        })
        return root
    }
}