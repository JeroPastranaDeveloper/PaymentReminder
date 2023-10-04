package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pr.paymentreminder.presentation.paymentreminder.prueba.BaseComposeFragment

class SettingsFragment : BaseComposeFragment() {
    @Composable
    override fun ScreenContent() {
        Content()
    }

    @Composable
    private fun Content() {
        Text(text = "Settings")
    }
}