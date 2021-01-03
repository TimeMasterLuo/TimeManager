package com.example.timemanager.ui.task.click

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timemanager.R
import com.example.timemanager.ui.alarm.AlarmActivity
import kotlinx.android.synthetic.main.fragment_task_click.*
import kotlinx.android.synthetic.main.fragment_task_none.*

class ClickFragment:Fragment() {
    private val logTag = "ClickFragment"
    private var count:Int=20

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_task_click, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity: AlarmActivity = activity as AlarmActivity
        button_task_click.setOnClickListener {
            count--;
            if (count==0)(activity as AlarmActivity).finish()
            button_task_click.text="点击 "+count+" 次！";
        }
        button_task_click.text="点击 "+count+" 次！";
    }
}