package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.pr.paymentreminder.presentation.paymentreminder.prueba.BaseComposeFragment
import com.pr.paymentreminder.presentation.paymentreminder.prueba.addRepeatingJob
import com.pr.paymentreminder.ui.theme.spacing16

class HomeFragment : BaseComposeFragment() {
    @Composable
    override fun ScreenContent() {
        Log.d("Hola", "HOLAHOLAQHOLAHOLA")
        Content()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addRepeatingJob(Lifecycle.State.STARTED) {

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Content() {
        Column(
            modifier = Modifier
                .padding(spacing16)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Home")
        }
    }
}