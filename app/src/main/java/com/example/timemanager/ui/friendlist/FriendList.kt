package com.example.timemanager.ui.friendlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.timemanager.R

class FriendList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_list)
    }
    fun onClick(view:View){
        val intent = Intent(this, FriendProfile::class.java).apply {
        }
        startActivity(intent)
    }
}