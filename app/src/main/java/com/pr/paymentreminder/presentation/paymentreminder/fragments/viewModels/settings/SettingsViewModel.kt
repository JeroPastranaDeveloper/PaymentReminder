package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings

import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiIntent
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()
    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            UiIntent.SignOut -> signOut()
            UiIntent.ShowSignOutDialog -> dispatchAction(UiAction.SignOut)
        }
    }

    private fun signOut() = loginUseCase.signOut()
}