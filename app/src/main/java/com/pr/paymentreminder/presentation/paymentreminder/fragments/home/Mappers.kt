package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Notification
import com.pr.paymentreminder.data.model.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Service.toNotification() = Notification(
    serviceId = id,
    serviceDate = date,
    notificationDate = getNotificationDay(),
    isNotified = isNotified
)

fun Service.getNotificationDay(): String {
    val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
    val serviceDate = LocalDate.parse(this.date, formatter)
    return serviceDate.minusDays(remember.toLong()).format(formatter)
}

fun Service.getDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
    return LocalDate.parse(this.date, formatter)
}