package com.pr.paymentreminder.data.use_case_implementations.notification_form

import com.pr.paymentreminder.data.model.Notification
import com.pr.paymentreminder.data.source.NotificationDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.notification_form.GetNotificationFormUseCase
import javax.inject.Inject

class GetNotificationFormUseCaseImpl @Inject constructor(
    private val notificationDatabaseDataSource: NotificationDatabaseDataSource
) : GetNotificationFormUseCase {
    override suspend fun invoke(serviceId: String): Notification? =
        notificationDatabaseDataSource.getNotificationForm(serviceId)
}