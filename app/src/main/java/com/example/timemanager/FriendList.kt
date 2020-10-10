package com.example.timemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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