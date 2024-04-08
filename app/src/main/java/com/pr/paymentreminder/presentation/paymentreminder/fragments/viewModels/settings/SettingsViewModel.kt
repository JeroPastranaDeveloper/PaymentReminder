package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings

import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesHandler: PreferencesHandler
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.SignOut -> signOut()
            UiIntent.ShowSignOutDialog -> dispatchAction(UiAction.SignOut)
        }
    }

    private fun signOut() {
        preferencesHandler.hasToLogin = false
        loginUseCase.signOut()
    }
}