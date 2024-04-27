package com.pr.paymentreminder.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import com.pr.paymentreminder.data.authentication.BiometricAuthenticator
import com.pr.paymentreminder.data.use_case_implementations.BiometricAuthenticatorUseCaseImpl
import com.pr.paymentreminder.domain.usecase.BiometricAuthenticatorUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var biometricAuthenticator: BiometricAuthenticatorUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        biometricAuthenticator = BiometricAuthenticatorUseCaseImpl(BiometricAuthenticator(this))
        setContent { ComposableContent() }
    }

    @Composable
    abstract fun ComposableContent()
}
