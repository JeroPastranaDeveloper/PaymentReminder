package com.pr.paymentreminder.presentation.paymentreminder.fragments.settings

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.ui.theme.emptyString

class SettingsViewContract : BaseViewContract() {
    data class UiState(
        val prueba: String = emptyString()
    )

    sealed class UiIntent {
        data object SignOut : UiIntent()
        data object ShowSignOutDialog : UiIntent()
    }

    sealed class UiAction {
        data object SignOut : UiAction()
    }
}