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
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.ui.theme.emptyString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TypesDropDownMenu(
    types: List<PaymentType>,
    initialSelectedType: String = emptyString(),
    textHelper: StateFlow<String?>,
    textHelperText: String,
    onCategorySelected: (String) -> Unit
) {
    var selectedPaymentType by remember { mutableStateOf(initialSelectedType) }
    var typesExpanded by remember { mutableStateOf(false) }

    val hasHelperText by textHelper.collectAsState(null)

    Text(
        text = stringResource(id = R.string.payment_type, selectedPaymentType),
        modifier = Modifier.clickable { typesExpanded = !typesExpanded }
    )

    ServiceSeparator()

    hasHelperText?.let {
        HelperText(textHelperText)
    }

    DropdownMenu(
        expanded = typesExpanded,
        onDismissRequest = {
            typesExpanded = false
        }
    ) {
        types.forEach { type ->
            DropdownMenuItem(onClick = {
                selectedPaymentType = type.type
                onCategorySelected(selectedPaymentType)
                typesExpanded = false
            }) {
                Text(text = type.type)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TypesDropDownMenuPreview() {
    Column {
        TypesDropDownMenu(
            types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY),
            initialSelectedType = PaymentType.MONTHLY.type,
            textHelper = MutableStateFlow<String?>(null),
            textHelperText = stringResource(id = R.string.invalid_service_type)
        ) { /* Nothing */ }
    }
}

@Preview(showBackground = true)
@Composable
private fun TypesDropDownMenuHelperTextPreview() {
    Column {
        TypesDropDownMenu(
            types = listOf(PaymentType.WEEKLY, PaymentType.MONTHLY, PaymentType.YEARLY),
            initialSelectedType = PaymentType.MONTHLY.type,
            textHelper = MutableStateFlow(emptyString()),
            textHelperText = stringResource(id = R.string.invalid_service_type)
        ) { /* Nothing */ }
    }
}