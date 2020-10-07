package com.example.timemanager.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.timemanager.R
import com.example.timemanager.SetAlarm
import com.example.timemanager.ui.login.Login
import kotlinx.android.synthetic.main.activity_away_phone.*

class UnauthorizedFragment : Fragment() {

    private lateinit var unauthorizedFragment: AuthorizedFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting_unauthorized, container, false)

        val btn1 : Button = root.findViewById(R.id.login_button)
        btn1.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(getActivity(), Login::class.java).apply {
                }
                startActivity(intent)
            }
        })

        return root
    }
}