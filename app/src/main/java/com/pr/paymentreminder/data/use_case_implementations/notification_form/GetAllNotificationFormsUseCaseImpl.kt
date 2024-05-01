package com.pr.paymentreminder.data.use_case_implementations.notification_form

import com.pr.paymentreminder.data.model.Notification
import com.pr.paymentreminder.data.source.NotificationDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.notification_form.GetAllNotificationFormsUseCase
import javax.inject.Inject

class GetAllNotificationFormsUseCaseImpl @Inject constructor(
    private val notificationDatabaseDataSource: NotificationDatabaseDataSource
) : GetAllNotificationFormsUseCase {
    override suspend fun invoke(): List<Notification>? =
        notificationDatabaseDataSource.getAllNotificationsForm()
}