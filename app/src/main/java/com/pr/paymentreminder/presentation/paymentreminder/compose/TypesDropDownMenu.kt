package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.emptyString

@Composable
fun TypesDropDownMenu(
    types: List<PaymentType>,
    initialSelectedType: String = emptyString(),
    hasHelperText: Boolean,
    textHelperText: String,
    onCategorySelected: (String) -> Unit
) {
    var selectedPaymentType by remember { mutableStateOf(initialSelectedType) }
    var typesExpanded by remember { mutableStateOf(false) }

    Text(
        text = stringResource(id = R.string.payment_type, selectedPaymentType),
        modifier = Modifier.clickable { typesExpanded = !typesExpanded }
    )

    Spacer(modifier = Modifier.height(dimen16))

    if (hasHelperText) HelperText(textHelperText)

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
            hasHelperText = false,
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
            hasHelperText = true,
            textHelperText = stringResource(id = R.string.invalid_service_type)
        ) { /* Nothing */ }
    }
}