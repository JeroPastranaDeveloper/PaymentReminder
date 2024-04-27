package com.pr.paymentreminder.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.ui.theme.orZero

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = notificationManager.getNotificationChannel(Constants.NOTIFICATION_CHANNEL_ID)
        if (channel == null) {
            val notificationChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val serviceName = intent?.getStringExtra(Constants.SERVICE_NAME)
        val serviceRemember = intent?.getStringExtra(Constants.SERVICE_REMEMBER)
        val servicePrice = intent?.getStringExtra(Constants.SERVICE_PRICE)

        val activityIntent = Intent(context, LoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_MUTABLE)

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(if (Integer.parseInt(serviceRemember.orEmpty()) > 1)
                context.getString(R.string.notification_plural, serviceRemember, serviceName)
            else
                context.getString(R.string.notification_singular, serviceRemember, serviceName)
            )
            .setContentText("$servicePrice€")
            .setSmallIcon(R.drawable.logo_no_bg)
            .setContentIntent(pendingIntent)
            .build()

        val notificationId = intent?.getIntExtra(Constants.NOTIFICATION_ID, 0).orZero()

        notificationManager.run {
            notify(notificationId, notification)
        }
    }
}