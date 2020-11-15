package com.example.timemanager.ui.task.puzzle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timemanager.ui.alarm.AlarmActivity
import com.example.timemanager.R
import kotlinx.android.synthetic.main.fragment_task_3puzzle.*

class PuzzleFragment : Fragment() {

    private val logTag = "PuzzleFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(logTag, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_task_3puzzle, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity: AlarmActivity = activity as AlarmActivity
        val puzzleBoardView = context?.let { PuzzleBoardView(it, 3) }
        puzzle_container.addView(puzzleBoardView)

        button_new_game.setOnClickListener {
            if (puzzleBoardView != null) {
                puzzleBoardView.initGame()
            }
            if (puzzleBoardView != null) {
                puzzleBoardView.invalidate()
            }
        }
    }
}
