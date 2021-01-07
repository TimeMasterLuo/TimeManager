package com.example.timemanager.ui.task.react

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timemanager.R
import com.example.timemanager.ui.alarm.AlarmActivity
import kotlinx.android.synthetic.main.fragment_task_click.*
import kotlinx.android.synthetic.main.fragment_task_react.*
import java.util.*



class ReactFragment: Fragment() {
    private val logTag = "ReactFragment"
    private var time:Long=3000
    private var buttonText="请等待，按钮变化时迅速点击！"
    private var flag=false
    private var finish=false
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_task_react, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        time= (2000..5000).random().toLong()
        val mainActivity: AlarmActivity = activity as AlarmActivity
        button_task_react.setOnClickListener {

            if (flag)(activity as AlarmActivity).finish()
            button_task_react.text=buttonText;
        }
        button_task_react.text=buttonText

        while(!finish){
            loop();
        }

    }
    private fun loop(){
        println("loop start")
        val task1 =object:TimerTask(){
            override fun run() {
                buttonText="就是现在！"
                flag=true
            }
        }
        val task2 =object:TimerTask(){
            override fun run() {
                buttonText="请等待，按钮变化时迅速点击！"
                flag=false

            }
        }
        Timer().schedule(task1, time)
        Timer().schedule(task2, 500)
        task1.cancel()
        task2.cancel()
    }
}