package com.pr.paymentreminder.data.model

import com.pr.paymentreminder.data.room.NotificationRoom

fun NotificationRoom.toDomain() = Notification(
    serviceId = serviceId,
    serviceDate = paymentDate,
    notificationDate = notificationDate,
    isNotified = isNotified
)

fun Notification.toEntity() = NotificationRoom(
    serviceId = serviceId,
    paymentDate = serviceDate,
    notificationDate = notificationDate,
    isNotified = isNotified
)