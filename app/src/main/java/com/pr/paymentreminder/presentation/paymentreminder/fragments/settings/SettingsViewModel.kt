package com.pr.paymentreminder.presentation.paymentreminder.fragments.settings

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiIntent
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.domain.usecase.ServiceFormUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesHandler: PreferencesHandler,
    private val serviceForm: ServiceFormUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.SignOut -> signOut()
            is UiIntent.ShowSignOutDialog -> setState { copy(signOut = intent.hasToShow) }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            preferencesHandler.hasToLogin = false
            serviceForm.clearAllServicesForm()
            loginUseCase.signOut()
        }
    }
}