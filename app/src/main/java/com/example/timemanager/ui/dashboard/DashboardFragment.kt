package com.example.timemanager.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timemanager.HistoryDetail
import com.example.timemanager.R
import com.example.timemanager.Analyze
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private val data = listOf("apple", "banana", "ori", "sdwqd")

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
                startActivity(intent)
            }
        })
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