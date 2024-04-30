package com.pr.paymentreminder.data.use_case_implementations.login

import com.pr.paymentreminder.data.source.LoginDataSource
import com.pr.paymentreminder.domain.usecase.login.SignOutUseCase
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : SignOutUseCase {
    override fun invoke() = loginDataSource.signOut()
}