package com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.ui.theme.emptyString

class SettingsViewContract : BaseViewContract() {
    data class UiState(
        val prueba: String = emptyString()
    )

    sealed class UiIntent {
        object SignOut : UiIntent()
        object ShowSignOutDialog : UiIntent()
    }

    sealed class UiAction {
        object SignOut : UiAction()
    }
}