package com.example.timemanager.ui.task.compute

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timemanager.R
import com.example.timemanager.ui.alarm.AlarmActivity
import kotlinx.android.synthetic.main.fragment_task_click.*
import kotlinx.android.synthetic.main.fragment_task_compute.*

class ComputeFragment: Fragment() {
    private val logTag = "ComputeFragment"
    private var count:Int=20
    private var a:Int=(10..30).random()
    private var b:Int=(5..10).random()
    private var c:Int=(1..100).random()
    private var ans:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)

        ans = a*b+c;
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_task_compute, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity: AlarmActivity = activity as AlarmActivity
        textView_question.text="请计算："+a+" * "+b+" + "+c+" = "
        button_task_compute.text="提交答案";

        button_task_compute.setOnClickListener {
            val input=task_compute_answer.text.toString();
            println("answer:$ans  input:$input")
            if (input!=""&&ans.toString()==task_compute_answer.text.toString())(activity as AlarmActivity).finish()

        }
    }
}