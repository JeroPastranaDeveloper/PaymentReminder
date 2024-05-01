package com.pr.paymentreminder.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.domain.usecase.notification_form.SaveNotificationFormUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServiceUseCase
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.toNotification
import com.pr.paymentreminder.ui.theme.doIfFalse
import com.pr.paymentreminder.ui.theme.orFalse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val application: Application,
    private val getService: GetServiceUseCase,
    private val saveNotificationForm: SaveNotificationFormUseCase
) : AlarmScheduler {
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("ScheduleExactAlarm")
    override suspend fun scheduleAlarm(serviceId: String) {
        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val service = getService(serviceId).firstOrNull()

        val serviceIdHashCode = serviceId.hashCode()
        val serviceRemember = service?.remember
        val serviceName = service?.name
        val servicePrice = service?.price
        val intent = Intent(application, AlarmReceiver::class.java).apply {
            putExtra(Constants.NOTIFICATION_ID, serviceIdHashCode)
            putExtra(Constants.SERVICE_REMEMBER, serviceRemember)
            putExtra(Constants.SERVICE_NAME, serviceName)
            putExtra(Constants.SERVICE_PRICE, servicePrice)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            application,
            service?.id.hashCode(),
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
        val serviceDate = LocalDate.parse(service?.date, formatter)
        val alarmTime = LocalDateTime.of(
            serviceDate.minusDays(service?.remember?.toLong() ?: 0),
            LocalTime.of(12, 0)
        )

        (service?.isNotified.orFalse()).doIfFalse {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                pendingIntent
            )
            GlobalScope.launch(Dispatchers.IO) {
                service?.toNotification()?.let { notification ->
                    saveNotificationForm(notification.copy(isNotified = true))
                }
            }
        }
    }
}
