package com.pr.paymentreminder.data.model

data class Notification(
    val serviceId: String,
    val serviceDate: String,
    val notificationDate: String,
    val isNotified: Boolean
)
