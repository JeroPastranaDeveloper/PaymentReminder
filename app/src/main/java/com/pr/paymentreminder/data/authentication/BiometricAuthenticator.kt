package com.pr.paymentreminder.data.authentication

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.pr.paymentreminder.R
import java.util.concurrent.Executor
import javax.inject.Inject

class BiometricAuthenticator @Inject constructor(private val activity: AppCompatActivity) {
    private val executor: Executor = ContextCompat.getMainExecutor(activity)

    fun authenticate(onSuccess: () -> Unit) {
        val biometricPrompt = BiometricPrompt(activity, executor,
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
            .setTitle(activity.getString(R.string.fingerprint_access_title) )
            .setSubtitle(activity.getString(R.string.fingerprint_access_body))
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()
}
