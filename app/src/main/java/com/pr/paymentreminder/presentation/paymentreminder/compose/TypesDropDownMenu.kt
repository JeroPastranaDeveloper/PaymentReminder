package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.clickable
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
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.PaymentType
import com.pr.paymentreminder.ui.theme.emptyString

@Composable
fun TypesDropDownMenu(
    types: List<PaymentType>,
    onCategorySelected: (String) -> Unit
) {
    var selectedPaymentType by remember { mutableStateOf(emptyString()) }
    var typesExpanded by remember { mutableStateOf(false) }

    Text(
        text = stringResource(id = R.string.payment_type, selectedPaymentType),
        modifier = Modifier.clickable { typesExpanded = !typesExpanded }
    )

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