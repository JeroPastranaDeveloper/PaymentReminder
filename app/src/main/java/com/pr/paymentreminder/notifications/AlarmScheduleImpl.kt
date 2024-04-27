package com.pr.paymentreminder.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val application: Application
) : AlarmScheduler {
    @SuppressLint("ScheduleExactAlarm")
    override suspend fun scheduleAlarm(service: Service) {
        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val serviceId = service.id.hashCode()
        val serviceName = service.name
        val serviceRemember = service.remember
        val servicePrice = service.price
        val intent = Intent(application, AlarmReceiver::class.java).apply {
            putExtra(Constants.NOTIFICATION_ID, serviceId)
            putExtra(Constants.SERVICE_REMEMBER, serviceRemember)
            putExtra(Constants.SERVICE_NAME, serviceName)
            putExtra(Constants.SERVICE_PRICE, servicePrice)
        }

        val pendingIntent = PendingIntent.getBroadcast(application, service.id.hashCode(), intent, PendingIntent.FLAG_MUTABLE)

        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        val serviceDate = LocalDate.parse(service.date, formatter)
        val alarmTime = LocalDateTime.of(serviceDate.minusDays(service.remember.toLong()), LocalTime.of(12, 0))

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), pendingIntent)
    }
}

/*
class AlarmSchedulerImpl NUEVO @Inject constructor(
    private val application: Application
) : AlarmScheduler {
    override suspend fun scheduleAlarm(service: Service) {
        val serviceId = service.id.hashCode()
        val serviceName = service.name
        val serviceRemember = service.remember
        val servicePrice = service.price

        val data = workDataOf(
            Constants.NOTIFICATION_ID to serviceId,
            Constants.SERVICE_REMEMBER to serviceRemember,
            Constants.SERVICE_NAME to serviceName,
            Constants.SERVICE_PRICE to servicePrice
        )

        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        val serviceDate = LocalDate.parse(service.date, formatter)
        val alarmTime = LocalDateTime.of(serviceDate.minusDays(service.remember.toLong()), LocalTime.of(12, 0))

        val delay = Duration.between(LocalDateTime.now(), alarmTime).toMillis()

        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(application).enqueue(notificationWork)
    }
}
*/
