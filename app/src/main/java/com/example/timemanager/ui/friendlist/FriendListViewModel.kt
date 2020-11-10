package com.example.timemanager.ui.friendlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FriendListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is friendlist Fragment"
    }
    val text: LiveData<String> = _text
}