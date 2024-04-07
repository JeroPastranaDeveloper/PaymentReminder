package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.source.RegisterDataSource
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : RegisterUseCase {
    override suspend fun register(email: String, password: String): StateFlow<Boolean> {
        registerDataSource.register(email, password)
        return registerDataSource.registerState
    }
}