package com.pr.paymentreminder.data.useCaseImplementations

import androidx.lifecycle.LiveData
import com.pr.paymentreminder.data.repository.RegisterRepository
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val registerRepository: RegisterRepository
) : RegisterUseCase {
    override suspend fun register(email: String, password: String): LiveData<Boolean> {
        return registerRepository.register(email, password)
    }
}