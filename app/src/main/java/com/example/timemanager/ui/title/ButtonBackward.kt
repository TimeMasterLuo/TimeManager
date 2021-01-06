package com.example.timemanager.ui.title

import android.app.Activity
import android.view.View

class ButtonBackward(private val activity: Activity) : View.OnClickListener {
    override fun onClick(view: View) {
        activity.finish()
    }

}