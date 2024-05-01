package com.pr.paymentreminder.data.use_case_implementations.notification_form

import com.pr.paymentreminder.data.source.NotificationDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.notification_form.ClearAllNotificationFormsUseCase
import javax.inject.Inject

class ClearAllNotificationFormsUseCaseImpl @Inject constructor(
    private val notificationDatabaseDataSource: NotificationDatabaseDataSource
) : ClearAllNotificationFormsUseCase {
    override suspend fun invoke() =
        notificationDatabaseDataSource.clearAllNotificationsForm()
}