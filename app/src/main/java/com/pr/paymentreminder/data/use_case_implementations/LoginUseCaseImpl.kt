package com.pr.paymentreminder.data.use_case_implementations

import com.pr.paymentreminder.data.source.LoginDataSource
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginUseCase {
    override fun login(email: String, password: String): StateFlow<Boolean> {
        loginDataSource.login(email, password)
        return loginDataSource.loginState
    }
    override fun isUserAuthenticated(): Boolean = loginDataSource.isUserAuthenticated()
    override fun signOut() = loginDataSource.signOut()
}