package com.pr.paymentreminder.presentation.paymentreminder.fragments.settings

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiIntent
import com.pr.paymentreminder.domain.usecase.login.SignOutUseCase
import com.pr.paymentreminder.domain.usecase.service_form.ClearAllServiceFormsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val preferencesHandler: PreferencesHandler,
    private val serviceForm: ClearAllServiceFormsUseCase
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
            serviceForm.invoke()
            signOutUseCase()
        }
    }
}