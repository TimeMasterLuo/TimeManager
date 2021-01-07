package com.example.timemanager.ui.home

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.timemanager.R
import com.example.timemanager.application.TimeManager
import com.example.timemanager.ui.title.ButtonMessage
import kotlinx.android.synthetic.main.layout_title.*
import com.google.android.material.bottomnavigation.BottomNavigationView


//class GlobalData : Application() {
//    var login_flag = false
//
//    override fun onCreate() {
//        login_flag = false
//        super.onCreate()
//    }
//}

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //自定义标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_home)
        window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_title)

        button_backward.visibility = View.INVISIBLE
        val globalData: TimeManager = application as TimeManager
        if (globalData.login_flag) {
            button_message.visibility = View.VISIBLE
            button_message.setOnClickListener(ButtonMessage(this, this))
        }
        //隐藏默认标题栏
        this.supportActionBar?.hide()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_friendlist,
                R.id.navigation_proflie
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}