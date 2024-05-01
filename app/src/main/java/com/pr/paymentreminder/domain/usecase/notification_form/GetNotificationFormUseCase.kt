package com.pr.paymentreminder.domain.usecase.notification_form

import com.pr.paymentreminder.data.model.Notification

fun interface GetNotificationFormUseCase {
    suspend operator fun invoke(serviceId: String): Notification?
}