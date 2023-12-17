package com.pr.paymentreminder.data.repositoryImplementations

import com.pr.paymentreminder.data.repository.LoginRepository
import com.pr.paymentreminder.data.source.LoginDataSource
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override fun login(email: String, password: String): StateFlow<Boolean> {
        loginDataSource.login(email, password)
        return loginDataSource.loginState
    }
    override fun isUserAuthenticated(): Boolean = loginDataSource.isUserAuthenticated()
    override fun hasToLogin(): Boolean = loginDataSource.hasToLogin()
    override fun signOut() = loginDataSource.signOut()
}