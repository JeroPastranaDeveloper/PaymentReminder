package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.ui.theme.spacing16

class HomeFragment : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun Content() {
        Column(
            modifier = Modifier
                .padding(spacing16)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
            Text(text = "Home")
        }
    }
}