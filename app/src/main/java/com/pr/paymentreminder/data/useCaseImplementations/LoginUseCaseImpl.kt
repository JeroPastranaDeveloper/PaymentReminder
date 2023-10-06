package com.pr.paymentreminder.data.useCaseImplementations

import androidx.lifecycle.LiveData
import com.pr.paymentreminder.data.repository.LoginRepository
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) :
LoginUseCase {
    override suspend fun login(email: String, password: String): LiveData<Boolean> {
        return loginRepository.login(email, password)
    }
}