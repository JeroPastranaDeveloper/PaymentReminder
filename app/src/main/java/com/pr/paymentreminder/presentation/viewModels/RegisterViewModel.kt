package com.pr.paymentreminder.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.pr.paymentreminder.domain.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
}