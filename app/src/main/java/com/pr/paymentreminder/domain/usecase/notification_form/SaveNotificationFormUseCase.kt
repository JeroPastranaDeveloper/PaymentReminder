package com.pr.paymentreminder.domain.usecase.notification_form

import com.pr.paymentreminder.data.model.Notification

fun interface SaveNotificationFormUseCase {
    suspend operator fun invoke(form: Notification)
}