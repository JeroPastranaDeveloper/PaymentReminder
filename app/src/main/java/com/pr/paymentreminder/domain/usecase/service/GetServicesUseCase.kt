package com.pr.paymentreminder.domain.usecase.service

import com.pr.paymentreminder.data.model.Service
import kotlinx.coroutines.flow.Flow

fun interface GetServicesUseCase {
    operator fun invoke(): Flow<List<Service>>
}