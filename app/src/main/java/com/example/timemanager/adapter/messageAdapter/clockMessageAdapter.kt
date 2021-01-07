package com.example.timemanager.adapter.messageAdapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.blankj.utilcode.util.TimeUtils
import com.example.timemanager.R
import com.example.timemanager.adapter.MyBaseAdapter
import com.example.timemanager.ui.dashboard.HistoryDetail
import com.example.timemanager.ui.dashboard.friendClockDetail
import com.example.timemanager.utils.clock.Clock
import java.util.*

class clockMessageAdapter(
    context: Context?,
    list: List<friendClockMessage?>?
) : MyBaseAdapter<friendClockMessage?>(context, list) {
    override fun getView(
        position: Int,
        myconvertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = myconvertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(mContext).inflate(R.layout.adapter_addmessage_item, null)
        }
        val message = mList[position]!!
        if (message.type == "好友闹钟") {
            val addFriendMessage =
                convertView?.findViewById<View>(R.id.addFriendMessage) as CardView?
            addFriendMessage?.visibility = View.GONE
            val clockUsername =
                convertView?.findViewById<View>(R.id.clockUsername) as TextView?
            val clockIntro =
                convertView?.findViewById<View>(R.id.clockIntro) as TextView?
            val alarm_detail_entry =
                convertView?.findViewById<View>(R.id.alarm_detail_entry) as ImageButton?
            if (clockUsername != null) {
                clockUsername.text = message.sender
            }
            if (message.note == "") {
                val note = message.sender + "很懒，没有任何留言~"
                if (clockIntro != null) {
                    clockIntro.text = note
                }
            } else {
                if (clockIntro != null) {
                    clockIntro.text = message.note
                }
            }
            if (alarm_detail_entry != null) {
                alarm_detail_entry.setOnClickListener {
                    getAlarmDetails(position)
                }
            }
        }
        return convertView
    }

    private fun getAlarmDetails(i: Int)
    {
        val message = mList[i]!!
        val starttime:Date = TimeUtils.string2Date(message.recordTime)
        val clock = Clock(message.id.toInt(), "闹钟", "好友闹钟", message.task, message.sender, starttime,
            message.clockStatus, message.score.toString(), message.coins, message.note, message.receiver)
        val bundle:Bundle = Bundle()
        bundle.putSerializable("clock", clock)
        val intent = Intent(mContext, friendClockDetail::class.java).apply {  }
        intent.putExtras(bundle)
        mContext.startActivity(intent)
    }
}