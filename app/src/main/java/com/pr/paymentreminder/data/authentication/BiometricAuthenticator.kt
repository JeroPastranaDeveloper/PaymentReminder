package com.pr.paymentreminder.data.authentication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.pr.paymentreminder.R
import java.util.concurrent.Executor
import javax.inject.Inject

class BiometricAuthenticator(private val context: Context) {
    private val executor: Executor = ContextCompat.getMainExecutor(context)

    fun authenticate(onSuccess: () -> Unit) {
        val biometricPrompt = BiometricPrompt(context as AppCompatActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }
            }
        )

        val promptInfo = createPrompt()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun createPrompt(): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.fingerprint_access_title) )
            .setSubtitle(context.getString(R.string.fingerprint_access_body))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()
}