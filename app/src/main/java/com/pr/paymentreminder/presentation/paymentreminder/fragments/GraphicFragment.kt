package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pr.paymentreminder.presentation.paymentreminder.prueba.BaseComposeFragment

class GraphicFragment : BaseComposeFragment() {
    @Composable
    override fun ScreenContent() {
        Content()
    }

    @Composable
    private fun Content() {
        Text(text = "Graphic")
    }
}