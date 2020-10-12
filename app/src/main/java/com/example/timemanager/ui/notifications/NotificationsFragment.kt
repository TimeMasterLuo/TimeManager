package com.example.timemanager.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.timemanager.R
import com.example.timemanager.AlarmFromFriend

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val btn1 : ImageButton = root.findViewById(R.id.alarm_detail_entry)
        btn1.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(activity, AlarmFromFriend::class.java).apply {
                }
                startActivity(intent)
            }
        })
        return root
    }
}