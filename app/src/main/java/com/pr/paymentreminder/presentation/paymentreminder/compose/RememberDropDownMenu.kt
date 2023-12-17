package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.ui.theme.emptyString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RememberDropDownMenu(
    rememberDays: List<Int> = listOf(1, 2, 3),
    initialSelectedDay: String = emptyString(),
    textHelper: StateFlow<String?>,
    textHelperText: String,
    onDaySelected: (String) -> Unit
) {
    var selectedDay by remember { mutableStateOf(initialSelectedDay) }
    var daysExpanded by remember { mutableStateOf(false) }

    val hasHelperText by textHelper.collectAsState(null)

    Text(
        text = stringResource(id = R.string.remember_days_before, selectedDay),
        modifier = Modifier.clickable { daysExpanded = !daysExpanded }
    )

    ServiceSeparator()

    hasHelperText?.let {
        HelperText(textHelperText)
    }

    DropdownMenu(
        expanded = daysExpanded,
        onDismissRequest = {
            daysExpanded = false
        }
    ) {
        rememberDays.forEach { day ->
            DropdownMenuItem(onClick = {
                selectedDay = day.toString()
                onDaySelected(selectedDay)
                daysExpanded = false
            }) {
                Text(text = day.toString())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RememberDropDownMenuPreview() {
    Column {
        RememberDropDownMenu(
            rememberDays = listOf(1, 2, 3),
            initialSelectedDay = "2",
            textHelper = MutableStateFlow<String?>(null),
            textHelperText = stringResource(id = R.string.invalid_service_remember)
        ) { /* Nothing */ }
    }
}

@Preview(showBackground = true)
@Composable
private fun RememberDropDownMenuHelperTextPreview() {
    Column {
        RememberDropDownMenu(
            rememberDays = listOf(1, 2, 3),
            initialSelectedDay = "2",
            textHelper = MutableStateFlow(emptyString()),
            textHelperText = stringResource(id = R.string.invalid_service_remember)
        ) { /* Nothing */ }
    }
}