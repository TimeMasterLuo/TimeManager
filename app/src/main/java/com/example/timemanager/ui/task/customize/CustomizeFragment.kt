package com.example.timemanager.ui.task.customize

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timemanager.R
import com.example.timemanager.ui.alarm.AlarmActivity
import kotlinx.android.synthetic.main.fragment_task_compute.*

class CustomizeFragment: Fragment() {
    private val logTag = "CustomizeFragment"
    private var a:Int=0
    private var b:Int=0
    private var ans:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_task_compute, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity: AlarmActivity = activity as AlarmActivity
        a= (activity as AlarmActivity).model.x.toInt();
        b= (activity as AlarmActivity).model.y.toInt();
        ans = a*b;
        textView_question.text="请计算："+a+" * "+b+ " = "
        button_task_compute.text="提交答案";
        button_task_compute.setOnClickListener {
            val input=task_compute_answer.text.toString();
            println("correct answer: $ans input: $input")
            if (input!=""&&ans.toString()==task_compute_answer.text.toString())(activity as AlarmActivity).finish()

        }
    }
}