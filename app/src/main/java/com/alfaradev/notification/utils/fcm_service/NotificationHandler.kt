package com.alfaradev.notification.utils.fcm_service


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alfaradev.notification.ui.MainActivity
import com.alfaradev.notification.R

object NotificationHandler {

    fun displayNotification(context : Context, title : String, body : String, i : Map<String, String>){
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val mBuilder = NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val mNotificationMgr = NotificationManagerCompat.from(context)
        mNotificationMgr.notify(1, mBuilder.build())
    }
}