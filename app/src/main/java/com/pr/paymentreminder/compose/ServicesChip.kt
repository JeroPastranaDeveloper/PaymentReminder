package com.pr.paymentreminder.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.pr.paymentreminder.theme.spacing4

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServicesChip(title: String, onClick: () -> Unit, selected: Boolean, onSelectedChange: (String) -> Unit) {
    Chip(
        onClick = {
            onClick()
            onSelectedChange(title)
        },
        modifier = Modifier.padding(end = spacing4),
        colors = ChipDefaults.chipColors(
            contentColor = if (selected) Color.White else Color.Black,
            backgroundColor = if (selected) Color.Gray else Color.LightGray
        )
    ) {
        Text(title)
    }
}