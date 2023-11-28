package com.pr.paymentreminder.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.pr.paymentreminder.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "ServiceChannel",
            "Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, "ServiceChannel")
            .setContentTitle("Recordatorio de servicio")
            .setContentText("Tienes un servicio programado para hoy.")
            .setSmallIcon(R.drawable.add)
            .build()

        notificationManager.notify(1, notification)
    }
}
