package com.example.timemanager.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timemanager.R

class AuthorizedFragment : Fragment() {

    private lateinit var authorizedFragment: AuthorizedFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting_authorized, container, false)
        return root
    }
}