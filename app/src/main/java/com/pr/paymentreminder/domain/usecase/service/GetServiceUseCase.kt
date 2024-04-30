package com.pr.paymentreminder.domain.usecase.service

import com.pr.paymentreminder.data.model.Service
import kotlinx.coroutines.flow.Flow

fun interface GetServiceUseCase {
    operator fun invoke(id: String) : Flow<Service>
}