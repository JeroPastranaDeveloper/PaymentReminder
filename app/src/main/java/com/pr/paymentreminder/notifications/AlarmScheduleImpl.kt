package com.pr.paymentreminder.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.ui.theme.orFalse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val application: Application,
    private val preferencesHandler: PreferencesHandler
) : AlarmScheduler {
    override suspend fun scheduleAlarm(service: Service) {
        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val serviceIdHashCode = service.id.hashCode()
        val serviceRemember = service.remember
        val serviceName = service.name
        val servicePrice = service.price
        val intent = Intent(application, AlarmReceiver::class.java).apply {
            putExtra(Constants.NOTIFICATION_ID, serviceIdHashCode)
            putExtra(Constants.SERVICE_REMEMBER, serviceRemember)
            putExtra(Constants.SERVICE_NAME, serviceName)
            putExtra(Constants.SERVICE_PRICE, servicePrice)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            application,
            service.id.hashCode(),
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        val serviceDate = LocalDate.parse(service.date, formatter)
        val alarmTime = LocalDateTime.of(
            serviceDate.minusDays(service.remember.toLong()),
            LocalTime.of(12, 0)
        )

        if (preferencesHandler.firstTime || !service.isNotified.orFalse()) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                pendingIntent
            )
        }
    }
}
