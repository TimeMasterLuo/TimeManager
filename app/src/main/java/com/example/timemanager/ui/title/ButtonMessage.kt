package com.example.timemanager.ui.title

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import com.example.timemanager.ui.notifications.MessageView

class ButtonMessage(private val context: Context, private val activity: Activity) :
    View.OnClickListener {
    override fun onClick(view: View) {
        val intent = Intent(context, MessageView::class.java)
        activity.startActivity(intent)
    }

}