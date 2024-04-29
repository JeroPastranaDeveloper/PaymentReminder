package com.pr.paymentreminder.data.use_case_implementations.register

import com.pr.paymentreminder.data.source.RegisterDataSource
import com.pr.paymentreminder.domain.usecase.register.RegisterUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val registerDataSource: RegisterDataSource
) : RegisterUseCase {
    override suspend fun invoke(email: String, password: String): StateFlow<Boolean> {
        registerDataSource.register(email, password)
        return registerDataSource.registerState
    }
}