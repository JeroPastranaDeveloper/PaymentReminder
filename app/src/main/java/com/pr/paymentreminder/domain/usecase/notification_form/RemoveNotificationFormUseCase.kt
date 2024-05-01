package com.pr.paymentreminder.domain.usecase.notification_form

fun interface RemoveNotificationFormUseCase {
    suspend operator fun invoke(serviceId: String)
}