package com.pr.paymentreminder.data.useCaseImplementations

import com.pr.paymentreminder.data.repository.LoginRepository
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository
) : LoginUseCase {
    override fun login(email: String, password: String): StateFlow<Boolean> = loginRepository.login(email, password)

    override fun isUserAuthenticated(): Boolean = loginRepository.isUserAuthenticated()

    override fun signOut() = loginRepository.signOut()
}