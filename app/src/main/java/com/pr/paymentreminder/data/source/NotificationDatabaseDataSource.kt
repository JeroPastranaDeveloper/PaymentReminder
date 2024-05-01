package com.pr.paymentreminder.data.source

import com.pr.paymentreminder.data.model.Notification

interface NotificationDatabaseDataSource {
    suspend fun clearAllNotificationsForm()
    suspend fun getAllNotificationsForm() : List<Notification>?
    suspend fun getNotificationForm(serviceId: String) : Notification?
    suspend fun removeNotification(serviceId: String)
    suspend fun saveNotificationForm(form: Notification)
}