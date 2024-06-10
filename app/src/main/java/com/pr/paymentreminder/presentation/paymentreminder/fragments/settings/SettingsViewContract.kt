package com.pr.paymentreminder.presentation.paymentreminder.fragments.settings

import com.pr.paymentreminder.base.BaseViewContract

class SettingsViewContract : BaseViewContract() {
    data class UiState(
        val signOut: Boolean = false
    )

    sealed class UiIntent {
        data object EditCategories : UiIntent()
        data object SignOut : UiIntent()
        data class ShowSignOutDialog(val hasToShow: Boolean) : UiIntent()
    }

    sealed class UiAction {
        data object EditCategories : UiAction()
    }
}