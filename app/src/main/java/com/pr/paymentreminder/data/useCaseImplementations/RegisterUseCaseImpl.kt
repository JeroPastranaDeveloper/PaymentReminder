package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.repository.RegisterRepository
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val registerRepository: RegisterRepository
) : RegisterUseCase {
    override suspend fun register(email: String, password: String): StateFlow<Boolean> = registerRepository.register(email, password)
}