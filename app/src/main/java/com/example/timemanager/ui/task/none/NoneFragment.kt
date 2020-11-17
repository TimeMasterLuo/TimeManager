package com.example.timemanager.ui.task.none

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timemanager.R
import com.example.timemanager.ui.alarm.AlarmActivity
import com.example.timemanager.ui.task.puzzle.PuzzleBoardView
import kotlinx.android.synthetic.main.fragment_task_3puzzle.*
import kotlinx.android.synthetic.main.fragment_task_none.*

class NoneFragment:Fragment() {
    private val logTag = "NoneFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_task_none, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity: AlarmActivity = activity as AlarmActivity
        button_task_none.setOnClickListener {
            (activity as AlarmActivity).finish()
        }
    }
}