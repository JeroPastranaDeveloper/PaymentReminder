package com.pr.servicesModule.data.usecase_implementation

import com.pr.paymentreminder.data.source.SettingsDataSource
import com.pr.paymentreminder.domain.usecase.SettingsUseCase
import javax.inject.Inject

class SettingsUseCaseImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource
) : SettingsUseCase {
    override fun signOut() = settingsDataSource.signOut()
}