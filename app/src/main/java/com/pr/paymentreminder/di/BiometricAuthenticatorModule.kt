package com.pr.paymentreminder.di

import androidx.appcompat.app.AppCompatActivity
import com.pr.paymentreminder.data.authentication.BiometricAuthenticator
import com.pr.paymentreminder.data.use_case_implementations.BiometricAuthenticatorUseCaseImpl
import com.pr.paymentreminder.domain.usecase.BiometricAuthenticatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BiometricAuthenticatorModule {
    @Singleton
    @Provides
    fun providesBiometricAuthenticator(
        @ApplicationContext context: AppCompatActivity,
    ): BiometricAuthenticator = BiometricAuthenticator(context)

    @Singleton
    @Provides
    fun providesBiometricAuthenticatorUseCase(
        biometricAuthenticationImpl: BiometricAuthenticatorUseCaseImpl
    ) : BiometricAuthenticatorUseCase = biometricAuthenticationImpl
}