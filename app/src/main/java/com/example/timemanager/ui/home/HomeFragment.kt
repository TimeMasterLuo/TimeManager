package com.example.timemanager.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.timemanager.Away_phone
import com.example.timemanager.R
import com.example.timemanager.SetAlarm

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val btn1 : Button = root.findViewById(R.id.alarm_button)
        btn1.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                textView.text = "Clicked button1"
                val intent = Intent(activity, SetAlarm::class.java).apply {
                }
                startActivity(intent)
            }
        })

        val btn2 : Button = root.findViewById(R.id.away_phone_button)
        btn2.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                textView.text = "Clicked button2"
                val intent = Intent(getActivity(), Away_phone::class.java).apply {
                }
                startActivity(intent)
            }
        })

        return root
    }

}