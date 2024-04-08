package com.pr.servicesModule.notifications

import com.pr.paymentreminder.data.model.Service

fun interface AlarmScheduler {
    suspend fun scheduleAlarm(service: Service)
}