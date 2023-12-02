package com.pr.paymentreminder.presentation.register

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.pr.paymentreminder.presentation.viewModels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {
    private val viewModel: RegisterViewModel by viewModels()
}