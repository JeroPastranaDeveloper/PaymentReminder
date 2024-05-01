package com.pr.paymentreminder.data.use_case_implementations.notification_form

import com.pr.paymentreminder.data.source.NotificationDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.notification_form.RemoveNotificationFormUseCase
import javax.inject.Inject

class RemoveNotificationFormUseCaseImpl @Inject constructor(
    private val notificationDatabaseDataSource: NotificationDatabaseDataSource
) : RemoveNotificationFormUseCase {
    override suspend fun invoke(serviceId: String) =
        notificationDatabaseDataSource.removeNotification(serviceId)
}