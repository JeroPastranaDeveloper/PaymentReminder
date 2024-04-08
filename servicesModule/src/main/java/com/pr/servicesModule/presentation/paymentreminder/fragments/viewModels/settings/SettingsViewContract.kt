package com.pr.servicesModule.presentation.paymentreminder.fragments.viewModels.settings

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.theme.emptyString

class SettingsViewContract : BaseViewContract() {
    data class UiState(
        val test: String = emptyString()
    )

    sealed class UiIntent {
        data object SignOut : UiIntent()
        data object ShowSignOutDialog : UiIntent()
    }

    sealed class UiAction {
        data object SignOut : UiAction()
    }
}