package com.pr.paymentreminder.notifications

fun interface AlarmScheduler {
    suspend fun scheduleAlarm(serviceId: String)
}