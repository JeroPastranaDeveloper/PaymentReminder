package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels

import androidx.lifecycle.ViewModel
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    fun signOut() = loginUseCase.signOut()
}