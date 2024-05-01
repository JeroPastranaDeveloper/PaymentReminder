package com.pr.paymentreminder.data.data_source_impls

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.model.Notification
import com.pr.paymentreminder.data.room.NotificationDao
import com.pr.paymentreminder.data.source.NotificationDatabaseDataSource
import kotlinx.coroutines.withContext
import com.pr.paymentreminder.data.model.toEntity
import com.pr.paymentreminder.data.model.toDomain
import kotlin.coroutines.CoroutineContext

class NotificationDatabaseDataSourceImpl(
    private val notificationDao: NotificationDao,
    @CoroutineIO private val coroutineContext: CoroutineContext
) : NotificationDatabaseDataSource {
    override suspend fun clearAllNotificationsForm() =
        withContext(coroutineContext) {
            val allForms = notificationDao.getAllForms()
            allForms.orEmpty().forEach {
                notificationDao.deleteForm(it)
            }
        }

    override suspend fun getAllNotificationsForm(): List<Notification>? =
        withContext(coroutineContext) {
            notificationDao.getAllForms()?.map { it.toDomain() }
        }

    override suspend fun getNotificationForm(serviceId: String): Notification =
        withContext(coroutineContext) {
            notificationDao.getNotificationForm(serviceId).toDomain()
        }

    override suspend fun removeNotification(serviceId: String) =
        withContext(coroutineContext) {
            val notification = notificationDao.getNotificationForm(serviceId)
            notificationDao.deleteForm(notification)
        }

    override suspend fun saveNotificationForm(form: Notification) =
        withContext(coroutineContext) {
            notificationDao.saveNotificationRoom(form.toEntity())
        }
}