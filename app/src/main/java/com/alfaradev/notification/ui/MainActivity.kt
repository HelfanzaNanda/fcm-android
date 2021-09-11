package com.alfaradev.notification.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alfaradev.notification.R
import com.alfaradev.notification.ui.history.HistoryFragment
import com.alfaradev.notification.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var navStatus = -1
        const val CHANNEL_ID = "notification"
        private const val CHANNEL_NAME= "notification app"
        private const val CHANNEL_DESC = "Android FCM"
    }
    private var fragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        navigation.setOnItemSelectedListener(onNavigationItemSelectedListener)
        if(savedInstanceState == null){ navigation.selectedItemId = R.id.navigation_home
        }
        setupNotificationManager()
    }

    private val onNavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_home -> {
                if(navStatus != 0){
                    fragment = HomeFragment()
                    navStatus = 0
                }
            }
            R.id.navigation_history -> {
                if(navStatus != 1){
                    fragment = HistoryFragment()
                    navStatus = 1
                }
            }
        }
        if(fragment == null){
            navStatus = 0
            fragment = HomeFragment()
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.screen_container, fragment!!)
        fragmentTransaction.commit()
        true
    }


    private fun setupNotificationManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

}