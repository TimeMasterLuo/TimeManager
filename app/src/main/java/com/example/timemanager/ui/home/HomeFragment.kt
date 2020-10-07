package com.example.timemanager.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

        val btn : Button = root.findViewById(R.id.alarm_button)
        btn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                textView.text = "Clicked"
                val intent = Intent(getActivity(), SetAlarm::class.java).apply {
                }
                startActivity(intent)
            }
        })
        return root
    }

}