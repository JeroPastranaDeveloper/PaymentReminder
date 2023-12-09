package com.pr.paymentreminder.data.repositoryImplementations

import androidx.lifecycle.LiveData
import com.pr.paymentreminder.data.repository.LoginRepository
import com.pr.paymentreminder.data.source.LoginDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override suspend fun login(email: String, password: String): LiveData<Boolean> = loginDataSource.login(email, password)
    override fun isUserAuthenticated(): Boolean = loginDataSource.isUserAuthenticated()
    override fun signOut() = loginDataSource.signOut()
}