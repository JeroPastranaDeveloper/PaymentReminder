package com.pr.paymentreminder.data.use_case_implementations.login

import com.pr.paymentreminder.data.source.LoginDataSource
import com.pr.paymentreminder.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginUseCase {
    override suspend fun invoke(email: String, password: String): Boolean =
        loginDataSource.login(email, password)
}